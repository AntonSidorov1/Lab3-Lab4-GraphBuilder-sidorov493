package com.example.lab3_lab4_graphbuilder_sidorov493;

public class Node extends GraphElement {
    public float X = 100, Y =100;
    private Graph graph;

    public void SetName(String name)
    {
        NameElement = name;
    }

    @Override
    public String GetNameFromID() {
        return "Node" + String.valueOf(id());
    }

    @Override
    public GraphElement CopyElement() {
        return new Node(this, GetGraph());
    }

    @Override
    public GraphElement CopyElement(Graph graph) {
        Node node = CopyElement().Node();
        node.SetGraph(graph);
        return node;
    }

    public int ID()
    {
        try {
            int index = graph.IndexNode(this);
            return index;
        }
        catch (Exception ex)
        {
            return  -1;
        }
    }

    public Graph GetGraph()
    {
        return  graph;
    }

    public void SetGraph(Graph graph)
    {

        try
        {
            this.graph.DeleteNode(id());
        }
        catch (Exception ex)
        {

        }
        this.graph = graph;
    }

    public Node(float x, float y, String name, Graph graph)
    {
        super(name);
        X = x;
        Y = y;

        SetGraph(graph);
    }

    public Node(float x, float y, Graph graph)
    {
        this(x, y, "", graph);
        SetNameFromID();
    }

    public Node(Graph graph)
    {
        this(450.0f, 350.0f, graph);
    }

    public Node (Node node, Graph graph)
    {
        this(node.X, node.Y, node.GetName(), graph);
        IDinAPI = node.IDinAPI;
    }

    public float rad = 0.0f;


    @Override
    public String TypeText() {
        return "Узел (Node)";
    }

    public void SetNode(float x, float y, String name)
    {
        X = x;
        Y = y;
        NameElement = name;

    }

    public void SetNode(Node node)
    {
        SetNode(node.X, node.Y, node.GetName());
        IDinAPI = node.IDinAPI;
    }


}
