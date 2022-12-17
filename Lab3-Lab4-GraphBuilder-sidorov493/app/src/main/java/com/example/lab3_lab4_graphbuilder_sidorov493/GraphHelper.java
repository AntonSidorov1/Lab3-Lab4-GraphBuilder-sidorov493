package com.example.lab3_lab4_graphbuilder_sidorov493;

import android.app.Activity;
import android.se.omapi.Session;

public class GraphHelper extends ApiHelper{
    public Graph graph;
    public Boolean MakeGraphID = true;

    public GraphIdView id = GraphIdView.graph;
    public String GetGraphIdView()
    {
        return id.toString();
    }

    public Boolean IsGraph()
    {
        return id == GraphIdView.graph;
    }

    public void ChangeGraphIdView()
    {
        if(IsGraph())
            id = GraphIdView.id;
        else
            id = GraphIdView.graph;
    }

    public void GraphChangeId()
    {
        if(IsGraph())
            ChangeGraphIdView();
    }

    public GraphHelper(Activity ctx, String session, GraphIdView id) {
        super(ctx);
        this.graph = graph;
        this.session = session;
        Method = "PUT";
    }

    public GraphHelper(Activity ctx, String session, Graph graph, GraphIdView id) {
        this(ctx, session, id);
        this.graph = graph;
    }

    public GraphHelper(Activity ctx, String session) {
        this(ctx, session, GraphIdView.graph);
    }

    public GraphHelper(ApiHelper helper, GraphIdView id)
    {
        this(helper.ctx, helper.session, id);
    }


    public GraphHelper(ApiHelper helper)
    {
        this(helper, GraphIdView.graph);
    }


    public void Send()
    {

    }

    public Boolean Did = false;

    @Override
    public void on_ready(String res) {
        super.on_ready(res);
        Run1(true);
        Run();

    }

    public GraphHelper GetGraph()
    {
        return  this;
    }


    @Override
    public void on_fail() {

            super.on_fail();
            Run1(false);
            ctx.runOnUiThread(()->{
                Run();
            });
    }

    public void Run()
    {

    }

    public void Run1(Boolean ready)
    {

    }

}
