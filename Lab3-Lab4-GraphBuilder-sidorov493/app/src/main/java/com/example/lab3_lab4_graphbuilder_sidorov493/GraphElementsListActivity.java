package com.example.lab3_lab4_graphbuilder_sidorov493;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class GraphElementsListActivity extends AppCompatActivity {

    Button exit;
    Graph graph;
    TextView typeText;
    GraphElement_List graphs;
    ListView elementList;
    Boolean run1 = true, run2 = true;
    Boolean add;

    ArrayList<Session> Sessions;
    ArrayAdapter<Session> SessionsAdapter;

    Button AddElement;

    public Graph Graph()
    {
        return graphs.GetGraph();
    }

    Thread thread;

    void update_API()
    {
        //for(int i = 0;  i < 20; i++)
        while (run1)
        {
            final Boolean[] run = {true};
            while (run[0]) {

                try {
                    try {
                        if(!run1)
                            break;
                    }
                    catch(Exception ex)
                    {

                    }
                    if(run2) {
                        GetActivity().runOnUiThread(() ->
                        {
                            if(run2) {
                                try {

                                    update_graphs();
                                } catch (Exception ex) {
                                    run[0] = false;
                                }
                            }

                        });
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        run[0] = run1;
                        if(add && run2) {
                            add = false;
                        }
                    }
                } catch (Exception ex) {
                    break;
                }

            }
        }
        try {
            GetActivity().runOnUiThread(() ->
            {
                try {
                    if (!GrapsParams.HaveSession(GetActivity())) {
                        Intent intent = getIntent();
                        setResult(500, intent);
                        finish();
                        return;
                    }
                }
                catch(Exception ex)
                {

                }
            });
        }
        catch (Exception ex)
        {

        }
    }

    Activity GetActivity()
    {
        return this;
    }

    ArrayAdapter<GraphElement> list;

    Boolean node, graph1, link;

    void update_graphs() {

        if(!GrapsParams.SessionsList) {
            GraphElement_List graphs1 = GrapsParams.graphList;
            GrapsParams.graphs.clear();
            GrapsParams.graphList.clear();
            if (!GrapsParams.API) {
                if (graphs.IsGraph() || graph1)
                    GrapsParams.graphs = new GraphElement_List(GrapsParams.DB.GetListGraphs());

            } else {

                if (graph1)
                    GrapsParams.graphs = GraphListHelper.GetGraphs(this);
                else {
                    if (node) {
                        GrapsParams.graphs = ListNodesHelper.GetNodes(this, GrapsParams.GraphAPI);
                    } else if (link) {
                        GrapsParams.graphs = ListLinksHelper.GetLinks(this, GrapsParams.GraphAPI);
                    }
                }
            }

            GrapsParams.graphList = new GraphElement_List(GrapsParams.graphs);
            graphs.clear();
            if (graph1 || !GrapsParams.API)
                graphs.addListGraphs(GrapsParams.graphList);
            else {
                graphs.AddElements(GrapsParams.graphList);
            }
        }
        else
        {
            Sessions.clear();
            Sessions.addAll(ListSessionsHelper.GetSessions(this));
        }
        update_list();

    }

    void update_list()
    {

        if(!GrapsParams.SessionsList) {
            if (!graphs.IsGraph() && !graph1) {
                if (!GrapsParams.API)
                    graphs.SetGraph(graph);
                else {


                }
            }
            list.notifyDataSetChanged();
        }
        else
        {

            SessionsAdapter.notifyDataSetChanged();
        }

    }

    public Context GetContext()
    {
        return  this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_elements_list);
        Boolean graphYes = GrapsParams.graphs.IsGraph();

        AddElement = findViewById(R.id.AddElement);
        add = false;

        if(GrapsParams.API && !graphYes)
        {
           // GrapsParams.GraphAPI = GrapsParams.GraphElement.GetGraph();
        }


        if(GrapsParams.API)
        {
            if(!GrapsParams.HaveSession(this))
            {
                Intent intent = getIntent();
                setResult(500, intent);
                finish();
                return;
            }
        }

        if(GrapsParams.SessionsList)
        {
            AddElement.setVisibility(View.GONE);
        }

        Runnable run = new Runnable() {
            @Override
            public void run() {
                update_API();
            }
        };
        thread = new Thread(run);

        if(!GrapsParams.SessionsList) {
            graphs = GrapsParams.graphList;
            try {

                node = GrapsParams.ElementName == GraphElementName.Node;
                link = GrapsParams.ElementName == GraphElementName.Link;
                graph1 = GrapsParams.ElementName == GraphElementName.Graph;
                if (node == graph1 && link == graph1) {
                    throw new Exception();
                }
                if (node == graph1 && node == true) {
                    throw new Exception();
                }
                if (link == graph1 && link == true) {
                    throw new Exception();
                }
                if (node == link) {
                    throw new Exception();
                }
            } catch (Exception ex) {
                node = false;
                link = false;
                graph1 = true;
            }
            graph = graphs.GetGraph();
        }
        exit = findViewById(R.id.CloseEditorElements);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Exit_Click(view);
            }
        });
        typeText = findViewById(R.id.TypeText);
        if(!GrapsParams.SessionsList) {
            typeText.setText(graphs.GetName());
        }
        else
        {
            typeText.setText("Сессии (Текущая сессия - "+GrapsParams.Session+")");
        }

        elementList = findViewById(R.id.listElements1);
        if(!GrapsParams.SessionsList) {
            list = new ArrayAdapter<GraphElement>(this, android.R.layout.simple_list_item_1, graphs);
            elementList.setAdapter(list);
            elementList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    run2 = false;
                    Intent intent = new Intent(GetContext(), GraphElementEdit.class);
                    GrapsParams.GraphElement = graphs.get(i);
                    if (GrapsParams.API) {
                        if (!GrapsParams.HaveSession(GetActivity())) {
                            Intent intent1 = getIntent();
                            setResult(500, intent1);
                            finish();
                            return;
                        }
                        //thread.stop();
                        if (GrapsParams.GraphElement.IsGraph()) {
                            Graph graph = GrapsParams.GraphElement.Graph();
                            GrapsParams.CreateGraph(GetActivity(), graph);
                            GrapsParams.NowGraph = graph;
                            GrapsParams.Run_Graph = true;


                            Intent intent1 = new Intent(GetContext(), GraphEdit2.class);
                            startActivityForResult(intent1, 100);
                            return;
                        }
                        if (GrapsParams.GraphElement.IsLink()) {
                            Link link = GrapsParams.GraphElement.Link();
                            source = link.IDsourceAPI();
                            target = link.IDtargetAPI();
                        }
                        startActivityForResult(intent, 100);
                        return;
                    } else if (GrapsParams.GraphElement.IsGraph()) {
                        Graph graph = GrapsParams.GraphElement.Graph();
                        GrapsParams.NowGraph = graph;
                        GrapsParams.Run_Graph = true;
                        graph.ClearNodes();
                        GrapsParams.DB.GetListNodes(graph);
                        GrapsParams.DB.GetListLinks(graph);
                        Intent intent1 = new Intent(GetContext(), GraphEdit2.class);
                        startActivityForResult(intent1, 100);
                        return;
                    }
                    startActivityForResult(intent, 100);
                }
            });

            if (GrapsParams.graphList.IsGraph())
                update_graphs();
            else
                update_list();
        }
        else
        {
            Sessions = new ArrayList<>();
            SessionsAdapter = new ArrayAdapter<Session>(this, android.R.layout.simple_list_item_1, Sessions);
            elementList.setAdapter(SessionsAdapter);
            elementList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!GrapsParams.HaveSession(GetActivity())) {
                        Intent intent1 = getIntent();
                        setResult(500, intent1);
                        finish();
                        return;
                    }
                    Session session = Sessions.get(i);
                    SessionView(session);
                }
            });
        }
        if(GrapsParams.API)
        thread.start();
    }

    public void SessionView(Session session)
    {
        run2 = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                run2 = true;
            }
        });
        builder.setNegativeButton("Закрыть", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!GrapsParams.HaveSession(GetActivity()))
                {
                    Intent intent = getIntent();
                    setResult(500, intent);
                    finish();
                    return;
                }

                CloseSession close = new CloseSession(GetActivity(), session.Key);
                close.SendStop();

                if(!close.Ready)
                {
                    Intent intent = getIntent();
                    setResult(500, intent);
                    finish();
                    return;
                }
                else
                {
                    SessionCloseView(session);
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setMessage(" Сессия - "+session.Key+"\n Дата открытия - "+session.GetDatetime());
        dialog.setCancelable(false);
        dialog.show();
    }

    void SessionCloseView(Session session)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(!GrapsParams.HaveSession(GetActivity()))
                {
                    Intent intent = getIntent();
                    setResult(500, intent);
                    finish();
                    return;
                }
                else {
                    run2 = true;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setMessage(" Сессия - "+session.Key+" успешно закрыта");
        dialog.setCancelable(false);
        dialog.show();
    }

    int source, target;

    @Override
    public void finish() {
        try {

            NoRun();
        }
        catch (Exception ex)
        {

        }
        super.finish();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        run2 = false;
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id)
        {
            case R.id.exit: {

                View v = exit;
                Exit_Click(v);
            }
            break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void Exit_Click(View v)
    {

        try {

            thread.stop();
        }
        catch(Exception ex)
        {

        }
        Intent intent = getIntent();
        setResult(554, intent);
        finish();
    }

    void NoRun()
    {
        if(!GrapsParams.SessionsList) {
            GrapsParams.ElementName = GraphElementName.Graph;
        }
        run1 = false;
        run2 = false;
        GrapsParams.SessionsList = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (GrapsParams.API && !GrapsParams.HaveSession(GetActivity())) {
            Intent intent1 = getIntent();
            setResult(500, intent1);
            finish();
            return;
        }
        if (requestCode==555 || resultCode == 555 || requestCode==550 || resultCode == 550) // Проверяем код результата (2-ая Activity была запущена с кодом 555)
        {
            if (data != null && !GrapsParams.API) // Вернула ли значение вторая Activity нам Intent с данными, или, просто, закрылась
            {
                int id = GrapsParams.elementID();
                if(GrapsParams.graphList.IsNode())
                {
                    if(id < 0) {
                        id = graph.AddNode().id();
                    }
                    graph.SetNode(id, GrapsParams.GraphElement.Node());
                }
                else if (GrapsParams.graphList.IsLink())
                {
                    if(id < 0) {
                        try {

                            Link link1 = GrapsParams.GraphElement.Link();
                            Link link = graph.AddLink(link1);
                            //link.Orientation = link1.Orientation;
                            //link.Value = link1.Value;
                            //link.Text = link1.Text;
                            //link.ValueVisible = link1.ValueVisible;
                            //link.TextVisible = link1.TextVisible;
                        }
                        catch(Exception ex)
                        {

                        }
                    }
                    else
                    {
                        try {

                            Link link1 = GrapsParams.GraphElement.Link();
                            Link link = graph.SetLink(id, link1);
                            //link.Orientation = link1.Orientation;
                            //link.Value = link1.Value;
                            //link.Text = link1.Text;
                            //link.ValueVisible = link1.ValueVisible;
                            //link.TextVisible = link1.TextVisible;
                        }
                        catch(Exception ex)
                        {

                        }
                    }
                }
                else if(GrapsParams.graphList.IsGraph())
                {
                    Graph graph = GrapsParams.GraphElement.Graph();
                    GrapsParams.DB.upload_graph(graph);
                    update_graphs();
                    return;
                }
            }
            else if(GrapsParams.API)
            {

                if(GrapsParams.GraphElement.IsNode()) {
                    Node n = GrapsParams.GraphElement.Node();
                    if(add)
                    {
                        CreateNode.NodeCreate(this, GrapsParams.GraphAPI, n);
                        run2 = true;
                        return;
                    }

                    String url = GrapsParams.GetUrl(this) + "node/update";
                    ApiHelper helper = new ApiHelper(this) {
                        @Override
                        public void send(String req, String payload) {
                            Method = "POST";
                            super.send(req, payload);
                        }
                    };

                        helper.SendStop(url, "token=" + GrapsParams.Session + "&id=" + n.IDinAPI + "&x=" + n.X + "&y=" + n.Y + "&name=" + n.GetName());

                }
                else if (GrapsParams.GraphElement.IsLink()) {
                    String url = GrapsParams.GetUrl(this) + "link/update";
                    Link n = GrapsParams.GraphElement.Link();
                    if(add)
                    {
                        CreateLink.LinkCreate(this, GrapsParams.GraphAPI, n);
                        run2 = true;
                        return;
                    }
                    ApiHelper helper = new ApiHelper(this) {
                        @Override
                        public void send(String req, String payload) {
                            //Method = "POST";
                            super.send(req, payload);
                        }
                    };
                    helper.Method = "POST";
                    try {
                        if(n.IDtargetAPI() == target && n.IDsourceAPI() == source)
                            throw  new Exception();
                        url = GrapsParams.GetUrl(this)+"link/delete";
                        helper.Method = "DELETE";
                        helper.SendStop(url,"token=" + GrapsParams.Session + "&id=" + n.IDinAPI);
                        //"token=" + GrapsParams.Session + "&id=" + n.IDinAPI
                        //"token=" + GrapsParams.Session + "&id=" + n.IDinAPI
                        if(helper.Ready)
                            CreateLink.LinkCreate(GetActivity(), graph, n);
                    } catch (Exception ex) {
                        helper.SendStop(url, "token=" + GrapsParams.Session + "&id=" + n.IDinAPI + "&value=" + n.Value);
                    }
                }
                run2 = true;
            }
        }
        else if(requestCode==556|| resultCode == 556)
        {
            try {

                if (data != null) {
                    int id = GrapsParams.elementID();
                    if (GrapsParams.GraphElement.IsNode()) {
                        if(!GrapsParams.API)
                        graph.DeleteNode(id);
                        else
                        {
                            Node n = GrapsParams.GraphElement.Node();
                            String url = GrapsParams.GetUrl(this)+"node/delete";
                            ApiHelper helper = new ApiHelper(this)
                            {
                                @Override
                                public void send(String req, String payload) {
                                    Method = "DELETE";
                                    super.send(req, payload);
                                }
                            };
                            helper.SendStop(url,"token="+GrapsParams.Session+"&id="+n.IDinAPI);
                            run2 = true;
                        }
                    } else if(GrapsParams.GraphElement.IsLink()) {
                        if(!GrapsParams.API)
                        graph.DeleteLink(id);
                        else
                        {
                            Link n = GrapsParams.GraphElement.Link();
                            String url = GrapsParams.GetUrl(this)+"link/delete";
                            ApiHelper helper = new ApiHelper(this)
                            {
                                @Override
                                public void send(String req, String payload) {
                                    Method = "DELETE";
                                    super.send(req, payload);
                                }
                            };
                            helper.SendStop(url,"token="+GrapsParams.Session+"&id="+n.IDinAPI);
                            run2 = true;
                        }
                    }
                    else if(GrapsParams.GraphElement.IsGraph())
                    {
                        Graph graph = GrapsParams.GraphElement.Graph();
                        if(!GrapsParams.API) {

                            GrapsParams.DB.delete_graph(graph);
                            update_graphs();
                        }
                        else
                        {
                            String url = GrapsParams.GetUrl(this)+"graph/delete";
                            ApiHelper helper = new ApiHelper(this)
                            {
                                @Override
                                public void send(String req, String payload) {
                                    Method = "DELETE";
                                    super.send(req, payload);
                                }
                            };
                            helper.send(url,"token="+GrapsParams.Session+"&graph="+graph.IDinAPI);
                            try {
                                helper.th.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if(!helper.Ready)
                            {
                                helper.send(url,"token="+GrapsParams.Session+"&id="+graph.IDinAPI);
                                try {
                                    helper.th.join();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            run2 = true;
                        }
                        return;
                    }
                }
            }
            catch(Exception ex)
            {

            }
        }
        if(GrapsParams.graphList.IsGraph() || graph1) {
            if(!GrapsParams.API) {
                update_graphs();
            }
            else {
                run2 = true;
            }
        }
        else
            update_list();
        if(GrapsParams.API)
            run2 = true;


        super.onActivityResult(requestCode,resultCode,data);
    }

    public void AddElements(View v)
    {
        run2 = false;
        add = true;
        Intent i =new Intent(this, GraphElementEdit.class);
        GrapsParams.GraphElement = graphs.add();
        if(GrapsParams.GraphElement.IsGraph())
        {
            Graph graph = GrapsParams.GraphElement.Graph();
            GrapsParams.NowGraph = graph;
            GrapsParams.Run_Graph = true;
            graph.ClearNodes();
            if(!GrapsParams.API) {
                GrapsParams.DB.GetListNodes(graph);
                GrapsParams.DB.GetListLinks(graph);
            }
            else
            {
                CreateGraph.GraphCreate(this, graph);
            }
            Intent intent1 =new Intent(GetContext(), GraphEdit2.class);
            startActivityForResult(intent1, 100);
            return;
        }
        startActivityForResult(i, 100);
    }

    public void PropertyGraph(View v)
    {

    }


}