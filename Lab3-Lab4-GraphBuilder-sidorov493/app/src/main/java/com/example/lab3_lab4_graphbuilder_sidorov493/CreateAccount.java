package com.example.lab3_lab4_graphbuilder_sidorov493;

import android.app.Activity;

public class CreateAccount extends ApiHelper {
    public String login, password;

    public CreateAccount(Activity ctx, String login, String password) {
        super(ctx);
        this.login = login;
        this.password = password;
        Method = "PUT";
    }


    @Override
    public String GetMessageFatal() {
        return "Не удалось зарегистрироваться";
    }

    @Override
    public String GetMessageReady() {
        return "Вы успешно зарегистрировались";
    }

    public void Send() {

        send(GrapsParams.Url(ctx) + "account/create", "name=" + login + "&secret=" + password);


    }
}
