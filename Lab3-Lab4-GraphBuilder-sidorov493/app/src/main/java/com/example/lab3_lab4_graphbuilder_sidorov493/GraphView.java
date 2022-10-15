package com.example.lab3_lab4_graphbuilder_sidorov493;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class GraphView extends SurfaceView
{

    public GraphView(Context context) {
        super(context);

        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.rgb(255, 255, 255));
        //super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        return super.onTouchEvent(event);
    }
}
