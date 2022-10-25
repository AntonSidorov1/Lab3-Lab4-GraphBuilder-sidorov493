package com.example.lab3_lab4_graphbuilder_sidorov493;

import java.util.ArrayList;

public class Link extends GraphElement {
    Graph graph;
    public float Value = 0.0f;
    public boolean Orientation = false;
    public String Text = "";
    public boolean ValueVisible = false;
    ArrayList<Node> nodes = new ArrayList<>();

    @Override
    public String GetNameFromID() {
        return GetName();
    }

    public void DecrimentAfterID(int id)
    {
        //id++;
        if(sourceID >= id)
            sourceID--;
        if(targetID >= id)
            targetID--;
        SetNodes();
    }


    public void ChangeNode()
    {
        int change = sourceID;
        sourceID = targetID;
        targetID = change;
        SetNodes();
    }

    public  void ChangeOrientationLink()
    {
        ChangeNode();
    }

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
        SetNodes();
        return source;
    }
    public Node Target()
    {
        SetNodes();
        return target;
    }

    public int sourceID, targetID;
    ArrayList<Integer> IDs = new ArrayList<>();

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

    public void SetNodes()
    {
        SetNodes(sourceID, targetID);
    }

    public void SetNodes(int source, int target)
    {

        if(target > graph.NodeCount())
            target = graph.NodeCount()-1;
        if(source == target)
            source = target-1;

        this.source = graph.GetNode(source);
        this.target = graph.GetNode(target);
        nodes.clear();
        nodes.add(this.source);
        nodes.add(this.target);

        IDs.clear();

        sourceID = source;
        targetID = target;
        IDs.add(source);
        IDs.add(target);

        NameElement = this.source.GetName() + " -> " + this.target.GetName();

    }

    public Boolean ContainsNode(Node node)
    {
        return nodes.contains(node);
    }

    public Boolean ContainsNode(int node)
    {
        return IDs.contains(node);
    }

    public Boolean ContainsNodes(Node node1, Node node2)
    {
        return ContainsNode(node1) && ContainsNode(node2);
    }

    public Boolean ContainsNodes(int node1, int node2)
    {
        return ContainsNode(node1) && ContainsNode(node2);
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


    @Override
    public String TypeText() {
        return "Связь (Link)";
    }

    @Override
    public String GetName() {
        String line = "-";
        if(Orientation)
            line+=">";
        String name = source.GetName() + " " + line + " " + target.GetName();
        return name;
    }
}
