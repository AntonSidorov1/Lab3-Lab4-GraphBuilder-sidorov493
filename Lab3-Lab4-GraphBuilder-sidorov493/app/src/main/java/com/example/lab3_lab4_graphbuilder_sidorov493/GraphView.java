package com.example.lab3_lab4_graphbuilder_sidorov493;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.number.Scale;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.Toast;

public class GraphView extends SurfaceView
{

    public boolean GetHaveAPI()
    {
        return  graph.GetHaveAPI();
    }

    public void SetHaveAPI(boolean have)
    {
        graph.SetHaveAPI(have);
    }

    Graph graph;
    Paint p;
    ColorNode NoSelect, Select1, Select2;
    int selected1 = -1, selected2 = -1, selectedNowLink = -1;
    int selectedNowNode = -1;
    public int SelectedNowLink()
    {
        return selectedNowLink;
    }

    public Node GetSelectedNode()
    {
        return graph.GetNode(selectedNowNode);
    }

    public Link GetSelectedLink()
    {
        return graph.GetLink(selectedNowLink);
    }

    public void SetNode(int index, Node node)
    {
        graph.SetNode(index, node);
    }

    public void SetNode(Node node)
    {
        int node1 = SelectedNowNode();
        if(node1 > -1 && node1 < graph.NodeCount())
        SetNode(SelectedNowNode(), node);
    }

    public void SetGraphElement(GraphElement element)
    {
        if(element.IsNode())
            SetNode(element.Node());
    }

    public Boolean Selection()
    {
        return SelectedNowNode() > -1 || SelectedNowLink() > -1;
    }

    public GraphElement GetSelected()
    {
        int node = SelectedNowNode();
        int link = SelectedNowLink();
        if(node > -1 && node < graph.NodeCount())
        {
            return GetSelectedNode();
        }
        else if (link > -1 && link < graph.LinkCount())
        {
            return GetSelectedLink();
        }
        else return null;
    }

    public int SelectedNowNode()
    {
        if(sel1)
            return selected1;
        else if (sel2)
            return selected2;
        else
            return selectedNowNode;
    }

    float csx, csy;
    float CSX()
    {
        return csx;
    }
    float CTX()
    {
        return CSX()+1800.f;
    }
    float CSY()
    {
        return csy;
    }
    float CTY()
    {
        return CSY()+3600.f;
    }

    public GraphView(Context context) {
        super(context);

        p = new Paint();
        NoSelect = new ColorNode(255, 0, 0);
        Select1 = new ColorNode(255, 192, 203);
        Select2 = new ColorNode(255, 0, 127);
        selected1 = selected2 = selectedNowLink = -1;

        Graph graph = new Graph();
        setWillNotDraw(false);
        SetGraph(graph);


        setWillNotDraw(false);
        invalidate();
        //canvas.scale(0.0f, 0.0f, 100.0f, 100.0f);

    }

    public GraphView(Graph graph, Context context)
    {
        this(context);
        SetGraph(graph);
    }

    public Link SetLink(int source, int target, boolean orientation)
    {
        if(source<0 || source >= graph.NodeCount())
            return null;
        if(target<0 || target >= graph.NodeCount())
            return null;
        Link link = graph.AddLink(source, target, orientation);
        SetGraph(graph);
        return link;
    }

    public Link SetLink(int source, int target)
    {
        return SetLink(source, target, true);
    }

    public Link SetLink()
    {
        return SetLink(selected1, selected2);
    }

    public Link SetLink(Link link)
    {
        return SetLink(link.sourceID, link.targetID, link.Orientation);
    }

    public Link SetLink(boolean orientation)
    {
        if(selectedNowLink < 0 || selectedNowLink >= graph.LinkCount())
        return SetLink(selected1, selected2, orientation);
        else
        {
            Link l = graph.GetLink(selectedNowLink);
            l.Orientation = orientation;
            for(int i = 0; i < graph.LinkCount(); i++)
            {
                Link l1 = graph.GetLink(i);
                if(i != selectedNowLink)
                if(l.Orientation)
                {
                    if(l1.ContainsNodes(l.sourceID, l.targetID));
                    {
                        DeleteLink(selectedNowLink);
                        SetGraph(graph);
                        return null;
                    }
                }
                else
                {
                    if(l1.sourceID == l.sourceID && l1.targetID == l.targetID)
                    {
                        DeleteLink(selectedNowLink);
                        SetGraph(graph);
                        return  null;
                    }
                }
            }
            SetGraph(graph);
            return l;
        }
    }

    public Graph GetGraph()
    {
        return  graph;
    }

    public void SetGraph(Graph graph)
    {
        this.graph = graph;
        invalidate();
    }


    public Node AddNode(float x, float y)
    {
        Node node = graph.AddNode(x, y);
        SetGraph(graph);

        return  node;
    }

    public Node AddNode(Node node1)
    {
        node1 = new Node(node1, graph);
        Node node = graph.AddNode(node1);
        SetGraph(graph);

        return  node;
    }

    public Node AddNode(float x, float y, String name)
    {
        Node node = graph.AddNode(x, y, name);
        SetGraph(graph);

        return  node;
    }

    public Node AddNode()
    {
        Graph graph1 = new Graph();
        Node n = graph1.AddNode();
        float x = n.X + dX;
        float y = n.Y + dy;
        Node node = graph.AddNode(x, y);
        SetGraph(graph);
        return node;
    }

    public void DeleteNode(int index)
    {
        int delete = index;
        if(delete < graph.NodeCount() && delete > -1)
        {
            graph.DeleteNode(delete);
        }

        int selectedNowNode = delete;
        if(selected1 == selectedNowNode)
        {

            selected1 = selected2;
            selected2 = -1;
        }
        else if (selected2 == selectedNowNode)
        {
            selected2 = -1;

        }

        if(selected1 > selectedNowNode)
            selected1--;
        if(selected2 > selectedNowNode)
            selected2 --;


        SetGraph(graph);
    }

    public void DeleteLink (int id)
    {
        if(id > -1 && id < graph.LinkCount())
        {
            graph.DeleteLink(id);
        }
    }

    public void ChangeOrientationLink(int id)
    {
        if(id > -1 && id < graph.LinkCount())
        {
            graph.GetLink(id).ChangeOrientationLink();
            SetGraph(graph);
        }
    }

    public void ChangeOrientationLink()
    {
        ChangeOrientationLink(SelectedNowLink());
    }

    public void Delete()
    {
        if(selectedNowNode > -1) {
            DeleteNode(selectedNowNode);
            if (selected1 == selectedNowNode) {

                selected1 = selected2;
                selected2 = -1;
            } else if (selected2 == selectedNowNode) {
                selected2 = -1;

            }

            if (selected1 > selectedNowNode)
                selected1--;
            if (selected2 > selectedNowNode)
                selected2--;
        }
        else if (selectedNowLink > -1)
        {
            DeleteLink(selectedNowLink);
        }
        SetGraph(graph);
    }


    public void paint()
    {
        invalidate();
    }

    float dX = 0.0f, dY = 0.0f;
    float sX = 0.0f, sY = 0.0f;

    float halfside = 40.0f;
    float rad = 60.0f;
    @Override
    protected void onDraw(Canvas canvas) {
        try {

            halfside = rad/2f;
            float csx = CSX(), csy = CSY(), ctx = CTX(), cty = CTY();

            p.setStrokeWidth(10.0f);
            //canvas.scale(1800.0f, 3600.0f);
            canvas.drawColor(Color.rgb(255, 255, 255));




            int links = graph.LinkCount();
            p.setStyle(Paint.Style.FILL);
            for (int i = 0; i < links; i++) {
                try {

                    if (i == selectedNowLink) p.setColor(Select2.GetBorderColor());
                    else p.setColor(NoSelect.GetBorderColor());

                    Link l = graph.GetLink(i);
                    Node source = l.Source();
                    Node target = l.Target();

                    float sx = source.X - dX;
                    float sy = source.Y - dY;
                    float tx = target.X - dX;
                    float ty = target.Y - dY;

                    canvas.drawLine(sx, sy, tx, ty, p);
                    if (l.Orientation) {
                        float xRad, yRad;

                        float URad = (rad*2f)/(sx + tx);
                        URad = 1f - URad;
                        xRad = (tx - sx)*URad;
                        yRad = (ty - sy) * URad;
                        xRad += sx;
                        yRad += sy;

                        float d = halfside / 2f;
                        canvas.drawRect(xRad - d, yRad - d, xRad + d, yRad + d, p);
                    }

                    float cx = (sx + tx) * 0.5f;
                    float cy = (sy + ty) * 0.5f;
                    float x0 = cx - halfside;
                    float x1 = cx + halfside;
                    float y0 = cy - halfside;
                    float y1 = cy + halfside;


                    ColorNode color;
                    if(i == selectedNowLink) color = Select2;
                    else color = Select1;

                    p.setStyle(Paint.Style.FILL);
                    p.setColor(color.GetFillColor());
                    canvas.drawRect(x0, y0, x1, y1, p);

                    p.setStyle(Paint.Style.STROKE);
                    p.setColor(color.GetBorderColor());
                    canvas.drawRect(x0, y0, x1, y1, p);

                }
                catch(Exception ex)
                {
                    try {

                            graph.DeleteLink(i);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            boolean sel1 = false, sel2 = false;

            if (selected2 < 0 || selected2 >= graph.NodeCount()) {
                selected2 = -1;
                selectedNowNode = selected1;
            } else if (selected1 < 0 || selected1 >= graph.NodeCount()) {
                if (selected2 >= 0)
                    selected1 = selected2;
                else
                    selected1 = -1;
                selected2 = -1;
                selectedNowNode = selected1;
            } else {
                selectedNowNode = selected2;
            }

            for (int i = 0; i < graph.NodeCount(); i++) {

                try {

                    Node n = graph.GetNode(i);
                    n.rad = rad;
                    p.setStyle(Paint.Style.FILL);
                    ColorNode color;
                    if (i == selected1) {
                        color = Select1;
                        sel1 = true;
                    } else if (i == selected2) {
                        color = Select2;
                        sel2 = true;
                    } else
                        color = NoSelect;

                    float nx = n.X - dX, ny = n.Y - dY;
                    p.setColor(color.GetFillColor());
                    canvas.drawCircle(nx, ny, rad, p);

                    p.setStyle(Paint.Style.STROKE);
                    p.setColor(color.GetBorderColor());
                    canvas.drawCircle(nx, ny, rad, p);
                }
                catch(Exception ex)
                {

                }
            }

            return;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG);
        }


        //super.onDraw(canvas);
    }

    boolean toach = false;
    float dx, dy;
    float selX, selY;
    boolean sel1, sel2;

    private float c (float a, float d) {
        return a + d;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();
        float xc = c(x, dX), yc = c(y, dY);

        int action = event.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                    {
                        if(dX == 0)
                            sX = x;
                        if(dY == 0)
                            sY = y;
                        xc = c(x, dX);
                        yc = c(y, dY);
                        
                        toach = false;
                        for(int i = 0; i< graph.NodeCount(); i++)
                        {
                            Node n = graph.GetNode(i);
                            float nx = n.X;
                            float ny = n.Y;
                            selX = nx;
                            selY = ny;
                            float nRad = n.rad;
                            nRad = nRad*nRad;
                            float rad = (float)(Math.pow(xc - nx, 2)+Math.pow(yc - ny, 2));
                            if(rad <= nRad)
                            {
                                toach = true;
                                dx = nx - xc;
                                dy = ny - yc;
                                selectedNowLink = -1;
                                selectedNowNode = i;
                                if(selected1 > -1)
                                {
                                    if(i == selected1)
                                    {
                                        sel1 = true;
                                        //SetGraph(graph);
                                        return true;
                                    }
                                    else
                                    {
                                        if(i == selected2)
                                        {
                                            sel2 = true;
                                        }
                                        else
                                        {
                                            selected2 = i;
                                        }
                                    }
                                }
                                else
                                {
                                    selected1 = i;
                                }

                                SetGraph(graph);
                                return true;
                            }
                        }

                        selected1 = -1;
                        selected2 = -1;
                        selectedNowNode = -1;

                        for(int i = 0; i < graph.LinkCount(); i++)
                        {
                            Link l = graph.GetLink(i);
                            Node s = l.Source();
                            Node t = l.Target();
                            float sx = s.X;
                            float tx = t.X;
                            float sy = s.Y;
                            float ty = t.Y;

                            float cx = (sx + tx) * 0.5f;
                            float cy = (sy + ty) * 0.5f;
                            float x0 = cx - halfside;
                            float x1 = cx + halfside;
                            float y0 = cy - halfside;
                            float y1 = cy + halfside;
                            if(i == selectedNowLink)
                            {
                                selectedNowLink = -1;

                                SetGraph(graph);
                                return true;
                            }
                            else if (xc >= x0 && xc <= x1 && yc >= y0 && yc <= y1)
                            {
                                selectedNowLink = i;

                                SetGraph(graph);
                                return true;
                            }
                        }
                        selectedNowLink = -1;

                        SetGraph(graph);
                        toach = true;
                        return true;
                    }
                    //break;
            case MotionEvent.ACTION_MOVE:
            {
                if(selectedNowNode > -1 && toach)
                {


                    Node n = graph.GetNode(SelectedNowNode());
                    n.X = xc + dx;
                    n.Y = yc + dy;
                }
                else
                {
                    dX = x - sX;
                    dY = y - sY;
                }

                SetGraph(graph);

                return true;
            }
            //break;
            case MotionEvent.ACTION_UP:
            {
                toach = false;

                int i = SelectedNowNode();
                if(i < 0 || i >= graph.NodeCount())
                    return true;
                Node n = graph.GetNode(i);
                sel2 = sel2 && (n.X == selX && n.Y == selY);
                sel1 = sel1 && (n.X == selX && n.Y == selY) && !sel2;

                if(sel2)
                {
                    selected1 = i;
                    selected2 = -1;
                }
                if(sel1)
                {

                    selected1 = -1;
                    selectedNowNode = -1;
                }

                SetGraph(graph);
                sel1 = sel2 = false;

                return true;
            }
            //break;
        }

        return super.onTouchEvent(event);
    }
}
