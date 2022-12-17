package com.example.lab3_lab4_graphbuilder_sidorov493;

import android.app.Activity;

import org.json.JSONException;

public class CreateGraph extends GraphHelper{

    public CreateGraph(ApiHelper helper, Graph graph, GraphIdView id) {
        super(helper, id);
        this.graph = graph;
        Method = "PUT";
        MakeGraphID = false;
    }

    public CreateGraph(ApiHelper helper, Graph graph) {
        this(helper, graph, GraphIdView.graph);
    }

    public CreateGraph(Activity ctx, String session, Graph graph, GraphIdView id) {
        super(ctx, session, id);
        this.graph = graph;
        Method = "PUT";
        MakeGraphID = false;
    }

    public CreateGraph(Activity ctx, String session, Graph graph) {
        this(ctx, session, graph, GraphIdView.graph);
    }

    Boolean SetBool(Boolean[] bool1, Boolean[] bool2)
    {
        Boolean result = true;
        for(int i = 0; i < bool1.length; i++)
        {
            result = result && bool1[i];
            if(!result)
                break;
        }
        if(result)
        {
            for(int i = 0; i < bool2.length; i++)
            {
                result = result && bool2[i];
                if(!result)
                    break;
            }
        }

        return result;
    }

    public void SetFalse(Boolean[] bool)
    {
        for(int i = 0; i < bool.length; i++)
        {
            bool[i] = false;
        }
    }

    @Override
    public void on_ready(String res) {
        try {

            graph.IDinAPI = IntFromJsonObject(res, "id");
        } catch (JSONException e) {
            graph.IDinAPI = Integer.getInteger("a");
        }



        for (int i = 0; i < graph.NodeCount(); i++) {


            Node node = graph.GetNode(i);
            int finalI = i;
            CreateNode node1 = new CreateNode(this, node, GraphIdView.graph);
            node1.Send();
            try {
                node1.th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(node1.Ready)
                node1.on_ready();

            CreateNode node2 = new CreateNode(this, node, GraphIdView.id);
            node2.Send();
            try {
                node2.th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(node2.Ready)
                node2.on_ready();
        }

        for(int i = 0; i < graph.LinkCount(); i++)
        {
            Link link = graph.GetLink(i);
            int source = link.sourceID;
            int target = link.targetID;
            link.SetNodes(source, target);
            CreateLink link1 = new CreateLink(this, link);
            link1.Send();
            try {
                link1.th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        BaseReady(res);

    }



    public void BaseReady(String res)
    {
        super.on_ready(res);
    }

    @Override
    public String GetMessageFatal() {
        return "Не удалось сохранить граф";
    }

    @Override
    public String GetMessageReady() {
        return "Граф успешно сохранён";
    }


    public void Send()
    {
        send(GrapsParams.Url(ctx)+"graph/create", "token="+session+"&name="+graph.GetName());
    }

    public static void GraphCreate(Activity ctx, Graph graph)
    {
        CreateGraph graph1 = new CreateGraph(ctx, GrapsParams.Session, graph);
        graph1.SendStop();

    }

    public  static  void PastGraph(Activity ctx, Graph graph)
    {
        Graph graph1 = graph.CopyElement().Graph();
        graph1.ClearNodes();
        ListNodesHelper.GetNodes(ctx, graph1);
        for(int i = 0; i < graph1.NodeCount(); i++) {
            Node node = graph1.GetNode(i);
            ApiHelper api = new ApiHelper(ctx);
            api.Method = "DELETE";
            String url = GrapsParams.GetUrl(ctx) + "node/delete";
            api.SendStop(url, "token=" + GrapsParams.Session + "&id=" + node.IDinAPI);
        }
        GraphCreate(ctx, graph);
    }
}
