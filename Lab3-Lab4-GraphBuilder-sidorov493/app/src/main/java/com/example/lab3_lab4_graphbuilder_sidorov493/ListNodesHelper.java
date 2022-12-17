package com.example.lab3_lab4_graphbuilder_sidorov493;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListNodesHelper extends GraphHelper {

    public ListNodesHelper(Activity ctx, String session, GraphIdView id) {
        super(ctx, session, id);
        Method = "GET";
    }

    public GraphElement_List graphs = new GraphElement_List();

    public ListNodesHelper(Activity ctx, String session, Graph graph, GraphIdView id) {
        super(ctx, session, graph, id);
        Method = "GET";
    }

    public ListNodesHelper(Activity ctx, String session) {
        super(ctx, session);
        Method = "GET";
    }

    public ListNodesHelper(ApiHelper helper, GraphIdView id) {
        super(helper, id);
        Method = "GET";
    }

    public ListNodesHelper(ApiHelper helper) {
        super(helper);
        Method = "GET";
    }

    @Override
    public void Send() {
        String params = "token="+session+"&"+GetGraphIdView()+"="+graph.IDinAPI;
        send(GrapsParams.Url(ctx)+"node/list", params);
    }


    @Override
    public void on_ready(String res) {
        if(Did)
            return;
        graph.ClearNodes();
        try {
            Did = true;
            JSONArray arrayGraphs = new JSONArray(res);
            for (int i = 0; i < arrayGraphs.length(); i++)
            {
                JSONObject jsonObject = arrayGraphs.getJSONObject(i);
                Node graph = new Node(this.graph);
                graph.IDinAPI = jsonObject.getInt("id");
                graph.X = Float.valueOf(String.valueOf(jsonObject.getDouble("x")));
                graph.Y = Float.valueOf(String.valueOf(jsonObject.getDouble("y")));
                graph.SetName(jsonObject.getString("name"));
                this.graph.AddNode(graph);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Did = true;
        super.on_ready(res);

    }

    public static GraphElement_List GetNodes(Activity ctx, Graph graph)
    {
        ListNodesHelper helper = new ListNodesHelper(ctx, GrapsParams.Session, graph, GraphIdView.graph);
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


        GraphElement_List nodes = new GraphElement_List(graph, GraphElementName.Node);
        return nodes;
    }

}
