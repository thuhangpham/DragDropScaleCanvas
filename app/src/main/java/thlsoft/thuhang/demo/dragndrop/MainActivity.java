package thlsoft.thuhang.demo.dragndrop;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;
    private MyImageView imageView, imageView2;
    private Zoomer zoomer;
    private static final float ZOOM_AMOUNT = 0.2f;
    int w = 0, h = 0;
    public static  float scale = 1.0f;
    float onScaleStart = 0, onScaleEnd = 0;
    private CanvasView customCanvas;
    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;
    public static float width;
    public static float height;
    private MotionEvent e;

    private int[] imgLst = {R.id.imgView, R.id.imgView2};

    private boolean is_scale = false;
    int mode = 0 ;
    int DRAG  = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (MyImageView) findViewById(R.id.imgView);
        imageView2 = (MyImageView) findViewById(R.id.imgView2);
        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);
        scaleGestureDetector = new ScaleGestureDetector(this, new MyScale());

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(100,100);
        layoutParams.leftMargin = 10;
        layoutParams.topMargin = 10;
        imageView.setLayoutParams(layoutParams);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(100,100);
        layoutParams2.leftMargin = 10;
        layoutParams2.topMargin = 60;
        imageView2.setLayoutParams(layoutParams2);

        for (int i=0; i<imgLst.length;i++)
            ((MyImageView)findViewById(imgLst[i])).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return customCanvas.onTouch(v,event);
                }
            });

//        imageView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return customCanvas.onTouch(v,event);
//            }
//        });

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
    }


    /*  */

    class MyScale extends ScaleGestureDetector.SimpleOnScaleGestureListener{

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale *= detector.getScaleFactor();
            return super.onScale(detector);
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            onScaleStart = scale;
            is_scale = true;
            Log.e(getClass().getName(), "OnStartBegin: " + onScaleStart );
            return super.onScaleBegin(detector);
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            onScaleEnd = scale;
            is_scale = false;
            Log.e(getClass().getName(), "onScaleEnd: " + onScaleEnd );
            super.onScaleEnd(detector);
        }
    }

}
