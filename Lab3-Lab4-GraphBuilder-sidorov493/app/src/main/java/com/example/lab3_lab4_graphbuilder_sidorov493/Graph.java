package com.example.lab3_lab4_graphbuilder_sidorov493;

import java.util.ArrayList;

public class Graph extends GraphElement {

    public void SetName(String name)
    {
        NameElement = name;
    }


    public ArrayList<Node> nodes = new ArrayList<>();
    public Node GetNode(int id)
    {
        return nodes.get(id);
    }

    public Node AddNode(float x, float y, String name)
    {
        Node node = new Node(x, y, name, this);
        //nodes.add(node);
        AddNode(node);
        return node;
    }

    public Node AddNode()
    {
        return AddNode(new Node(this));
    }

    public Node AddNode(float x, float y)
    {
        Node node = new Node(x, y, this);
        //nodes.add(node);
        AddNode(node);
        node.SetNameFromID();
        return node;
    }

    public Node AddNode(Node node1)
    {
        Node node = node1;
        node1.SetGraph(this);
        nodes.add(node);
        return node;
    }

    public Node InsertNode(int index, float x, float y, String name)
    {
        Node node = new Node(x, y, name, this);
        InsertNode(index, node);
        return node;
    }

    public Node InsertNode(int index, float x, float y)
    {
        Node node = new Node(x, y, this);
        //nodes.add(index, node);

        InsertNode(index, node);
        return node;
    }

    public Node InsertNode(int index, Node node1)
    {
        Node node = node1;
        node1.SetGraph(this);
        nodes.add(index, node);
        return node;
    }

    public void DeleteNode(int id)
    {

            Node node = GetNode(id);

            int linkCount = LinkCount();
            for (int i = 0; i < linkCount; i++) {
                Link link = GetLink(i);
                if (link.ContainsNode(id)) {
                    DeleteLink(i);
                    linkCount = LinkCount();
                    i--;
                }
                else
                {
                    link.DecrimentAfterID(id);
                }
            }
            nodes.remove(id);

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
        int index = nodes.indexOf(node);
        return index;
    }

    public int NodeCount()
    {
        return nodes.size();
    }

    public ArrayList<Link> links = new ArrayList<>();
    public Link GetLink(int id)
    {
        return links.get(id);
    }

    public Link AddLink(int source, int target, float value)
    {
        Link node = new Link(this, source, target, value);
        if(ContainsLink(node, true))
            return null;
        links.add(node);
        return node;
    }

    public Node SetNode(int index, float x, float y, String name)
    {
        Node node = GetNode(index);
        node.SetNode(x, y, name);
        return node;
    }

    public Node SetNode(int index, Node node1)
    {
        Node node = GetNode(index);
        node.SetNode(node1);
        return node;
    }

    public Graph GetGraph()
    {
        return this;
    }

    public Link SetLink(int index, Link link)
    {
        Graph graph = GetGraph();
        Link l = graph.GetLink(index);
        l.Orientation = link.Orientation;
        l.sourceID = link.sourceID;
        l.targetID = link.targetID;
        l.Text = link.Text;
        l.Value = link.Value;
        l.TextVisible = link.TextVisible;
        l.ValueVisible = link.ValueVisible;
        l.Set_API_ID(link.Get_API_ID());
        for(int i = 0; i < graph.LinkCount(); i++)
        {
            Link l1 = graph.GetLink(i);
            if(i != index)
                if(!l1.Orientation)
                {
                    if(l1.ContainsNodes(l.sourceID, l.targetID))
                    {
                        DeleteLink(index);
                        return null;
                    }
                    else
                    {

                    }
                }
                else
                {
                    if(l1.sourceID == l.sourceID && l1.targetID == l.targetID)
                    {
                        DeleteLink(index);
                        return  null;
                    }
                }
        }
        return  l;
    }

    public int IdNodeFromApi(int api)
    {
        for(int i = 0; i< NodeCount(); i++)
        {
            if(GetNode(i).Get_API_ID() == api)
                return i;
        }
        return  -1;
    }

    public Link AddLink(int source, int target, float value, Boolean orientation)
    {
        Link node = new Link(this, source, target, value);
        node.Orientation = orientation;
        if(ContainsLink(node, true))
            return null;
        links.add(node);
        return node;
    }

    public Link AddLink(int source, int target)
    {
        Link node = new Link(this, source, target);
        if(ContainsLink(node, true))
            return null;
        links.add(node);
        return node;
    }

    public Link AddLink(int source, int target, boolean orientation)
    {
        Link node = new Link(this, source, target);
        node.Orientation = orientation;
        if(ContainsLink(node, true))
            return null;
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
        return ContainsLink(node, false);
    }

    public Boolean ContainsLink(Link node, boolean add)
    {
        if(!add)
        return links.contains(node);
        else
        {
            return ContainsLink(node.sourceID, node.targetID, node.Orientation);
        }
    }

    public Boolean ContainsLink(int source, int target, Boolean orientation)
    {
        for(int i = 0; i < LinkCount(); i++)
        {
            Link link = GetLink(i);
            if(!orientation)
            {
                if(link.ContainsNodes(GetNode(source), GetNode(target)))
                    return  true;
            }
            else
            {
                if(link.sourceID == source && link.targetID == target)
                    return true;
            }
        }

        return false;
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


    @Override
    public String TypeText() {
        return "Граф (Graph)";
    }

    @Override
    public int ID() {
        return Get_API_ID();
    }

    @Override
    public String GetNameFromID() {
        return "graph"+String.valueOf(id());
    }

    @Override
    public GraphElement CopyElement() {
        Graph graph = new Graph(GetName());

        for(int i = 0; i < NodeCount(); i++)
        {
            graph.AddNode(GetNode(i).CopyElement().Node());
        }

        for(int i = 0; i < LinkCount(); i++)
        {
            graph.AddLink(GetLink(i).CopyElement().Link());
        }

        return graph;
    }

    public boolean HaveNode(int index)
    {
        return index >-1 && index < NodeCount();
    }

    public GraphElement_List GetList(GraphElementName name)
    {
        return new GraphElement_List(this, name);
    }

    public Link AddLink(Link link)
    {
        Link result = AddLink(link.sourceID, link.targetID, link.Orientation);
        result = SetLink(result.id(), link);
        return result;
    }

    @Override
    public GraphElement CopyElement(Graph graph) {
        graph.ClearNodes();
        for(int i = 0; i < NodeCount(); i++)
        {
            graph.AddNode(GetNode(i).CopyElement().Node());
        }

        for(int i = 0; i < LinkCount(); i++)
        {
            graph.AddLink(GetLink(i).CopyElement().Link());
        }

        return graph;
    }

}
