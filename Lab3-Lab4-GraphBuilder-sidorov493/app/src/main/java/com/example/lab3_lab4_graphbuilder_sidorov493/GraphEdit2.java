package com.example.lab3_lab4_graphbuilder_sidorov493;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class GraphEdit2 extends AppCompatActivity {

    Graph graphCopy;
    Button exit;
    GraphView graphs;
    LinearLayout panelGraphs;
    TextView GraphNameView;

    Boolean run = true, run1 = true, run2 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_edit2);

        exit = findViewById(R.id.ButtonBack1);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Exit_Click(view);
            }
        });
        GraphNameView = findViewById(R.id.GraphNameView);

        //graphs = findViewById(R.id.GraphsPanel);
        graphs = new GraphView(this)
        {
            @Override
            public void NameView() {
                GraphNameView.setText(GetName());
            }

            @Override
            public void Save() {
                if(!GrapsParams.API) {
                    GrapsParams.DB.upload_graph(GetGraph());

                    GrapsParams.graphs = new GraphElement_List(GrapsParams.DB.GetListGraphs());
                    GrapsParams.graphList = new GraphElement_List(GrapsParams.graphs);
                }

            }

            @Override
            public void BeforeEditNode(GraphElement n) {
                graphCopy = graphs.GetGraph().CopyElement().Graph();
                run = false;
            }

            @Override
            public void AfterEditNode(GraphElement element, String method) {
                if(!GrapsParams.API)
                {
                    run = true;
                    return;
                }

                if(method == "select")
                {
                    if(SelectedNowNode() < 0 && SelectedNowLink() < 0)
                        run = true;
                    else
                        run = false;
                    return;
                }

                ApiHelper helper = new ApiHelper(GetActivity())
                {
                    @Override
                    public void send(String req, String payload) {
                        super.send(req, payload);
                    }
                };
                String url = "";

                String keys = "";
                Boolean inserting = method == "insert" || method.equals("insert");
                if(!inserting) {
                    if (element.IsLink()) {
                        Link link = element.Link();
                        Link n = link;
                        url = GrapsParams.GetUrl(GetActivity()) + "link/" + method;
                        helper.Method = "POST";
                        if (method == "update") {
                            keys = "token=" + GrapsParams.Session + "&id=" + n.IDinAPI + "&value=" + n.ValueWithCheck();
                        } else if (method == "delete") {
                            keys = "token=" + GrapsParams.Session + "&id=" + n.IDinAPI;
                        }
                    } else {
                        Node n = element.Node();
                        url = GrapsParams.GetUrl(GetActivity()) + "node/" + method;
                        helper.Method = "POST";
                        if (method == "update") {
                            keys = "token=" + GrapsParams.Session + "&id=" + n.IDinAPI + "&x=" + n.X + "&y=" + n.Y + "&name=" + n.GetName();
                        } else if (method == "delete") {
                            keys = "token=" + GrapsParams.Session + "&id=" + n.IDinAPI;
                        }
                    }
                }
                if(inserting)
                {
                    if(element.IsNode()) {
                        CreateNode.NodeCreate(GetActivity(), this.GetGraph(), element.Node());
                    }
                    else
                    {
                        CreateLink.LinkCreate(GetActivity(), GetGraph(), element.Link());
                    }
                }
                else {
                    helper.SendStop(url, keys);
                    if (!helper.Ready) {
                        helper.Method = "DELETE";
                        helper.SendStop(url, keys);
                        if (!helper.Ready) {
                            GrapsParams.NowGraph = graphCopy.CopyElement().Graph();
                            graphs.SetGraph(GrapsParams.NowGraph);
                            graphs.invalidate();
                        }
                    }
                }
                if(SelectedNowNode() < 0 && SelectedNowLink() < 0)
                run = true;
                else
                    run = false;
            }
        };
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        panelGraphs = findViewById(R.id.GraphPanel);
        panelGraphs.addView(graphs);
        graphs.setLayoutParams(params);
        if(GrapsParams.Run_Graph)
        {
            graphs.SetGraph(GrapsParams.NowGraph);
            GrapsParams.Run_Graph = false;
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                upload_graph();
            }
        };
        Thread thread = new Thread(runnable);
        if(GrapsParams.API)
            thread.start();
    }

    void upload_graph()
    {
        while (run1)
        {
            if(run)
            {
                try {
                    Graph graph = graphs.GetGraph();
                    graphs.GetGraph().ClearNodes();
                    graphCopy = graph.CopyElement().Graph();

                    GraphElement_List list = GraphListHelper.GetGraphs(GetActivity());
                    int api = graph.IDinAPI;
                    Graph graph1 = list.ElementFromAPI(api).Graph();
                    GrapsParams.CreateGraph(GetActivity(), graph1);
                    graph = graph1.CopyElement().GetGraph();
                    Graph finalGraph = graph;
                    GetActivity().runOnUiThread(() ->
                    {
                        try {
                            graphs.SetGraph(finalGraph);
                        } catch (Exception ex) {

                        }
                    });
                }
                catch (Exception ex)
                {
                    GetActivity().runOnUiThread(() ->
                    {
                        try {
                            graphs.SetGraph(graphCopy);
                            graphs.invalidate();
                        } catch (Exception ex1) {

                        }
                    });
                }

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else
            {



            }
        }
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
        GrapsParams.NowGraph = graphs.GetGraph();


        run1 = false;
        finish();
    }

    public void EditNode(View v)
    {
        if(!graphs.Selection())
            return;
        Intent i =new Intent(this, GraphElementEdit.class);
        GrapsParams.GraphElement = graphs.GetSelected();
        if(GrapsParams.API) {
            GrapsParams.GraphElement = GrapsParams.GraphElement.CopyElement();
        }
        startActivityForResult(i, 100);
    }


    public void AddNode(View v)
    {
        graphs.AddNode();
    }
    public void DeleteNode(View v)
    {
        graphs.Delete();
    }

    public void SetLink(View v) {graphs.SetLink(false);}

    public void SetOrientationLink(View v) {graphs.SetLink(true);}

    public Activity GetActivity()
    {
        return this;
    }

    @Override
    public void finish() {
        run = false;
        run1 = false;
        run2 = false;
        super.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        GrapsParams.Run_Graph = false;
        GrapsParams.ElementName = GraphElementName.Graph;
        if (GrapsParams.API && !GrapsParams.HaveSession(GetActivity())) {
            Intent intent1 = getIntent();
            setResult(500, intent1);
            run1 = false;
            finish();
            return;
        }
        //run = true;
        if(requestCode==554 || resultCode == 554)
        {
            graphs.invalidate();
            run = run2;
        }
        else if (requestCode==555 || resultCode == 555 || requestCode==550 || resultCode == 550) // Проверяем код результата (2-ая Activity была запущена с кодом 555)
        {
            if (data != null) // Вернула ли значение вторая Activity нам Intent с данными, или, просто, закрылась
            {
                GraphElement element = GrapsParams.GraphElement;
                if(element.IsNode() || element.IsLink())
                graphs.SetGraphElement(element);
                else if(element.IsGraph()) {

                    graphs.SetGraph(element.ToGraph());
                    if(!GrapsParams.API) {
                        int api = element.Get_API_ID();
                        if (api > -1) {
                            GrapsParams.DB.upload_graph(element.Graph());
                        } else {
                            AlertDialog.Builder bld = new AlertDialog.Builder(this);

                            bld.setPositiveButton("Нет",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel(); // Закрываем диалоговое окно
                                        }
                                    });
                            bld.setNegativeButton("Да", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    GrapsParams.DB.upload_graph(element.Graph());
                                }
                            });
                            AlertDialog dlg = bld.create();
                            dlg.setTitle("Сохранять граф?");
                            dlg.setMessage("Сохранять граф?");
                            dlg.show();
                        }
                    }
                    else
                    {
                        String url = GrapsParams.GetUrl(this)+"graph/update";
                        ApiHelper helper = new ApiHelper(this)
                        {
                            @Override
                            public void send(String req, String payload) {
                                Method = "POST";
                                super.send(req, payload);
                            }
                        };
                        helper.send(url,"token="+GrapsParams.Session+"&graph="+element.IDinAPI+"&name="+element.GetName());
                        try {
                            helper.th.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(!helper.Ready)
                        {
                            helper.send(url,"token="+GrapsParams.Session+"&id="+element.IDinAPI+"&name="+element.GetName());
                            try {
                                helper.th.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
        }
        else if(requestCode==556|| resultCode == 556)
        {
            if(data != null)
            {
                int id = GrapsParams.elementID();
                if(GrapsParams.GraphElement.IsNode())
                {
                    if(!GrapsParams.API)
                    graphs.DeleteNode(id);
                    else
                        graphs.DeleteNode();
                }
                else if(GrapsParams.GraphElement.IsLink())
                {
                    if(!GrapsParams.API)
                    graphs.DeleteLink(id);
                    else
                        graphs.DeleteLink();
                }
                else
                {
                    Intent intent = getIntent();
                    setResult(556, intent);
                    run1 = false;
                    finish();
                }
            }
        }

        graphs.invalidate();
        super.onActivityResult(requestCode,resultCode,data);
    }

    public void ChangeOrientationLink(View v)
    {
        graphs.ChangeOrientationLink();
    }
    
    public Context getActivity()
    {
        return this;
    }

    public void List_Click(View v)
    {

        run2 = run;
        run = false;
        GrapsParams.GraphAPI = graphs.GetGraph();
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        GrapsParams.GraphElement = graphs.GetGraph();

        bld.setPositiveButton("Узлы графа", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                GrapsParams.graphList = new GraphElement_List(graphs.GetGraph(), GraphElementName.Node);
                GrapsParams.ElementName = GraphElementName.Node;
                StartList(v);
            }
        });

        bld.setNegativeButton("Рёбра (связи) графа", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                GrapsParams.graphList = new GraphElement_List(graphs.GetGraph(), GraphElementName.Link);
                GrapsParams.ElementName = GraphElementName.Link;
                StartList(v);
            }
        });
        bld.setCancelable(true);

        bld.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                run = run2;
            }
        });

        AlertDialog dlg = bld.create();
        dlg.setTitle("Список элементов графа");
        dlg.setMessage("Список элементов графа");

        dlg.show();
    }


    public void StartList(View v)
    {

        GrapsParams.Run_Graph = true;
        Intent i =new Intent(this, GraphElementsListActivity.class);
        GrapsParams.GraphElement = graphs.GetSelected();
        startActivityForResult(i, 100);
    }

    public void GraphProperty(View v)
    {

        GrapsParams.Run_Graph = true;
        Intent i =new Intent(this, GraphElementEdit.class);
        GrapsParams.GraphElement = graphs.GetGraph();
        startActivityForResult(i, 100);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        run = false;
        super.startActivityForResult(intent, requestCode);
    }
}