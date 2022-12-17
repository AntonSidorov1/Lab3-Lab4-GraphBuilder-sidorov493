package com.example.lab3_lab4_graphbuilder_sidorov493;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ApiMainActivity extends AppCompatActivity {

    Button exit;
    TextView session;
    EditText sessionEdit;

    public String GetSession()
    {
        return "Сессия: "+GrapsParams.GetSession(this);
    }

    public void GraphListOpen_Click(View v)
    {

        GrapsParams.SessionsList = false;
        try {

            GraphListHelper helper = new GraphListHelper(this, GrapsParams.Session);
            helper.Send();

            try {
                helper.th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            helper.on_ready();
            GrapsParams.graphList = new GraphElement_List(helper.graphs);
        }
        catch(Exception ex)
        {

        }
        Intent i =new Intent(this, GraphElementsListActivity.class);
        startActivityForResult(i, 100);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GrapsParams.ChangePassword = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_main);
        exit = findViewById(R.id.ApiClose);
        GrapsParams.API = true;
        GrapsParams.Registration = true;
        session = findViewById(R.id.sessionText);

        DB_Sessions.GetSession(this);
        session.setText(GetSession());
        if(!GrapsParams.HaveSession(this))
            NoSession();

        if(GrapsParams.HaveSession(this))
        {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        GrapsParams.ChangePassword = false;
        session.setText(GetSession());
        DB_Sessions.SetSession(this);
        if(resultCode == 500)
        {
            NoSession();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    void NoSession()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.setMessage("Войдите или зарегистрируйтесь в системе");
        dialog.setCancelable(false);
        dialog.show();
    }

    public void Autorization_Click(View v)
    {
        Intent i = new Intent(GetContent(), AutorizationActivity.class);
        startActivityForResult(i, 100);
    }

    public Context GetContent()
    {
        return  this;
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

        finish();
    }

    public void CloseSession_Click(View v)
    {
        try {
            GrapsParams.CloseSession(this, true);
            session.setText(GetSession());
        }
        catch (Exception ex)
        {

        }
    }

    public void DeleteAccount_Click(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        try {

            ApiHelper helper = new ApiHelper(this);
            helper.Method = "DELETE";
            helper.SendStop(GrapsParams.GetUrl(this)+"/account/delete", "token="+GrapsParams.Session);
            if(!helper.Ready)
                throw  new Exception();
            else
            {
                dialog.setMessage("Аккаунт успено удалён");
                dialog.show();
            }
            session.setText(GetSession());
        }
        catch (Exception ex)
        {
            try {
                session.setText(GetSession());
            }
            catch (Exception ex1)
            {

            }
            dialog.setMessage("Сессия не была открыта");
            dialog.setTitle("Ошибка");
            dialog.show();
        }
    }


    public void ChangePassword(View v)
    {
        GrapsParams.ChangePassword = true;
        Intent i = new Intent(GetContent(), AutorizationActivity.class);
        startActivityForResult(i, 100);
    }

    public void ListSessions_Click(View v)
    {
        GrapsParams.SessionsList = true;
        Intent i = new Intent(GetContent(), GraphElementsListActivity.class);
        startActivityForResult(i, 100);
    }

}