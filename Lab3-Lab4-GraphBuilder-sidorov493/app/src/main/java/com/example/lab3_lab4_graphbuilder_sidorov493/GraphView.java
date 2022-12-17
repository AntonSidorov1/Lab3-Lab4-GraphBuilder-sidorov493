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
        if(selectedNowLink > graph.LinkCount())
            selectedNowLink=-1;
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
        Node node1 = graph.GetNode(index);
        BeforeEditNode(node1);
        graph.SetNode(index, node);
        node1 = graph.GetNode(index);
        AfterEditNode(node1, "update");
    }

    public void SetNode(Node node)
    {
        int node1 = SelectedNowNode();
        if(node1 > -1 && node1 < graph.NodeCount())
        SetNode(SelectedNowNode(), node);
    }

    public void SetGraphElement(GraphElement element)
    {
        if(element.IsNode()) {
            SetNode(element.Node());
        }
        else if(element.IsLink())
        {
            try {


                Link link1 = element.Link();
                int source = -1;
                int target = -1;
                BeforeEditNode(link1);
                try {
                    Link link2 = graph.GetLink(selectedNowLink);
                    source = link2.IDsourceAPI();
                    target = link2.IDtargetAPI();
                }
                catch (Exception ex)
                {
                    link1.GetText();
                }
                Link link = graph.SetLink(selectedNowLink, link1);
                link.Orientation = link1.Orientation;
                link.Value = link1.Value;
                link.Text = link1.Text;
                link.ValueVisible = link1.ValueVisible;
                link.TextVisible = link1.TextVisible;
                if(link.IDsourceAPI() == source && link.IDtargetAPI() == target)
                {
                    AfterEditNode(link, "update");
                }
                else
                {
                    AfterEditNode(link, "delete");
                    AfterEditNode(link, "insert");
                }


            }
            catch (Exception ex)
            {
                AfterEditNode(null, "select");
            }

        }
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
        else {
            if(selectedNowNode > graph.NodeCount())
                selectedNowNode = -1;
            return selectedNowNode;
        }
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
        BeforeEditNode(link);
        SetGraph(graph);
        AfterEditNode(link, "insert");
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
                        selectedNowLink = -1;
                        SetGraph(graph);
                        return null;
                    }
                }
                else
                {
                    if(l1.sourceID == l.sourceID && l1.targetID == l.targetID)
                    {
                        DeleteLink(selectedNowLink);
                        selectedNowLink = -1;
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
        BeforeEditNode(n);
        float x = n.X + dX;
        float y = n.Y + dY;
        Node node = graph.AddNode(x, y);
        //Node node = graph.AddNode(sX, sY);
        SetGraph(graph);
        AfterEditNode(n, "insert");
        return node;
    }

    public void DeleteNode()
    {
        DeleteNode(SelectedNowNode());
    }

    public void DeleteNode(int index)
    {
        if(index < 0 || index > graph.NodeCount())
            return;
        int delete = index;
        Node node = graph.GetNode(index);
        BeforeEditNode(node);
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
        AfterEditNode(node, "delete");
    }


    public void DeleteLink()
    {
        DeleteLink(SelectedNowLink());
    }


    public void DeleteLink (int id)
    {
        if(id > -1 && id < graph.LinkCount())
        {
            Link link = graph.GetLink(id);
            BeforeEditNode(link);
            graph.DeleteLink(id);
            AfterEditNode(link, "delete");
        }
    }

    public void ChangeOrientationLink(int id)
    {
        if(id > -1 && id < graph.LinkCount())
        {
            Link link = graph.GetLink(id);
            BeforeEditNode(link);
            graph.GetLink(id).ChangeOrientationLink();
            SetGraph(graph);
            AfterEditNode(link, "delete");
            AfterEditNode(link, "insert");
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
                    ColorNode color;
                    if(i == selectedNowLink) color = Select2;
                    else color = NoSelect;

                    Link l = graph.GetLink(i);
                    Node source = l.Source();
                    Node target = l.Target();

                    float sx1 = source.X;
                    float tx1 = target.X;
                    float sy1 = source.Y;
                    float ty1 = target.Y;
                    float sx = sx1 - dX;
                    float sy = sy1 - dY;
                    float tx = tx1 - dX;
                    float ty = ty1 - dY;
                    float sx2 = sx1, sy2 = sy1, tx2 = tx1, ty2 = ty1;

                    p.setStyle(Paint.Style.FILL);
                    p.setColor(color.GetBorderColor());

                    canvas.drawLine(sx, sy, tx, ty, p);
                    if (l.Orientation) {
                        float xRad, yRad;

                        //sx1 = Math.abs(sx1);
                        //tx1 = Math.abs(tx1);
                        //sy1 = Math.abs(sy1);
                        //ty1 = Math.abs(ty1);
                        //float URadX = (rad*2f)/(sx1 + tx1);
                        //float URadY = (rad*2f)/(sy1 + ty1);
                        float max = 0.2f;
                        float SX = Math.abs(tx - sx);
                        //float URadX = (rad*2f)/SX;
                        float URadX = max;
                        while(URadX > max)
                        {
                            URadX/=2;
                        }
                        float SY = Math.abs(ty - sy);
                        //float URadY = (rad*2f)/SY;
                        float URadY = max;
                        while(URadY > max)
                        {
                            URadY/=2;
                        }

                        //float URadY = URadX;
                        /*URadX = 1f - URadX;
                        URadY = 1f - URadY;
                        xRad = (tx - sx)*URadX;
                        yRad = (ty - sy) * URadY;
                        xRad += sx;
                        yRad += sy;*/
                        xRad = yRad = 0;
                        if(tx == sx)
                        {
                            xRad = sx;
                        }
                        else if(tx > sx)
                        {
                            xRad = tx - SX*URadX;
                        }
                        else
                        {
                            xRad = tx + SX*URadX;
                        }
                        if(ty == sy)
                        {
                            yRad = sy;
                        }
                        else if(ty > sy)
                        {
                            yRad = ty - SY*URadY;
                        }
                        else
                        {
                            yRad = ty + SY*URadY;
                        }

                        float d = halfside / 2f;
                        canvas.drawRect(xRad - d, yRad - d, xRad + d, yRad + d, p);
                    }

                    float cx = (sx + tx) * 0.5f;
                    float cy = (sy + ty) * 0.5f;
                    float x0 = cx - halfside;
                    float x1 = cx + halfside;
                    float y0 = cy - halfside;
                    float y1 = cy + halfside;

                    if(i == selectedNowLink) color = Select2;
                    else color = Select1;

                    p.setStyle(Paint.Style.FILL);
                    p.setColor(color.GetFillColor());
                    canvas.drawRect(x0, y0, x1, y1, p);

                    p.setStyle(Paint.Style.STROKE);
                    p.setColor(color.GetBorderColor());
                    canvas.drawRect(x0, y0, x1, y1, p);
                    float width = p.getStrokeWidth();
                    p.setStrokeWidth(width/2f);
                    p.setTextSize(p.getStrokeWidth()*10f);
                    p.setColor(Color.BLACK);
                    float length = p.getTextSize()/2f;
                    float length1 = 1f;
                    if(l.TextVisible)
                    {

                        float yv = y1;
                        if(l.sourceID > l.targetID) {
                            length1 *= (-1);
                            yv = y0;
                        }

                        canvas.drawText(l.Text, x0 - length, yv+(p.getStrokeWidth()*10f*length1), p);
                    }
                    if(l.ValueVisible)
                    {
                        if(l.sourceID > l.targetID)
                            length*=(-1);
                        float yv = y0;
                        if(l.sourceID > l.targetID)
                        yv = y1;
                        canvas.drawText(String.valueOf(l.Value), x0 - (length*2f), yv, p);
                    }
                    p.setStrokeWidth(width);



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

            NameView();
            if(graph.Get_API_ID() > -1)
            {
                Save();
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

    public void BeforeEditNode(GraphElement n)
    {

    }

    public void AfterEditNode(GraphElement n, String method)
    {

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
                        else
                            sX=x+dX;
                        if(dY == 0)
                            sY = y;
                        else
                            sY = y+dY;
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
                                BeforeEditNode(n);
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
                                Link link = graph.GetLink(i);
                                BeforeEditNode(link);

                                //Link link = graph.GetLink(i);
                                //BeforeEditNode(link);
                                SetGraph(graph);
                                return true;
                            }
                        }
                        selectedNowLink = -1;

                        SetGraph(graph);
                        toach = true;
                        AfterEditNode(null, "select");
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
                    //dX = x - sX;
                    dX = sX - x;
                    //dY = y - sY;
                    dY = sY - y;
                }

                SetGraph(graph);

                return true;
            }
            //break;
            case MotionEvent.ACTION_UP:
            {

                AfterEditNode(null, "select");
                toach = false;

                int i = SelectedNowNode();
                if(i < 0 || i >= graph.NodeCount()) {
                    return true;
                }
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

                AfterEditNode(n, "update");

                SetGraph(graph);
                sel1 = sel2 = false;
                return true;
            }
            //break;
        }

        return super.onTouchEvent(event);
    }

    public String GetName()
    {
        return graph.GetName();
    }

    public void NameView()
    {

    }

    public void Save()
    {

    }
}
