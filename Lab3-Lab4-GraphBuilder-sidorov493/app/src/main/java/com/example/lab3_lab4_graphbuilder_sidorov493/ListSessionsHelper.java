package com.example.lab3_lab4_graphbuilder_sidorov493;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListSessionsHelper extends ApiHelper {

    public Boolean GetList = true;
    public ListSessionsHelper(Activity ctx) {
        super(ctx);
        Sessions = new ArrayList<>();
        Method="GET";
    }

    public ArrayList<Session> Sessions;


    public ListSessionsHelper(Activity ctx, Boolean getList) {
        this(ctx);
        GetList = getList;
    }


    public ListSessionsHelper(Activity ctx, String session) {
        this(ctx);
        this.session = session;
    }

    public ListSessionsHelper(Activity ctx, String session, Boolean getList) {
        this(ctx, session);
        GetList = getList;
    }

    public Boolean Did = false;
    @Override
    public void on_ready(String res) {
        if(GetList)
        {
            if(Did)
                return;
            try {
                Did = true;
                JSONArray arrayGraphs = new JSONArray(res);
                for (int i = 0; i < arrayGraphs.length(); i++)
                {
                    JSONObject jsonObject = arrayGraphs.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String session = jsonObject.getString("token");
                    Session Session = new Session(session);
                    Session.ID = id;

                    Session.SetTimeStamp(jsonObject.getLong("timestamp"));
                    Sessions.add(Session);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            Did = true;
        }
        super.on_ready(res);
    }

    @Override
    public void Send()
    {
        send(GrapsParams.Url(ctx)+"session/list", "token="+session);
    }

    public static ArrayList<Session> GetSessions(Activity ctx)
    {

        ListSessionsHelper helper = new ListSessionsHelper(ctx,GrapsParams.Session);
        helper.SendStop();
        if(helper.Ready)
            helper.on_ready();
        return helper.Sessions;
    }


}
