package com.example.lab3_lab4_graphbuilder_sidorov493;

public class Node extends GraphElement {
    public float X = 100, Y =100;
    private Graph graph;

    public void SetName(String name)
    {
        NameElement = name;
    }

    public int ID()
    {
        try {
            return graph.IndexNode(this);
        }
        catch (Exception ex)
        {
            return  -1;
        }
    }

    public int id()
    {
        return ID();
    }

    public Graph GetGraph()
    {
        return  graph;
    }

    public Graph Graph()
    {
        return  GetGraph();
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
        NameElement = "Node" + String.valueOf(id());
    }



}
