package com.example.lab3_lab4_graphbuilder_sidorov493;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.ContentValues;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;

public class DB_Sessions extends SQLiteOpenHelper {
    public DB_Sessions(@Nullable Activity context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        ctx = context;
    }

    public DB_Sessions(Activity context)
    {
        this(context, "Session.db", null, 1);
    }


    Activity ctx;

    public  Activity GetActivity()
    {
        return ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        SQLiteDatabase db = sqLiteDatabase;
        String urlTable = "CREATE TABLE "+sessionTable+"  (\n" +
                "  "+sessionColumn+" VARCHAR(255) NOT NULL\n" +
                ");";
        db.execSQL(urlTable);
        UpdateSession(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    String sessionTable = "sessionTable";
    String sessionColumn = "session";

    public int Count()
    {
        try {
            SQLiteDatabase db = getReadableDatabase();
            String sql = "SELECT Count(*) FROM "+sessionTable+";";
            Cursor cur = db.rawQuery(sql, null);
            int count = 0;
            if (cur.moveToFirst() == true) {

                count = cur.getInt(0);
            }
            cur.close();
            return  count;
        } catch (Exception ex) {
            return  0;
        }
    }



    public void UpdateSession(SQLiteDatabase db)
    {

        try {
            if(Count() < 1)
                throw new Exception();

            ContentValues context = new ContentValues();
            context.put(sessionColumn, GrapsParams.GetSession(ctx));
            db.update(sessionTable, context, null, null);
        }
        catch (Exception ex)
        {

            ContentValues context = new ContentValues();
            context.put(sessionColumn, GrapsParams.GetSession(ctx));
            db.insert(sessionTable, null, context);
        }
        GetSession();
    }


    public void UpdateSession()
    {
        UpdateSession(getWritableDatabase());
    }


    public void GetSession()
    {
        try {
            try {
                SQLiteDatabase db = getReadableDatabase();
                String sql = "SELECT "+sessionColumn+" FROM "+sessionTable+";";
                Cursor cur = db.rawQuery(sql, null);
                if (cur.moveToFirst() == true) {

                    GrapsParams.OpenSession( cur.getString(0));
                    cur.close();
                }
            } catch (Exception ex) {
                UpdateSession();
            }
        }
        catch(Exception ex)
        {

        }
    }


    public static DB_Sessions GetDB(Activity context)
    {
        return  new DB_Sessions(context);
    }

    public static String GetSession(Activity ctx)
    {
        GetDB(ctx).GetSession();
        return GrapsParams.GetSession(ctx);
    }


    public static String SetSession(Activity ctx)
    {
        DB_Sessions db = GetDB(ctx);
        db.UpdateSession();
        return GetSession(ctx);
    }

}
