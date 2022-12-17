package com.example.lab3_lab4_graphbuilder_sidorov493;

import android.app.Activity;

public class LinkHelper extends GraphHelper{


    Link link;
    public LinkHelper(GraphHelper helper, GraphIdView id) {
        this(helper.ctx, helper.session, helper.graph, id);
    }

    public LinkHelper(GraphHelper helper, Link link, GraphIdView id) {
        this(helper, id);
        this.link = link;
    }

    public LinkHelper(Activity ctx, String session, Graph graph, GraphIdView id) {
        super(ctx, session, graph, id);
    }


    public LinkHelper(Activity ctx, String session, Graph graph, Link link, GraphIdView id) {
        this(ctx, session, graph, id);
        this.link = link;
    }


    public LinkHelper(Activity ctx, String session, Graph graph, Link link) {
        this(ctx, session, graph, link, GraphIdView.graph);
    }
}
