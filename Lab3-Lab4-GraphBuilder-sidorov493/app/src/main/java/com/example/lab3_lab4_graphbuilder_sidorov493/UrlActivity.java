package com.example.lab3_lab4_graphbuilder_sidorov493;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UrlActivity extends AppCompatActivity {

    Button exit;
    EditText domain, port;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);
        exit = findViewById(R.id.ExitButton);
        domain = findViewById(R.id.DomainNameText);
        port = findViewById(R.id.PortText);
        domain.setText(GrapsParams.DomainUrl);
        port.setText(GrapsParams.PortUrl);
    }

    public  void  DomainClear_Click(View v)
    {
        domain.setText("");
    }

    public void DomainStart_Click(View v)
    {
        domain.setText(GrapsParams.DomainUrl);
    }

    public void PortStart_Click(View v)
    {
        port.setText(GrapsParams.PortUrl);
    }

    public  void  PortClear_Click(View v)
    {
        port.setText("");
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
        //GrapsParams.NowGraph = graphs.GetGraph();
        GrapsParams.DomainUrl = domain.getText().toString();
        GrapsParams.PortUrl = port.getText().toString();

        Intent i = getIntent();
        finish();
    }
}