package com.example.lab3_lab4_graphbuilder_sidorov493;

public abstract class GraphElement {
    protected String NameElement;
    public String GetName()
    {
        return NameElement;
    }
    public GraphElement(String name)
    {
        NameElement = name;
    }


    @Override
    public String toString() {
        return GetName();
    }
}
