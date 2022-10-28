package com.example.lab3_lab4_graphbuilder_sidorov493;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

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

public class GraphEdit2 extends AppCompatActivity {

    Button exit;
    GraphView graphs;
    LinearLayout panelGraphs;
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

        //graphs = findViewById(R.id.GraphsPanel);
        graphs = new GraphView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        panelGraphs = findViewById(R.id.GraphPanel);
        panelGraphs.addView(graphs);
        graphs.setLayoutParams(params);
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
        finish();
    }

    public void EditNode(View v)
    {
        if(!graphs.Selection())
            return;
        Intent i =new Intent(this, GraphElementEdit.class);
        GrapsParams.GraphElement = graphs.GetSelected();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==554 || resultCode == 554)
        {
            graphs.invalidate();
        }
        else if (requestCode==555 || resultCode == 555) // Проверяем код результата (2-ая Activity была запущена с кодом 555)
        {
            if (data != null) // Вернула ли значение вторая Activity нам Intent с данными, или, просто, закрылась
            {
                GraphElement element = GrapsParams.GraphElement;
                if(element.IsNode() || element.IsLink())
                graphs.SetGraphElement(element);
                else if(element.IsGraph())
                    graphs.SetGraph(element.ToGraph());

            }
        }
        else if(requestCode==556|| resultCode == 556)
        {
            if(data != null)
            {
                int id = GrapsParams.elementID();
                if(GrapsParams.GraphElement.IsNode())
                {
                    graphs.DeleteNode(id);
                }
                else
                {
                    graphs.DeleteLink(id);
                }
            }
        }

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
        AlertDialog.Builder bld = new AlertDialog.Builder(this);

        bld.setPositiveButton("Узлы графа", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                GrapsParams.graphList = new GraphElement_List(graphs.GetGraph(), GraphElementName.Node);
                StartList(v);
            }
        });

        bld.setNegativeButton("Рёбра (связи) графа", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                GrapsParams.graphList = new GraphElement_List(graphs.GetGraph(), GraphElementName.Link);
                StartList(v);
            }
        });
        bld.setCancelable(true);


        AlertDialog dlg = bld.create();
        dlg.setTitle("Список элементов графа");
        dlg.setMessage("Список элементов графа");

        dlg.show();
    }


    public void StartList(View v)
    {
        Intent i =new Intent(this, GraphElementsListActivity.class);
        GrapsParams.GraphElement = graphs.GetSelected();
        startActivityForResult(i, 100);
    }

    public void GraphProperty(View v)
    {
        Intent i =new Intent(this, GraphElementEdit.class);
        GrapsParams.GraphElement = graphs.GetGraph();
        startActivityForResult(i, 100);
    }
}