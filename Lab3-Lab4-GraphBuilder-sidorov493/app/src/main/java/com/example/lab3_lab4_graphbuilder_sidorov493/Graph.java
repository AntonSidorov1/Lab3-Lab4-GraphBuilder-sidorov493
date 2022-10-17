package com.example.lab3_lab4_graphbuilder_sidorov493;

import java.util.ArrayList;

public class Graph extends GraphElement {

    public void SetName(String name)
    {
        NameElement = name;
    }


    private ArrayList<Node> nodes = new ArrayList<>();
    public Node GetNode(int id)
    {
        return nodes.get(id);
    }

    public Node AddNode(float x, float y, String name)
    {
        Node node = new Node(x, y, name, this);
        nodes.add(node);
        return node;
    }

    public Node InsertNode(int index, float x, float y, String name)
    {
        Node node = new Node(x, y, name, this);
        nodes.add(index, node);
        return node;
    }

    public void DeleteNode(int id)
    {
        Node node = GetNode(id);
        nodes.remove(id);

        for(int i = 0; i< LinkCount(); i++)
        {
            Link link = links.get(i);
            if(link.ContainsNode(node))
            {
                DeleteLink(i);
            }
        }
    }

    public void ClearNodes()
    {
        nodes.clear();
        ClearLinks();
    }

    public void ClearLinks()
    {
        links.clear();
    }

    public Boolean ContainsNode(Node node)
    {

        return nodes.contains(node);
    }

    public int IndexNode(Node node)
    {
        return nodes.indexOf(node);
    }

    public int NodeCount()
    {
        return nodes.size();
    }

    private ArrayList<Link> links = new ArrayList<>();
    public Link GetLink(int id)
    {
        return links.get(id);
    }

    public Link AddLink(int source, int target, float value)
    {
        Link node = new Link(this, source, target, value);
        links.add(node);
        return node;
    }

    public Link InsertLink(int index, int source, int target, float value)
    {
        Link node = new Link(this, source, target, value);
        links.add(index, node);
        return node;
    }

    public void DeleteLink(int id)
    {
        links.remove(id);

    }

    public Boolean ContainsLink(Link node)
    {

        return links.contains(node);
    }

    public int IndexLink(Link node)
    {
        return links.indexOf(node);
    }

    public void DeleteLink(Node node)
    {
        links.remove(node);
    }

    public int LinkCount()
    {
        return links.size();
    }

    public Graph(String name)
    {
        super(name);
    }

    public Graph(String name, int id)
    {
        this(name+String.valueOf(id));
    }

    public Graph(int id)
    {
        this("graph", id);
    }

    public Graph()
    {
        this(GrapsParams.GraphID);
    }

}
