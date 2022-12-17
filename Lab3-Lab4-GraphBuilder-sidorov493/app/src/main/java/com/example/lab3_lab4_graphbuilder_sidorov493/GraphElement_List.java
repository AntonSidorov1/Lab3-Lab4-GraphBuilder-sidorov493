package com.example.lab3_lab4_graphbuilder_sidorov493;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class GraphElement_List extends ArrayList<GraphElement> {
    Graph graph;
    public Graph GetGraph()
    {
        return graph;
    }

    public int IdElementFromAPI(int id_api)
    {
        try
        {
            int id = id_api;
            for(int i = 0; i < size(); i++)
            {
                try
                {
                    GraphElement node = get(i);
                    int api = node.IDinAPI;
                    if(api == id)
                        return i;
                }
                catch (Exception ex)
                {

                }
            }
            return  -1;
        }
        catch (Exception ex)
        {
            return -1;
        }
    }

    public GraphElement ElementFromAPI(int id_api) {
        try {
            int id = IdElementFromAPI(id_api);
            GraphElement node = get(id);
            return node;
        } catch (Exception ex) {
            return null;
        }
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

    public void AddElements(GraphElement_List elements)
    {
        if(elements.IsGraph())
            addListGraphs(elements);
        else if(elements.IsNode())
            addListNodes(elements);
        else if(elements.IsLink())
            addListLinks(elements);
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

    public void addListGraphs(GraphElement_List list)
    {
        if(!list.IsGraph())
            return;
        for(int i = 0; i < list.size(); i++)
        {
            add(list.get(i));
        }
        elementName = GraphElementName.Graph;
    }

    public GraphElement_List(GraphElement_List list)
    {
        this();
        if(list.IsGraph())
        addListGraphs(list);
        else if(list.IsNode())
            addListNodes(list);
        else if(list.IsLink())
            addListLinks(list);
    }



    public boolean addListNodes(@NonNull ArrayList<Node> graphs) {
        return super.addAll(graphs);
    }

    public boolean addListNodes(@NonNull GraphElement_List graphs) {
        for(int i = 0; i < graphs.size(); i++)
        {
            GraphElement graphElement = graphs.get(i);
            if(graphElement.IsNode())
                add(graphElement);
        }
        return size() > 0;
    }

    public boolean addListLinks(@NonNull GraphElement_List graphs) {
        for(int i = 0; i < graphs.size(); i++)
        {
            GraphElement graphElement = graphs.get(i);
            if(graphElement.IsLink())
                add(graphElement);
        }
        return size() > 0;
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
