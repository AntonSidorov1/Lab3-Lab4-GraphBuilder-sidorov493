package com.example.lab3_lab4_graphbuilder_sidorov493;

import android.app.Activity;

import org.json.JSONException;

public class CloseSession  extends ApiHelper{
    public CloseSession(Activity ctx) {
        super(ctx);
        Method = "DELETE";
    }


    public CloseSession(Activity ctx, String session)
    {
        this(ctx);
        this.session = session;
    }

    public CloseSession(ApiHelper helper)
    {
        this(helper.ctx, helper.session);
    }


    public void Send()
    {
        send(GrapsParams.Url(ctx)+"session/close", "token="+session);
    }

    @Override
    public String GetMessageReady() {
        return "Вы успешно закрыли сессию";
    }

    @Override
    public String GetMessageFatal() {
        return "Сессия не была открыта";
    }
}
