package com.example.lab3_lab4_graphbuilder_sidorov493;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DB_Graphs extends SQLiteOpenHelper {
    public DB_Graphs(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //String sql = "CREATE TABLE notes (id INT, txt Text);";
        //db.execSQL(sql);

        String graphTable = "CREATE TABLE graph  (\n" +
                "  id INTEGER NOT NULL,\n" +
                "  name VARCHAR(30) NOT NULL,\n" +
                "  timestamp DATETIME DEFAULT (CURRENT_TIMESTAMP) NOT NULL,\n" +
                "  PRIMARY KEY (id)\n" +
                ")";
        db.execSQL(graphTable);

        String nodeTable = "CREATE TABLE node  (\n" +
                "  id INTEGER NOT NULL,\n" +
                "  graph INTEGER NOT NULL,\n" +
                "  x FLOAT NOT NULL,\n" +
                "  y FLOAT NOT NULL,\n" +
                "  name VARCHAR(30) NOT NULL,\n" +
                "  PRIMARY KEY (id),\n" +
                "  FOREIGN KEY(graph) REFERENCES graph (id) ON DELETE CASCADE\n" +
                ")";
        db.execSQL(nodeTable);

        String linkTable = "CREATE TABLE link  (\n" +
                "  id INTEGER NOT NULL,\n" +
                "  source INTEGER NOT NULL,\n" +
                "  target INTEGER NOT NULL,\n" +
                "  orientatin Integer Not null, \n" +
                "  value FLOAT NOT NULL,\n" +
                "  valueVisible Integer Not null, \n" +
                "  Text VARCHAR(200) NOT NULL,\n" +
                "  textVisible Integer Not null, \n" +
                "  PRIMARY KEY (id),\n" +
                "  UNIQUE (source, target),\n" +
                "  FOREIGN KEY(source) REFERENCES node (id) ON DELETE CASCADE,\n" +
                "  FOREIGN KEY(target) REFERENCES node (id) ON DELETE CASCADE\n" +
                ")";
        db.execSQL(linkTable);
    }

    public void GetGraph()
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "Select * From graph;";
        Cursor cur = db.rawQuery(sql,null);
    }

    public int getMaxId()
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "Select Max(id) From notes;";
        Cursor cur = db.rawQuery(sql,null);
        if(cur.moveToFirst() == true) return cur.getInt(0);
        return 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void AddNote(int id, String stxt)
    {
        String sid = String.valueOf(id);
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO notes VALUES ("+sid+", '"+stxt+"');";
        db.execSQL(sql);
    }

    public String getNote(int id)
    {
        String sid = String.valueOf(id);
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT txt FROM notes WHERE id = "+sid+";";
        Cursor cur = db.rawQuery(sql, null);
        if(cur.moveToFirst() == true) return cur.getString(0);
        return "";
    }

    public void getAllNotes (ArrayList<Graph> lst)
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT id, txt FROM notes;";
        Cursor cur = db.rawQuery(sql,null);
        if(cur.moveToFirst() == true)
        {
            do {

                Graph n = new Graph();
                //n.id = cur.getInt(0);
                //n.txt = cur.getString(1);
                lst.add(n);
            }
            while(cur.moveToNext() == true);
        }
    }

    public void AlterNote(int id, String stxt)
    {
        String sid = String.valueOf(id);
        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE notes SET txt = '"+stxt+"' WHERE id = "+sid+";";
        db.execSQL(sql);
    }

    public void DeleteNote(int id)
    {
        String sid = String.valueOf(id);
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM notes WHERE id = " + sid + ";";
        db.execSQL(sql);
    }

    public void DeleteAllNotes()
    {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM notes;";
        db.execSQL(sql);

    }
}
