package com.example.lab3_lab4_graphbuilder_sidorov493;
import android.content.ContentValues;
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

    public static DB_Graphs CreateDB(@Nullable Context context, @Nullable String name)
    {
        return CreateDB(context, name, null, 1);
    }

    public static DB_Graphs CreateDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version)
    {
        DB_Graphs graphs = new DB_Graphs(context, name, factory, version);
        graphs.GetGraphs();
        return graphs;
    }



    public void GetGraphs()
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "Select * From graph;";
        Cursor cur = db.rawQuery(sql,null);
    }

    public int getMaxGraphId()
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "Select Max(id) From graph;";
        Cursor cur = db.rawQuery(sql,null);
        if(cur.moveToFirst() == true) return cur.getInt(0);
        return 0;
    }

    public int getCountGraphId(int id)
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "Select Count(id) From graph where id="+id+";";
        Cursor cur = db.rawQuery(sql,null);
        if(cur.moveToFirst() == true) return cur.getInt(0);
        return 0;
    }

    public int getMaxNodeId()
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "Select Max(id) From node;";
        Cursor cur = db.rawQuery(sql,null);
        if(cur.moveToFirst() == true) return cur.getInt(0);
        return 0;
    }

    public int getCountLinkId(int id)
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "Select Count(id) From link where id="+id+";";
        Cursor cur = db.rawQuery(sql,null);
        if(cur.moveToFirst() == true) return cur.getInt(0);
        return 0;
    }

    public int getMaxLinkId()
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "Select Max(id) From link;";
        Cursor cur = db.rawQuery(sql,null);
        if(cur.moveToFirst() == true) return cur.getInt(0);
        return 0;
    }

    public int getCountNodeId(int id)
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "Select Count(id) From node where id="+id+";";
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

    public void DeleteAllNodes(Graph graph)
    {
        for(int i = 0; i < graph.LinkCount(); i++)
        {
            delete_node(graph.GetNode(i).Get_API_ID());
        }

    }

    public void delete_graph(Graph graph)
    {
        DeleteAllNodes(graph);
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM graph where id="+graph.Get_API_ID()+";";
        db.execSQL(sql);
    }

    public void delete_node(int node)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM node where id="+node+";";
        db.execSQL(sql);
        db = getWritableDatabase();
        sql = "DELETE FROM link where source="+node+" or target="+node+";";
        db.execSQL(sql);
    }

    public void delete_link(int link)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM link where id="+link+";";
        db.execSQL(sql);
    }

    public void upload_graph(Graph graph)
    {
        int api_id = graph.Get_API_ID();
        int max = getMaxGraphId();
        int count = getCountGraphId(api_id);
        if(count != 0)
        {
            SQLiteDatabase db = getWritableDatabase();
            String sql = "Update graph set name='"+graph.GetName()+"' where id="+graph.Get_API_ID()+";";
            db.execSQL(sql);
        }
        else
        {
            SQLiteDatabase db = getWritableDatabase();
            graph.Set_API_ID(max+1);
            String sql = "INSERT INTO graph (id, name) VALUES ("+graph.Get_API_ID()+", '"+graph.GetName()+"');";
            db.execSQL(sql);
        }
        api_id = graph.Get_API_ID();
        ArrayList<Integer> IDs = new ArrayList<Integer>();
        IDs.clear();
        for(int i = 0; i < graph.NodeCount(); i++)
        {
            Node node = graph.GetNode(i);
            upload_node(node);
            IDs.add(node.Get_API_ID());
        }
        ArrayList<Node> nodes = GetListNodes(api_id);
        for(int i = 0; i < nodes.size(); i++)
        {
            Node node = nodes.get(i);
            int id = node.Get_API_ID();
            if(!IDs.contains(id))
            {
                delete_node(id);
            }
        }

        IDs.clear();
        for(int i = 0; i < graph.LinkCount(); i++)
        {
            Link node = graph.GetLink(i);
            upload_link(node);
            IDs.add(node.Get_API_ID());
        }
        ArrayList<Link> links = GetListLinks(api_id);
        for(int i = 0; i < links.size(); i++)
        {
            Link node = links.get(i);
            int id = node.Get_API_ID();
            if(!IDs.contains(id))
            {
                delete_link(id);
            }
        }
    }

    public void upload_node(Node graph)
    {
        int api_id = graph.Get_API_ID();
        int max = getMaxNodeId();
        int count = getCountNodeId(api_id);
        if(count != 0)
        {
            SQLiteDatabase db = getWritableDatabase();
            String sql = "Update node set name='"+graph.GetName()+"' where id="+graph.Get_API_ID()+";";
            //db.execSQL(sql);
            ContentValues values = new ContentValues();
            //values.put("id", graph.Get_API_ID());
            values.put("name", graph.GetName());
            values.put("x", graph.X);
            values.put("y", graph.Y);
            //values.put("graph", graph.GetGraph().Get_API_ID());
            db.update("node", values, "id="+graph.Get_API_ID(), null);
        }
        else
        {
            SQLiteDatabase db = getWritableDatabase();
            graph.Set_API_ID(max+1);
            //String sql = "INSERT INTO graph (id, name, x, y) VALUES ("+graph.Get_API_ID()+", '"+graph.GetName()+"', "+graph.X+", "+graph.Y+");";
            //db.execSQL(sql);
            ContentValues values = new ContentValues();
            values.put("id", graph.Get_API_ID());
            values.put("name", graph.GetName());
            values.put("x", graph.X);
            values.put("y", graph.Y);
            values.put("graph", graph.GetGraph().Get_API_ID());

            db.insert("node", null, values);
        }
    }

    public void upload_link(Link graph)
    {
        int api_id = graph.Get_API_ID();
        int max = getMaxLinkId();
        int count = getCountLinkId(api_id);
        if(count != 0)
        {
            SQLiteDatabase db = getWritableDatabase();
            String sql = "Update node set name='"+graph.GetName()+"' where id="+graph.Get_API_ID()+";";
            //db.execSQL(sql);
            ContentValues values = new ContentValues();
            values.put("source", graph.Source().Get_API_ID());
            values.put("target", graph.Target().Get_API_ID());
            int orientation = 0;
            if(graph.Orientation)
                orientation = 1;
            values.put("orientatin", orientation);
            orientation = 0;
            if(graph.TextVisible)
                orientation = 1;
            values.put("textVisible", orientation);
            orientation = 0;
            if(graph.ValueVisible)
                orientation = 1;
            values.put("valueVisible", orientation);
            values.put("Text", graph.Text);
            values.put("value", graph.Value);
            db.update("link", values, "id="+graph.Get_API_ID(), null);
        }
        else
        {
            SQLiteDatabase db = getWritableDatabase();
            graph.Set_API_ID(max+1);
            //String sql = "INSERT INTO graph (id, name, x, y) VALUES ("+graph.Get_API_ID()+", '"+graph.GetName()+"', "+graph.X+", "+graph.Y+");";
            //db.execSQL(sql);
            ContentValues values = new ContentValues();
            values.put("id", graph.Get_API_ID());
            //values.put("name", graph.GetName());
            values.put("source", graph.Source().Get_API_ID());
            values.put("target", graph.Target().Get_API_ID());
            int orientation = 0;
            if(graph.Orientation)
                orientation = 1;
            values.put("orientatin", orientation);
            orientation = 0;
            if(graph.TextVisible)
                orientation = 1;
            values.put("textVisible", orientation);
            orientation = 0;
            if(graph.ValueVisible)
                orientation = 1;
            values.put("valueVisible", orientation);
            values.put("Text", graph.Text);
            values.put("value", graph.Value);
            db.insert("link", null, values);
        }
    }

    public ArrayList<Graph> GetListGraphs()
    {
        ArrayList<Graph> lst = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT id, name, timestamp FROM graph;";
        Cursor cur = db.rawQuery(sql,null);
        if(cur.moveToFirst() == true)
        {
            do {

                Graph n = new Graph();
                //n.id = cur.getInt(0);
                //n.txt = cur.getString(1);
                n.Set_API_ID(cur.getInt(0));
                n.SetName(cur.getString(1));
                n.TimeStamp = cur.getString(2);
                lst.add(n);
            }
            while(cur.moveToNext() == true);
        }

        return lst;
    }

    public void GetListNodes(Graph graph)
    {
        ArrayList<Node> lst = GetListNodes(graph.Get_API_ID());
        for(int i =0; i<lst.size();i++)
        {
            graph.AddNode(lst.get(i));
        }
    }

    public ArrayList<Node> GetListNodes(int id)
    {
        ArrayList<Node> lst = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT id, name, x, y FROM node where graph="+id+";";
        Cursor cur = db.rawQuery(sql,null);
        if(cur.moveToFirst() == true)
        {
            do {

                Graph graph = new Graph();
                Node n = new Node(graph);
                //n.id = cur.getInt(0);
                //n.txt = cur.getString(1);
                n.TimeStamp = graph.TimeStamp;
                n.Set_API_ID(cur.getInt(0));
                n.SetName(cur.getString(1));
                n.X = cur.getFloat(2);
                n.Y = cur.getFloat(3);
                lst.add(n);
            }
            while(cur.moveToNext() == true);
        }


        return lst;
    }

    public ArrayList<Link> GetListLinks(Graph graph)
    {
        ArrayList<Link> lst = new ArrayList<>();
        for(int i = 0; i< graph.NodeCount(); i++)
        {
            lst.addAll(GetListLinks(graph.GetNode(i)));
        }
        return lst;
    }

    public ArrayList<Link> GetListLinks(int graph)
    {
        Graph graph1 = GetGraph(graph);
        GetListNodes(graph1);
        return GetListLinks(graph1);
    }


    public ArrayList<Link> GetListLinks(Node node)
    {
        ArrayList<Link> lst = new ArrayList<>();

        int id = node.Get_API_ID();
        SQLiteDatabase db = getReadableDatabase();
        Graph graph = node.GetGraph();
        String sql = "SELECT id, target, text, value, textVisible, valueVisible, orientatin FROM link where source="+id+";";
        Cursor cur = db.rawQuery(sql,null);
        if(cur.moveToFirst() == true)
        {
            do {


                Link n = new Link(graph);
                //n.id = cur.getInt(0);
                //n.txt = cur.getString(1);
                n.TimeStamp = graph.TimeStamp;
                n.Set_API_ID(cur.getInt(0));
                int source = node.ID();
                int target = cur.getInt(1);
                target = graph.IdNodeFromApi(target);
                n.SetNodes(source, target);
                n.Orientation = cur.getInt(6) == 1;
                n.Text = cur.getString(2);
                n.Value = cur.getFloat(3);
                n.TextVisible = cur.getInt(4) == 1;
                n.ValueVisible = cur.getInt(5) == 1;
                lst.add(n);
                graph.AddLink(n);
            }
            while(cur.moveToNext() == true);
        }


        return lst;
    }

    public Graph GetGraph(int id)
    {
        ArrayList<Graph> lst = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT id, name, timestamp FROM graph where id="+id+";";
        Cursor cur = db.rawQuery(sql,null);
        if(cur.moveToFirst() == true)
        {
            do {

                Graph n = new Graph();
                //n.id = cur.getInt(0);
                //n.txt = cur.getString(1);
                n.Set_API_ID(cur.getInt(0));
                n.SetName(cur.getString(1));
                n.TimeStamp = cur.getString(2);
                lst.add(n);
            }
            while(cur.moveToNext() == true);
        }

        return lst.get(0);
    }
}
