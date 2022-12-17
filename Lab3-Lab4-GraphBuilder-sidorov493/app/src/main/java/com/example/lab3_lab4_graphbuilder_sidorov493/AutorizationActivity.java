package com.example.lab3_lab4_graphbuilder_sidorov493;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

public class AutorizationActivity extends AppCompatActivity {

    Button exit;
    CheckBox save, show;
    EditText password, logIn;
    Button LogIn, Registration;
    LinearLayout LoginLayout, LoginInputLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autorization);
        exit = findViewById(R.id.closeAutorization);
        LogIn = findViewById(R.id.LogIn);
        Registration = findViewById(R.id.Registration);
        LoginLayout = findViewById(R.id.LoginLayout);
        LoginInputLayout = findViewById(R.id.LoginImputLayout);

        if(GrapsParams.ChangePassword)
        {
            LogIn.setText("Установить пароль");
            LoginInputLayout.setVisibility(View.GONE);
            Registration.setVisibility(View.GONE);
            if(!GrapsParams.HaveSession(this))
            {

                    Intent intent = getIntent();
                    setResult(500, intent);
                    finish();
                    return;
            }
        }

        show = findViewById(R.id.ShowPasswordRegistrate);
        password = findViewById(R.id.PasswordRegistrate);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        logIn = findViewById(R.id.LogInRegistrate);

        show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(!isChecked)
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                else
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        });

    }

    Context GetContext()
    {
        return this;
    }

    public void SessionOpen(ApiHelper helper)
    {
        if(!GrapsParams.API)
        {
            CreateGraph createGraph = new CreateGraph(helper, GrapsParams.NowGraph)
            {
                @Override
                public void Run()
                {
                    GraphOutput(this);
                }
            };
            //GraphOutput(helper);
            createGraph.Send();
        }
    }

    public void GraphOutput(ApiHelper helper)
    {
        CloseSession session = new CloseSession(helper)
        {
            @Override
            public void MessageReadyOutput(String message) {
                runOnUiThread(() -> {
                    finish();
                });

            }
        };
        session.Send();
        //finish();
    }

    public void Registrate_Click(View v)
    {
        String login = logIn.getText().toString();
        String password = this.password.getText().toString();
        if(!CheckNullLogin(login))
            return;
        CreateAccount session = new CreateAccount(this, login, password){

            @Override
            public void MessageOutput(String message) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(GetContext());
                AlertDialog dlg = dialog.create();
                dlg.setMessage(message);
                dlg.show();
            }
        };
        session.Send();
    }

    Boolean CheckNullLogin(String login)
    {
        Boolean check = true;

        if(login == "" || login.equals("") || login.isEmpty())
        {
            check = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(GetContext());
            builder.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.setTitle("Ошибка");
            AlertDialog dialog = builder.create();
            dialog.setMessage("Введите, хотя бы, логин \n" +
                    "Желательно, также и пароль");
            dialog.setCancelable(false);
            dialog.show();
        }


        return check;
    }

    @Override
    public void finish() {
        GrapsParams.ChangePassword = false;
        super.finish();
    }

    public void save_onClick(View v)
    {
        String password = this.password.getText().toString();
        if(GrapsParams.ChangePassword)
        {
            if(!GrapsParams.HaveSession(this)) {
                Intent intent = getIntent();
                setResult(500, intent);
                finish();
                return;
            }
            ApiHelper helper = new ApiHelper(this);
            helper.Method = "POST";
            helper.SendStop(GrapsParams.GetUrl(this)+"account/update", "token="+GrapsParams.Session+"&secret="+password);
            if(!helper.Ready)
            {
                Intent intent = getIntent();
                setResult(500, intent);
                finish();
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.setMessage("Пароль успешно изменён");
            dialog.setCancelable(false);
            dialog.show();
            return;
        }
        String login = logIn.getText().toString();
        if(!CheckNullLogin(login))
            return;

            GetSession session = new GetSession(this, login, password){
                @Override
                public void MessageReadyOutput(String message) {
                    if(!GrapsParams.Registration) {
                        SessionOpen(this);
                    }
                    else
                    {
                        GrapsParams.OpenSession(session);
                        finish();
                    }
                }

                @Override
                public void MessageOutput(String message) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(GetContext());
                    AlertDialog dlg = dialog.create();
                    dlg.setMessage(message);
                    dlg.show();
                }
            };
            session.Send();

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
}