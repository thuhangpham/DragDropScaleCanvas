package thlsoft.thuhang.demo.dragndrop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;


/**
 * Created by ADMIN on 7/24/2017.
 */
//https://examples.javacodegeeks.com/android/core/graphics/canvas-graphics/android-canvas-example/

public class CanvasView  extends View implements IMyCanvas, View.OnTouchListener {
    private boolean is_scale = false;
    private float scale = 1.0f;
    private ScaleGestureDetector scaleGestureDetector;
    int mode = 0 ;
    int DRAG  = 1, DROP = 0;

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Path xPath, yPath;
    Context context;
    private Paint mPaint;
    private float mX, mY, width, height;
    private static final float TOLERANCE = 5;

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;
        // we set a new Path
        mPath = new Path();

        // and we set a new Paint with the desired attributes
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(1f);

        xPath = new Path();
        yPath = new Path();

        scaleGestureDetector = new ScaleGestureDetector(context, new MyScale());

    }

    // override onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // your Canvas will draw onto the defined Bitmap
//        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//        mCanvas = new Canvas(mBitmap);
        width = w;
        height = h;
    }

    // override onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // draw the mPath with the mPaint on the canvas when onDraw
        //canvas.drawPath(mPath, mPaint);
        canvas.drawPath(xPath, mPaint);
        canvas.drawPath(yPath, mPaint);
        xPath.reset();
        yPath.reset();
        invalidate();

    }

    // when ACTION_DOWN start touch according to the x,y values
    private void startTouch(float x, float y) {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    // when ACTION_MOVE move touch according to the x,y values
    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;


            xPath.moveTo(mX, 0); // di qua diem
            xPath.quadTo(mX, mY, mX, height);
            yPath.moveTo(0, mY);
            yPath.quadTo(mX, mY,width, mY);

        }


    }

    public void clearCanvas() {
        mPath.reset();
        invalidate();
    }

    // when ACTION_UP stop touch
    private void upTouch() {
        mPath.lineTo(mX, mY);

        xPath.reset();
        yPath.reset();
        invalidate();
    }
    public boolean onTouchEv(int x, int y, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //startTouch(x, y);
                moveTouch(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
            case MotionEvent.ACTION_MASK:
                moveTouch(x,y);
                break;
        }
        return true;
    }

    @Override
    public void onTouch(int x, int y, MotionEvent event) {
        onTouchEv(x, y, event);
    }

    RelativeLayout.LayoutParams params;
    float dx = 0, dy = 0, x = 0, y = 0;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView img = (ImageView) v;
        scaleGestureDetector.onTouchEvent(event);

        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                params = (RelativeLayout.LayoutParams) img.getLayoutParams();
                dx = event.getRawX() - params.leftMargin;
                dy = event.getRawY() - params.topMargin;
                mode = DRAG;
                break;
            case MotionEvent.ACTION_MOVE:
                if(mode==DRAG){
//                    if(scale>= 1.0f && scale<= 2.5f) {
//                        img.setScaleY(scale);
//                        img.setScaleX(scale);
//                    }

                    x = event.getRawX();
                    y = event.getRawY();

                    params.leftMargin = (int) (x - dx);
                    params.topMargin = (int) (y - dy);

                    if(params.leftMargin <= img.getWidth()/3)
                        params.leftMargin = 0;
                    if(params.topMargin <= img.getHeight()/3)
                        params.topMargin = 0;

                    params.rightMargin = 0;
                    params.bottomMargin = 0;

                    params.rightMargin = (int) (width + img.getWidth()/4);
                    params.bottomMargin = (int) (height + img.getHeight()/4);

//                    if(params.rightMargin <= img.getWidth())
//                        params.leftMargin = (int) (width - img.getWidth());
//                    if(params.bottomMargin <= img.getHeight())
//                        params.topMargin = (int) (height - img.getHeight());
                    if(params.leftMargin >= width - img.getWidth())
                        params.leftMargin = (int) (width - img.getWidth());
                    if(params.topMargin >= height - img.getHeight())
                        params.topMargin = (int) (height - img.getHeight());


                    img.setLayoutParams(params);
                    int xx = (int) img.getX() + img.getWidth() / 2;
                    int yy = (int) img.getY() + img.getHeight();
//                    if(!is_scale){
//
//                        this.onTouch(xx,yy,event);
//                        is_scale = false;
//                    }
                    this.onTouch(xx,yy,event);
                }
                break;
        }
        return true;
    }
    class MyScale extends ScaleGestureDetector.SimpleOnScaleGestureListener{

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale *= detector.getScaleFactor();
            return super.onScale(detector);
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            is_scale = true;
            return super.onScaleBegin(detector);
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            is_scale = false;
            super.onScaleEnd(detector);
        }
    }


    //override the onTouchEvent
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        float x = event.getRawX();
//        float y = event.getRawY();
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                startTouch(x, y);
//                invalidate();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                moveTouch(x, y);
//                invalidate();
//                break;
//            case MotionEvent.ACTION_UP:
//                upTouch();
//                invalidate();
//                break;
//        }
//        return true;
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                break;
//            case MotionEvent.ACTION_MOVE:
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//        }
//        return true;
//    }
}