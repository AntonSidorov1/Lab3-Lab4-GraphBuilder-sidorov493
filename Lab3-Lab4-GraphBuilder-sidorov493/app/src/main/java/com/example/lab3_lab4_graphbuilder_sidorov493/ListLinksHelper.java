package com.example.lab3_lab4_graphbuilder_sidorov493;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListLinksHelper extends GraphHelper {
    public ListLinksHelper(Activity ctx, String session, GraphIdView id) {
        super(ctx, session, id);
        Method = "GET";
    }

    public ListLinksHelper(Activity ctx, String session, Graph graph, GraphIdView id) {
        super(ctx, session, graph, id);
        Method = "GET";
    }

    public ListLinksHelper(Activity ctx, String session) {
        super(ctx, session);
        Method = "GET";
    }

    public ListLinksHelper(ApiHelper helper, GraphIdView id) {
        super(helper, id);
        Method = "GET";
    }

    public ListLinksHelper(ApiHelper helper) {
        super(helper);
        Method = "GET";
    }


    @Override
    public void Send() {
        String params = "token="+session+"&"+GetGraphIdView()+"="+graph.IDinAPI;
        send(GrapsParams.Url(ctx)+"link/list", params);
    }

    @Override
    public void on_ready(String res) {
        if(Did)
            return;
        graph.ClearLinks();
        try {
            JSONArray arrayGraphs = new JSONArray(res);
            for (int i = 0; i < arrayGraphs.length(); i++)
            {
                Did = true;
                JSONObject jsonObject = arrayGraphs.getJSONObject(i);
                Link graph = new Link(this.graph);
                int id = jsonObject.getInt("id");
                int source = jsonObject.getInt("source");
                int target = jsonObject.getInt("target");
                source = this.graph.IdNodeFromAPI(source);
                target = this.graph.IdNodeFromAPI(target);
                graph.SetNodes(source, target);
                graph.Value = Float.valueOf(String.valueOf(jsonObject.getDouble("value")));
                graph.ValueVisible = true;
                graph.Orientation = true;
                graph.IDinAPI = id;
                this.graph.AddLink(graph);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Did = true;

        super.on_ready(res);
    }


    public static GraphElement_List GetLinks(Activity ctx, Graph graph)
    {
        ListLinksHelper helper = new ListLinksHelper(ctx, GrapsParams.Session, graph, GraphIdView.graph);
        helper.SendStop();
        if(helper.Ready)
            helper.on_ready();
        else
        {
            helper.ChangeGraphIdView();
            helper.SendStop();
            if(helper.Ready)
                helper.on_ready();
        }
        graph = helper.graph;
        GraphElement_List links = new GraphElement_List(graph, GraphElementName.Link);
        return links;
    }


}
