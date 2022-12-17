package com.example.lab3_lab4_graphbuilder_sidorov493;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GraphListHelper extends GraphHelper {

    public GraphListHelper(Activity ctx, String session) {
        super(ctx, session);
        Method = "GET";
    }

    public GraphListHelper(ApiHelper helper) {
        this(helper.ctx, helper.session);
    }

    GraphElement_List graphs = new GraphElement_List();

    public static GraphElement_List GetGraphs(Activity ctx)
    {
        GraphListHelper helper = new GraphListHelper(ctx, GrapsParams.Session);
        helper.Send();

        try {
            helper.th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        helper.on_ready();
        return new GraphElement_List(helper.graphs);
    }

    @Override
    public void on_ready(String res) {
        try {
            JSONArray arrayGraphs = new JSONArray(res);
            for (int i = 0; i < arrayGraphs.length(); i++)
            {
                JSONObject jsonObject = arrayGraphs.getJSONObject(i);
                Graph graph = new Graph();
                graph.IDinAPI = jsonObject.getInt("id");
                graph.nodesAPI = jsonObject.getInt("nodes");
                graph.SetName(jsonObject.getString("name"));
                graph.SetTimeStamp(jsonObject.getLong("timestamp"));
                graphs.add(graph);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        super.on_ready(res);
    }

    @Override
    public void Send() {
        send(GrapsParams.Url(ctx)+"graph/list", "token="+session);
    }
}
