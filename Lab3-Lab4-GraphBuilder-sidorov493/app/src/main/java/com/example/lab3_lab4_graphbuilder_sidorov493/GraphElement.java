package com.example.lab3_lab4_graphbuilder_sidorov493;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class GraphElement {
    protected String NameElement;
    public String GetName()
    {
        return NameElement;
    }
    public GraphElement(String name)
    {
        NameElement = name;
        SetDatetimeNow();
    }

    public void SetDatetimeNow()
    {

        SetTimeStamp(new Date());
    }

    public String TimeStamp;

    public void SetdateTime(String date)
    {
        SetTimeStamp(new Date(date));
    }

    public void SetTimeStamp(Date date)
    {
        TimeStamp = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss").format(date);
    }

    public String GetDatetime()
    {
        return TimeStamp;
    }

    public Date GetTimeStamp()
    {
        return new Date(TimeStamp);
    }


    public abstract String TypeText();

    public boolean IsNode()
    {
        return this instanceof Node;
    }

    public boolean IsLink()
    {
        return this instanceof Node;
    }

    public boolean IsGraph()
    {
        return this instanceof Graph;
    }

    public Node Node()
    {
        return (Node)this;
    }

    public Link Link() {
        return (Link) this;
    }

    public Graph Graph() {
        return (Graph) this;
    }

    private int API_ID = -1;
    public int Get_API_ID()
    {
        return API_ID;
    }
    public void Set_API_ID(int id)
    {
        API_ID = id;
    }

    private boolean have_api = false;
    public boolean GetHaveAPI()
    {
        return have_api;
    }
    public void SetHaveAPI(boolean have)
    {
        have_api = have;
    }


    @Override
    public String toString() {
        return GetName();
    }

    public abstract int ID();

    public int id()
    {
        return ID();
    }

    public void SetNameFromID()
    {
        NameElement = GetNameFromID();
    }

    public abstract String GetNameFromID();

}
