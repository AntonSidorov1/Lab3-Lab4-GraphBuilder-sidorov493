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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.time.LocalDateTime;

public class GraphElementEdit extends AppCompatActivity {

    Button exit, buttonNameID;
    TextView id, nameLabel, dateTime;
    EditText nameEdit;

    GraphElement graphElement;
    LinearLayout nameLayout, nameEditLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_element_edit);
        exit = findViewById(R.id.cancelButton);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Exit_Click(view);
            }
        });
        graphElement = GrapsParams.GraphElement;
        id = findViewById(R.id.TextElementID);
        id.setText(String.valueOf(graphElement.ID()));
        nameLayout = findViewById(R.id.NameLayout);
        nameLabel = findViewById(R.id.NameLabel);
        nameLabel.setText(graphElement.GetName());

        nameEditLayout = new LinearLayout(this);
        nameEditLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
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
        if(graphElement.IsNode())
        graphElement.Node().SetName(name);

        GrapsParams.GraphElement = graphElement;

        Intent intent = getIntent();
        setResult(555, intent);
        finish();
    }

    public void Delete(View v)
    {


        String name = nameEdit.getText().toString();
        nameLabel.setText(name);
        if(graphElement.IsNode())
            graphElement.Node().SetName(name);

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