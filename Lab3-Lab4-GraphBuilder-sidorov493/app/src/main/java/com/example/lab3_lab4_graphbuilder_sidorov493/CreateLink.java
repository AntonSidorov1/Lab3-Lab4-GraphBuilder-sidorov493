package com.example.lab3_lab4_graphbuilder_sidorov493;

import android.app.Activity;

import org.json.JSONException;

public class CreateLink extends LinkHelper{
    public CreateLink(GraphHelper helper, Link link) {
        super(helper, link, GraphIdView.graph);
    }

    public CreateLink(Activity ctx, String session, Graph graph, Link link)
    {
        super(ctx, session, graph, link);
    }

    @Override
    public void on_ready(String res) {

        try {

            link.IDinAPI = IntFromJsonObject(res, "id");
        } catch (JSONException e) {
            link.IDinAPI = Integer.getInteger("a");
        }

        super.on_ready(res);
    }

    public static void LinkCreate(Activity ctx, Graph graph, Link link)
    {
        CreateLink createLink = new CreateLink(ctx, GrapsParams.Session, graph, link);
        createLink.SendStop();
        if(createLink.Ready)
            createLink.on_ready();
    }


    @Override
    public void Send() {

        float value = 0;
        try
        {
            if(link.ValueVisible)
                value = link.Value;
        }
        catch (Exception ex)
        {

        }

        Node source = link.Source();
        int sourceID = source.IDinAPI;
        Node target = link.Target();
        int targetID = target.IDinAPI;
        String params = "token="+session;
        params+="&source="+link.IDsourceAPI()+"&target="+link.IDtargetAPI()+"&value="+value;
        send(GrapsParams.Url(ctx)+"link/create", params);
    }
}
