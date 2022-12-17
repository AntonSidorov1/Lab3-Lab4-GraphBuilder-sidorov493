package com.example.lab3_lab4_graphbuilder_sidorov493;

import android.app.Activity;

public class NodeHelper extends GraphHelper{

    public Node node;

    public NodeHelper(Activity ctx, String session, Graph graph, GraphIdView id) {
        super(ctx, session, graph, id);
        this.graph = graph;
        Method = "PUT";
    }

    public NodeHelper(Activity ctx, String session, Graph graph) {
        this(ctx, session, graph, GraphIdView.graph);
    }

    public NodeHelper(Activity ctx, Graph graph, String session, Node node) {
        this(ctx, session, graph, GraphIdView.graph);
        this.node = node;
    }

    public  NodeHelper(GraphHelper graphHelper)
    {
        this(graphHelper.ctx, graphHelper.session, graphHelper.graph, graphHelper.id);
    }

    public  NodeHelper(GraphHelper graphHelper, Node node)
    {
        this(graphHelper.ctx, graphHelper.session, graphHelper.graph, graphHelper.id);
        this.node = node;
    }

}
