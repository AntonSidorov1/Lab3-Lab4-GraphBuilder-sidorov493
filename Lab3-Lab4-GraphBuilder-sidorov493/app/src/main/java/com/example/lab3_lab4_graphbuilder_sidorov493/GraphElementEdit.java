package com.example.lab3_lab4_graphbuilder_sidorov493;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.time.LocalDateTime;

public class GraphElementEdit extends AppCompatActivity {

    Button exit, buttonNameID, ChangeOrientation;
    TextView id, nameLabel, dateTime, elementType;
    EditText nameEdit;

    GraphElement graphElement;
    LinearLayout nameLayout, nameEditLayout, attributesPanel;
    LinearLayout xyPanel, stPanel, mainPanel, OrientationPanel;
    LayoutPoleInput xPole, yPole;

    CheckBox OrientationGraph;
    TextVisibleView LinkText, LinkValue;
    Button copy, past, toGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_element_edit);
        mainPanel = findViewById(R.id.MainPanel);
        exit = findViewById(R.id.cancelButton);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Exit_Click(view);
            }
        });
        graphElement = GrapsParams.GraphElement;
        elementType = findViewById(R.id.ElementType);
        elementType.setText(graphElement.TypeText());
        id = findViewById(R.id.TextElementID);
        id.setText(String.valueOf(graphElement.ID()));
        nameLayout = findViewById(R.id.NameLayout);
        nameLabel = findViewById(R.id.NameLabel);
        try{
        nameLabel.setText(graphElement.GetName());
        }
        catch (Exception ex) {

        }

        nameEditLayout = new LinearLayout(this);
        nameEditLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        //nameLayout.addView(nameEditLayout);
        nameEditLayout.setLayoutParams(params);
        //params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        nameEdit = new androidx.appcompat.widget.AppCompatEditText(this);
        nameEdit.setLayoutParams(params);
        nameEdit.setText(nameLabel.getText().toString());
        nameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String name = nameEdit.getText().toString();
                nameLabel.setText(name);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        nameEditLayout.addView(nameEdit);

        buttonNameID = new Button(this);
        buttonNameID.setLayoutParams(params);
        buttonNameID.setText("Имя из ID");
        buttonNameID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameEdit.setText(graphElement.GetNameFromID());
            }
        });

        nameEditLayout.addView(buttonNameID);
        if(graphElement.IsNode() || graphElement.IsGraph()) {
            nameLayout.addView(nameEditLayout);
        }

        attributesPanel = findViewById(R.id.AttributesPanel);
        xyPanel = new LinearLayout(this);
        stPanel = new LinearLayout(this);
        xyPanel.setLayoutParams(params);
        stPanel.setLayoutParams(params);

        xPole = new LayoutPoleInput(this);
        xPole.setLayoutParams(params1);
        xyPanel.addView(xPole);

        yPole = new LayoutPoleInput(this);
        yPole.setLayoutParams(params1);
        xyPanel.addView(yPole);

        OrientationPanel = new LinearLayout(this);
        OrientationPanel.setLayoutParams(params);

        OrientationGraph = new CheckBox(this);
        OrientationGraph.setLayoutParams(params1);
        OrientationGraph.setText("Ориентированное ребро");
        OrientationPanel.addView(OrientationGraph);

        ChangeOrientation = new Button(this);
        ChangeOrientation.setLayoutParams(params1);
        ChangeOrientation.setText("Сменить направление");
        ChangeOrientation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String source = xPole.InputPole().getText().toString();
                String target = yPole.InputPole().getText().toString();
                String change = source;
                source = target;
                target = change;
                xPole.InputPole().setText(source);
                yPole.InputPole().setText(target);
            }
        });
        OrientationPanel.addView(ChangeOrientation);

        LinkText = new TextVisibleView(this);
        LinkValue = new TextVisibleView(this);

        if(graphElement.IsNode())
        {
            attributesPanel.addView(xyPanel);
            Node n = graphElement.Node();
            xPole.SignaturePole().setText("X: ");
            xPole.InputPole().setText(String.valueOf(n.X));
            yPole.SignaturePole().setText("Y: ");
            yPole.InputPole().setText(String.valueOf(n.Y));
        }
        else if(graphElement.IsLink()){
            attributesPanel.addView(xyPanel);
            Link n = graphElement.Link();
            xPole.SignaturePole().setText("Source: ");
            try {

        nameEdit.setText(nameLabel.getText().toString());
                xPole.InputPole().setText(String.valueOf(n.sourceID));
            }
            catch (Exception ex) {

            }
            yPole.SignaturePole().setText("Target: ");
            try{
            yPole.InputPole().setText(String.valueOf(n.targetID));
            }
            catch (Exception ex) {

            }
            mainPanel.addView(OrientationPanel);
            OrientationGraph.setChecked(n.Orientation);
            try{
            LinkText.SetText(n.GetText());
            LinkText.SetTextVisible(n.TextVisible);
            }
            catch (Exception ex) {

            }
            mainPanel.addView(LinkText);
            try{
            LinkValue.SetText(n.GetTextValue());
            LinkValue.SetTextVisible(n.ValueVisible);
        }
            catch (Exception ex) {

        }
            mainPanel.addView(LinkValue);
        }
        else if(graphElement.IsGraph())
        {

        }

        //UpdateElement();

        dateTime = findViewById(R.id.DateTimeText);
        dateTime.setText(graphElement.TimeStamp);

        copy = new Button(this);
        past = new Button(this);
        copy.setText("Копировать");
        past.setText("Вставить");
        copy.setLayoutParams(params);
        past.setLayoutParams(params);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CopyElement(view);
            }
        });
        past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               PastElement(view);
            }
        });
        mainPanel.addView(copy);
        mainPanel.addView(past);

        toGraph = new Button(this);
        toGraph.setText("К графу");
        toGraph.setLayoutParams(params);
        mainPanel.addView(toGraph);
        toGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToGraph(view);
            }
        });

    }

    public void ToGraph (View v)
    {
        if(GrapsParams.Run_Graph)
            finish();
        else {
            Graph graph = new Graph();
            if (GrapsParams.GraphElement.IsGraph()) {
                graph = GrapsParams.GraphElement.Graph();
            }
            else
            {
                graph = GrapsParams.GraphElement.GetGraph();
            }
            GrapsParams.NowGraph = graph;
            Intent i = new Intent(this, GraphEdit2.class);
            GrapsParams.Run_Graph = true;
            startActivity(i);
        }
    }

    public void UpdateElement()
    {
        try{
                nameLabel.setText(graphElement.GetName());
            }
            catch (Exception ex) {

            }
        try {
            if (graphElement.IsNode()) {
                nameEdit.setText(nameLabel.getText().toString());
                //attributesPanel.addView(xyPanel);
                Node n = graphElement.Node();
                xPole.SignaturePole().setText("X: ");
                xPole.InputPole().setText(String.valueOf(n.X));
                yPole.SignaturePole().setText("Y: ");
                yPole.InputPole().setText(String.valueOf(n.Y));
            } else if (graphElement.IsLink()) {
                //attributesPanel.addView(xyPanel);
                Link n = graphElement.Link();
                xPole.SignaturePole().setText("Source: ");
                try {

                    xPole.InputPole().setText(String.valueOf(n.sourceID));
                } catch (Exception ex) {

                }
                yPole.SignaturePole().setText("Target: ");
                try {
                    yPole.InputPole().setText(String.valueOf(n.targetID));
                } catch (Exception ex) {

                }
                //mainPanel.addView(OrientationPanel);
                OrientationGraph.setChecked(n.Orientation);
                try {
                    LinkText.SetText(n.GetText());
                    LinkText.SetTextVisible(n.TextVisible);
                } catch (Exception ex) {

                }
                //mainPanel.addView(LinkText);
                try {
                    LinkValue.SetText(n.GetTextValue());
                    LinkValue.SetTextVisible(n.ValueVisible);
                } catch (Exception ex) {

                }
                //mainPanel.addView(LinkValue);
            } else if (graphElement.IsGraph()) {

                nameEdit.setText(nameLabel.getText().toString());
            }
        }
        catch(Exception ex)
        {

        }
    }


    public void Save(View v)
    {


        String name = nameEdit.getText().toString();
        nameLabel.setText(name);
        if(graphElement.IsNode()) {
            Node node = graphElement.Node();
            node.SetName(name);
            try {
                node.X = Float.valueOf(xPole.InputPole().getText().toString());
                node.Y = Float.valueOf(yPole.InputPole().getText().toString());
            }
            catch (Exception ex)
            {
                return;
            }
        }
        else if (graphElement.IsLink())
        {
            Link node = graphElement.Link();
            try {
                int source = Integer.valueOf(xPole.InputPole().getText().toString());
                int target = Integer.valueOf(yPole.InputPole().getText().toString());
                node.SetNodes(source, target);
                node.Orientation = OrientationGraph.isChecked();
                node.TextVisible = LinkText.IsTextVisible();
                node.SetText(LinkText.GetText());
                node.ValueVisible = LinkValue.IsTextVisible();
                node.SetValue(LinkValue.GetText());
        nameEdit.setText(nameLabel.getText().toString());
            }
            catch (Exception ex)
            {
                return;
            }
        }
        else if(graphElement.IsGraph())
        {

                Graph node = graphElement.Graph();
                node.SetName(name);

            Graph graph = node;
            //if(node.Get_API_ID() > -1)
            //GrapsParams.DB.upload_graph(graph);
        }

        GrapsParams.GraphElement = graphElement;

        Intent intent = getIntent();
        setResult(555, intent);
        finish();
    }

    public void Delete(View v)
    {


        String name = nameEdit.getText().toString();
        nameLabel.setText(name);
        if(graphElement.IsNode()) {
            Node node = graphElement.Node();
            node.SetName(name);
        }
        GrapsParams.GraphElement = graphElement;

        Intent intent = getIntent();
        setResult(556, intent);
        finish();
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

    public void PastElement(View v)
    {
        try {

            int id = graphElement.Get_API_ID();
            if (graphElement.EqualsTypes(GrapsParams.GraphCopy)) {
                graphElement = GrapsParams.GraphCopy.CopyElement();
                graphElement.Set_API_ID(id);
                UpdateElement();
            }
        }
        catch (Exception ex)
        {

        }
    }

    public void CopyElement(View v)
    {
        GrapsParams.GraphCopy = graphElement.CopyElement();
    }

}