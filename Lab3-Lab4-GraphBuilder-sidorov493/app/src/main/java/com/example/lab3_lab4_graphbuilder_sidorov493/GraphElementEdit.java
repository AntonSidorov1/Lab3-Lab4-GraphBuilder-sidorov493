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

    Button exit, buttonNameID;
    TextView id, nameLabel, dateTime, elementType;
    EditText nameEdit;

    GraphElement graphElement;
    LinearLayout nameLayout, nameEditLayout, attributesPanel;
    LinearLayout xyPanel, stPanel, mainPanel;
    LayoutPoleInput xPole, yPole;

    CheckBox OrientationGraph;

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
        nameLabel.setText(graphElement.GetName());

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
        if(graphElement.IsNode()) {
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

        OrientationGraph = new CheckBox(this);
        OrientationGraph.setLayoutParams(params1);
        OrientationGraph.setText("Ориентированное ребро");

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
            xPole.InputPole().setText(String.valueOf(n.sourceID));
            yPole.SignaturePole().setText("Target: ");
            yPole.InputPole().setText(String.valueOf(n.targetID));
            mainPanel.addView(OrientationGraph);
            OrientationGraph.setChecked(n.Orientation);
        }

        UpdateElement();

        dateTime = findViewById(R.id.DateTimeText);
        dateTime.setText(graphElement.TimeStamp);
    }

    public void UpdateElement()
    {

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
            }
            catch (Exception ex)
            {
                return;
            }
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

}