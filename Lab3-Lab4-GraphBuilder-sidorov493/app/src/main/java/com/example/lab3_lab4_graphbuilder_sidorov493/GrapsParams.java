package com.example.lab3_lab4_graphbuilder_sidorov493;

import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import org.jetbrains.annotations.Nullable;

public class GrapsParams {
    public static int GraphID = 0;
    public  static Graph Graph;

    public static String Session = "";
    public static Boolean API = false;

    public static GraphElement GraphElement;
    public static int elementID()
    {
        return GraphElement.id();
    }

    public static GraphElement_List graphList;

    public static GraphElement GraphCopy;

    public static DB_Graphs DB;

    public static GraphElement_List graphs;

    public static boolean Run_Graph = false;

    public static Graph NowGraph;

    public static String DomainUrl="labs-api.spbcoit.ru";
    public static String PortUrl = "lab/graph/api";

    public static Boolean Registration = false;

    public static Boolean Opened;

    public static Graph GraphAPI;

    public static void OpenSession(String session)
    {
        Session = session;
        Opened = true;
        Registration = true;
    }

    public static Boolean HaveSession()
    {
        try
        {
            if(!Opened)
            {

            }
        }
        catch (Exception ex)
        {
            Opened = false;
        }
        if(Session == "" || Session.equals("") || Session == null || !Opened)
        {
            Session = "";
            Opened = false;
            return  false;
        }
        return  true;
    }

    public static Boolean HaveSession(Activity ctx)
    {
        if(!HaveSession())
            return false;
        ListSessionsHelper helper = new ListSessionsHelper(ctx, Session, false);
        helper.SendStop();
        Opened = helper.Ready;

        return HaveSession();
    }

    public static String GetSession()
    {
        HaveSession();
        return Session;
    }

    public static String GetSession(Activity ctx)
    {
        HaveSession(ctx);
        return Session;
    }

    public static void CloseSession(String session, Activity ctx)
    {
        CloseSession(session, ctx, false);
    }

    public static void CloseSession(Activity ctx)
    {
        CloseSession(ctx, false);
    }

    public static void CloseSession(String session, Activity ctx, Boolean message)
    {
        Boolean message1 = message;
        CloseSession close = new CloseSession(ctx, session)
        {
            @Override
            public void MessageOutput(String message) {
                if(message1)
                {

                        try {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                            AlertDialog dialog = builder.create();
                            dialog.setMessage(message);
                            dialog.show();
                        }
                        catch(Exception ex)
                        {

                        }

                }
            }
        };
        close.Send();
    }

    public static void CloseSession(Activity ctx, Boolean message)
    {
        CloseSession(Session, ctx, message);
        Session = "";
        Opened = false;
    }

    public  static String GetUrl(Context context)
    {
        UrlStorege.GetDB(context).GetUrl();
        return Url(context);
    }

    public static String Url(@Nullable Context context) {
        String url = "";

        int end = 0;
        String end1 = "";
        if(PortUrl != "" && !PortUrl.equals("") && PortUrl != null) {
            try {
                if(DomainUrl.contains("/"))
                    throw  new Exception();
                String[] parts = PortUrl.split("/");
                Integer.parseInt(parts[0]);
                url = "http://" + DomainUrl + ":" + PortUrl;
            } catch (Exception ex) {
                end = DomainUrl.length() - 1;
                end1 = DomainUrl.substring(end, end + 1);
                String start = PortUrl.substring(0, 1);
                if (end1 != "/" && !end1.equals("/"))
                    if (start != "/" && !start.equals("/"))
                        url = "http://" + DomainUrl + "/" + PortUrl;
                    else
                        url = "http://" + DomainUrl + PortUrl;
                else
                    url = "http://" + DomainUrl + PortUrl;
            }
        }
        else
            url = "http://"+DomainUrl;
        end = url.length()-1;
        end1 = url.substring(end, end+1);
        if(end1 != "/" && !end1.equals("/"))
            url+="/";
        UrlStorege.GetDB(context).UpdateUrl();
        return  url;
    }

    public static void CreateGraph(Activity ctx, Graph graph)
    {
        ListNodesHelper.GetNodes(ctx, graph);
        ListLinksHelper.GetLinks(ctx, graph);
    }

    public static  GraphElementName ElementName;

    public static Boolean ChangePassword = false;
    public static Boolean SessionsList = false;
}
