package com.example.lab3_lab4_graphbuilder_sidorov493;

import android.app.Activity;

import org.json.JSONException;

public class CreateNode extends NodeHelper {

    public CreateNode(GraphHelper graphHelper, Node node, GraphIdView id) {
        super(graphHelper, node);
        this.id = id;
    }

    public CreateNode(Activity ctx, Graph graph, String Session, Node node) {
        super(ctx, graph, Session, node);
    }

    public CreateNode(GraphHelper graphHelper) {
        super(graphHelper);
    }

    @Override
    public void on_ready(String res) {

        try {

            node.IDinAPI = IntFromJsonObject(res, "id");
        } catch (JSONException e) {
            node.IDinAPI = Integer.getInteger("a");
        }

        super.on_ready(res);
    }



    @Override
    public void Send() {
        String params = "token="+session+"&"+GetGraphIdView()+"="+graph.IDinAPI;
        params+="&x="+node.X+"&y="+node.Y+"&name="+node.GetName();
        send(GrapsParams.Url(ctx)+"node/create", params);
    }

    public static void NodeCreate(Activity ctx, Graph graph, Node node)
    {
        CreateNode create = new CreateNode(ctx, graph, GrapsParams.Session, node);
        create.SendStop();
        if(!create.Ready)
        {
            create.ChangeGraphIdView();
            create.SendStop();
        }
    }
}
