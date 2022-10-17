package com.example.lab3_lab4_graphbuilder_sidorov493;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class GraphView extends SurfaceView
{

    Graph graph;
    Paint p;
    ColorNode NoSelect, Select1, Select2;
    int selected1, selected2, selected3;
    public GraphView(Context context) {
        super(context);

        p = new Paint();
        NoSelect = new ColorNode(255, 0, 0);
        Select1 = new ColorNode(255, 192, 203);
        Select2 = new ColorNode(255, 0, 127);
        selected1 = selected2 = selected3 = -1;

        Graph graph = new Graph();
        setWillNotDraw(false);
        SetGraph(graph);
    }

    public GraphView(Graph graph, Context context)
    {
        this(context);
        SetGraph(graph);
    }

    public Graph GetGraph()
    {
        return  graph;
    }

    public void SetGraph(Graph graph)
    {
        this.graph = graph;
        paint();
    }

    public void paint()
    {
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.rgb(255, 255, 255));

        float halfside = 1.0f;
        float rad = 5.0f;

        for(int i = 0; i < graph.LinkCount(); i++)
        {
            Link l = graph.GetLink(i);
            Node source = l.Source();
            Node target = l.Target();

            float sx = source.X;
            float sy = source.Y;
            float tx = target.X;
            float ty = target.Y;

            canvas.drawLine(sx, sy, tx, ty, p);
            if(l.Orientation)
            {
                float dx = tx - sx;
                float dy = ty - sy;
                if(dx < 0.0f)
                {
                    dx = -1f;
                }
                else
                {
                    dx = 1f;
                }
                if(dy < 0.0f)
                {
                    dy = -1f;
                }
                else
                {
                    dy = 1f;
                }
                dx = dx * 4f;
                dy = dy * 4f;
                canvas.drawRect(tx, ty, tx + dx, ty + dy, p);
            }

            float cx = (sx + tx)*0.5f;
            float cy = (sy + ty)*0.5f;
            float x0 = cx - halfside;
            float x1 = cx + halfside;
            float y0 = cy - halfside;
            float y1 = cy + halfside;

            if(i == selected3) p.setColor(Select2.GetBorderColor());
            else p.setColor(Select1.GetBorderColor());
            canvas.drawRect(x0, y0, x1, y1, p);

            if(i == selected3) p.setColor(Select2.GetBorderColor());
            else p.setColor(Select1.GetBorderColor());
        }

        for(int i = 0; i < graph.NodeCount(); i++)
        {
            Node n = graph.GetNode(i);
            p.setStyle(Paint.Style.FILL);
            ColorNode color;
            if(i == selected1)
                color = Select1;
            else if (i == selected2)
                color = Select2;
            else
                color = NoSelect;

            p.setColor(color.GetFillColor());
            canvas.drawCircle(n.X, n.Y, rad, p);

            p.setStyle(Paint.Style.STROKE);
            p.setColor(color.GetBorderColor());
            canvas.drawCircle(n.X, n.Y, rad, p);
        }

        //super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        return super.onTouchEvent(event);
    }
}
