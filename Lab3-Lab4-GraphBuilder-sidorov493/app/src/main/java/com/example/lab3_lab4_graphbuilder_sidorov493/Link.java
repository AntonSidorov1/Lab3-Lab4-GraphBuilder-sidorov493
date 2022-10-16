package com.example.lab3_lab4_graphbuilder_sidorov493;

import java.util.ArrayList;

public class Link extends GraphElement {
    Graph graph;
    public float Value = 0.0f;
    public boolean Orientation = false;
    public String Text = "";
    public boolean ValueVisible = false;
    ArrayList<Node> nodes = new ArrayList<>();

    public int ID()
    {
        try {
            return graph.IndexLink(this);
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

    public Graph Graph()
    {
        return  graph;
    }
    private Node source, target;
    public Node Source()
    {
        return source;
    }
    public Node Target()
    {
        return target;
    }

    public void SetGraph(Graph graph)
    {
        try
        {
            this.graph.DeleteLink(id());
        }
        catch (Exception ex)
        {

        }

        this.graph = graph;
    }

    public void SetNodes(int source, int target)
    {

        this.source = graph.GetNode(source);
        this.target = graph.GetNode(target);
        nodes.clear();
        nodes.add(this.source);
        nodes.add(this.target);
        NameElement = this.source.GetName() + " -> " + this.target.GetName();

    }

    public Boolean ContainsNode(Node node)
    {
        return nodes.contains(node);
    }

    public Link(Graph graph, int source, int target)
    {
        super("");
        SetGraph(graph);
        SetNodes(source, target);
    }

    public Link(Graph graph, int source, int target, float value)
    {
        this(graph, source, target);
        Value = value;
        ValueVisible = true;
    }


}
