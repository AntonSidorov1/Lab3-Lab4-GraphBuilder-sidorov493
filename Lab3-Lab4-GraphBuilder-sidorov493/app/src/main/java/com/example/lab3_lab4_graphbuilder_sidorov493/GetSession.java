package com.example.lab3_lab4_graphbuilder_sidorov493;

import android.app.Activity;

import org.json.JSONException;

public class GetSession extends ApiHelper {
    public String login, password;
    public GetSession(Activity ctx, String login, String password) {
        this(ctx);
        this.login = login;
        this.password = password;

    }


    public GetSession(Activity ctx) {
        super(ctx);
        Method = "PUT";

    }

    @Override
    public void on_ready(String res) {
        String session;
        try {
            session = StringFromJsonObject(res, "token");
        } catch (JSONException e) {
            session = null;
        }
        this.session = session;
        OutputSession(session);
        super.on_ready(res);
    }

    public void OutputSession(String session)
    {

    }

    @Override
    public String GetMessageFatal() {
        return "Неверный логин или пароль";
    }

    @Override
    public String GetMessageReady() {
        return "Вы успешно вошли в систему";
    }

    public void Send()
    {
        send(GrapsParams.Url(ctx)+"session/open", "name="+login+"&secret="+password);
    }
}
