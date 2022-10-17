package com.example.lab3_lab4_graphbuilder_sidorov493;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exit = findViewById(R.id.ExitButton);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Exit_Click(view);
            }
        });
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
        AlertDialog.Builder bld = new AlertDialog.Builder(this);

        bld.setPositiveButton("Нет",
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel(); // Закрываем диалоговое окно
                    }
                });
        bld.setNegativeButton("Да", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish(); // Закрываем Activity
            }
        });
        AlertDialog dlg = bld.create();
        dlg.setTitle("Выход из приложения");
        dlg.setMessage("Уважаемый пользователь \n" +
                "Вы действительно хотите выйти из программы \n" +
                "Вы, также, можете запустить программу снова \n" +
                "С уважением и любовью, Создатель программы, Сидоров Антон Дмитриевич");
        dlg.show();
    }

}