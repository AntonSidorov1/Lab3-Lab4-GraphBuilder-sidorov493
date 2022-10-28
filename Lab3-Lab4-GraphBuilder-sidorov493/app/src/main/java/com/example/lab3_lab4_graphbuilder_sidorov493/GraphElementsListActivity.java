package com.example.lab3_lab4_graphbuilder_sidorov493;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

    public Graph Graph()
    {
        return graphs.GetGraph();
    }

    ArrayAdapter<GraphElement> list;


    void update_list()
    {

        graphs.SetGraph(graph);
        list.notifyDataSetChanged();
    }

    public Context GetContext()
    {
        return  this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_elements_list);

        graphs = GrapsParams.graphList;
        graph = graphs.GetGraph();
        exit = findViewById(R.id.CloseEditorElements);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Exit_Click(view);
            }
        });
        typeText = findViewById(R.id.TypeText);
        typeText.setText(graphs.GetName());

        elementList = findViewById(R.id.listElements1);
        list = new ArrayAdapter<GraphElement>(this, android.R.layout.simple_list_item_1, graphs);
        elementList.setAdapter(list);
        elementList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(GetContext(), GraphElementEdit.class);
                GrapsParams.GraphElement = graphs.get(i);
                startActivityForResult(intent, 100);
            }
        });

        update_list();
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

        Intent intent = getIntent();
        setResult(554, intent);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==555 || resultCode == 555) // Проверяем код результата (2-ая Activity была запущена с кодом 555)
        {
            if (data != null) // Вернула ли значение вторая Activity нам Intent с данными, или, просто, закрылась
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
            }
        }
        else if(requestCode==556|| resultCode == 556)
        {
            try {

                if (data != null) {
                    int id = GrapsParams.elementID();
                    if (GrapsParams.GraphElement.IsNode()) {
                        graph.DeleteNode(id);
                    } else {
                        graph.DeleteLink(id);
                    }
                }
            }
            catch(Exception ex)
            {

            }
        }

        update_list();

        super.onActivityResult(requestCode,resultCode,data);
    }

    public void AddElements(View v)
    {
        Intent i =new Intent(this, GraphElementEdit.class);
        GrapsParams.GraphElement = graphs.add();
        startActivityForResult(i, 100);
    }

    public void PropertyGraph(View v)
    {

    }


}