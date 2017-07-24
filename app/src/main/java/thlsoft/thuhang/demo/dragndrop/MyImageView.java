package thlsoft.thuhang.demo.dragndrop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by ADMIN on 7/24/2017.
 */

@SuppressLint("AppCompatCustomView")
public class MyImageView extends ImageView {

    private boolean is_scale = false;
    private float scale = 1.0f;
    private ScaleGestureDetector scaleGestureDetector;
    int mode = 0 ;
    int DRAG  = 1, DROP = 0;
    FrameLayout.LayoutParams params;

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //LayoutInflater layoutInflater = LayoutInflater.from(context).inflate();
        scaleGestureDetector = new ScaleGestureDetector(context,new MyScale());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

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

}
