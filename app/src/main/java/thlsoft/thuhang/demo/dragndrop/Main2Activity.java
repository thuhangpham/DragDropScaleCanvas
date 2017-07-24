package thlsoft.thuhang.demo.dragndrop;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;

public class Main2Activity extends AppCompatActivity {
    private CanvasView customCanvas;
//    public static float width;
//    public static float height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
//        width = size.x;
//        height = size.y;
    }
    public void clearCanvas(View v) {
        //customCanvas.clearCanvas();
    }

}
