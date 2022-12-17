package com.example.lab3_lab4_graphbuilder_sidorov493;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Session {
    public String Key = "";
    public String TimeStamp = "";
    public int ID = -1;


    public void SetDatetimeNow()
    {

        SetTimeStamp(new Date());
    }

    public void SetTimeStamp(long date)
    {
        SetTimeStamp(new Date(date));
    }
    public void SetDateTime(String date)
    {
        SetTimeStamp(new Date(date));
    }
    public void SetTimeStamp(String date)
    {
        SetDateTime(date);
    }

    public String GetDatetime()
    {
        return TimeStamp;
    }

    public Date GetTimeStamp()
    {
        return new Date(TimeStamp);
    }

    public void SetTimeStamp(Date date)
    {
        TimeStamp = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss").format(date);
    }

    public Session(String key, Date date)
    {
        this(key);
        SetTimeStamp(date);
    }

    public Session(String key, String date)
    {
        this(key);
        SetTimeStamp(date);
    }

    public Session(String key, long date)
    {
        this(key);
        SetTimeStamp(date);
    }

    public Session(String key)
    {
        Key = key;
        SetDatetimeNow();
    }

    @Override
    public String toString() {
        return Key;
    }
}
