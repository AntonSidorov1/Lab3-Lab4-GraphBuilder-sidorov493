package com.example.lab3_lab4_graphbuilder_sidorov493;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class GraphElement_List extends ArrayList<GraphElement> {
    Graph graph;
    public Graph GetGraph()
    {
        return graph;
    }

    public GraphElementName elementName;

    public void SetGraph(Graph graph, GraphElementName elementName)
    {
        this.graph = graph;
        clear();

        this.elementName = elementName;
        if(elementName == GraphElementName.Node)
            addListNodes(graph.nodes);
        else if(elementName == GraphElementName.Link)
            addListLinks(graph.links);
    }

    public void SetGraph()
    {
        SetGraph(GetGraph());
    }

    public void SetGraph(Graph graph)
    {
        SetGraph(graph, elementName);
    }

    public GraphElement_List()
    {
        super();
    }

    public GraphElement_List(Graph graph, GraphElementName elementName)
    {
        this();
        SetGraph(graph, elementName);
    }

    public GraphElement_List(ArrayList<Graph> graphs)
    {
        this();
        elementName = GraphElementName.Graph;
        addListGraphs(graphs);

    }

    public boolean addListNodes(@NonNull ArrayList<Node> graphs) {
        return super.addAll(graphs);
    }

    public boolean addListLinks(@NonNull ArrayList<Link> graphs) {
        return super.addAll(graphs);
    }

    public boolean addListGraphs(@NonNull ArrayList<Graph> graphs) {
        return super.addAll(graphs);
    }

    public boolean addListGraphElements(@NonNull ArrayList<GraphElement> graphs) {
        return super.addAll(graphs);
    }

    public boolean addListNodes(int index, @NonNull ArrayList<Link> graphs) {
        return super.addAll(index, graphs);
    }

    public boolean addListLinks(int index, @NonNull ArrayList<Link> graphs) {
        return super.addAll(index, graphs);
    }

    public boolean addListGraphs(int index, @NonNull ArrayList<Graph> graphs) {
        return super.addAll(index, graphs);
    }

    public boolean addListGraphElements(int index, @NonNull ArrayList<GraphElement> graphs) {
        return super.addAll(index, graphs);
    }

    public boolean IsGraph()
    {
        try {
            if(size() == 0)
                throw  new Exception();
            boolean graph = true;
            for (int i = 0; i < size(); i++) {
                graph = graph && get(i).IsGraph();
            }
            return graph;
        }
        catch(Exception ex)
        {
            return elementName == GraphElementName.Graph;
        }
    }

    public boolean IsNode()
    {
        try {
            if(size() == 0)
                throw  new Exception();
            boolean graph = true;
            for (int i = 0; i < size(); i++) {
                graph = graph && get(i).IsNode();
            }
            return graph;
        }
        catch(Exception ex)
        {
            return elementName == GraphElementName.Node;
        }
    }

    public boolean IsLink()
    {
        try {
            if(size() == 0)
                throw  new Exception();
            boolean graph = true;
            for (int i = 0; i < size(); i++) {
                graph = graph && get(i).IsLink();
            }
            return graph;
        }
        catch(Exception ex)
        {
            return elementName == GraphElementName.Link;
        }
    }

    public String GetName()
    {
        if(IsNode())
        {
            return "Узлы (Nodes)";
        }
        else if(IsLink())
        {
            return "Связи/Рёбра (Links)";
        }
        else if(IsGraph())
        {
            return "Графы (Graphs)";
        }
        else
        {
            return "";
        }
    }

    public GraphElement add()
    {
        if(IsLink())
            return new Link(graph);
        else if (IsNode())
            return new Node(graph);
        else if (IsGraph())
            return new Graph();
        else
            return null;
    }

}
