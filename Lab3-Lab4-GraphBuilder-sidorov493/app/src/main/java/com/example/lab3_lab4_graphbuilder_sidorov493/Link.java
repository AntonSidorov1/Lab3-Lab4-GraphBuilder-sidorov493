package com.example.lab3_lab4_graphbuilder_sidorov493;

public class Link {
    public Node a, b;
    public String Text = "";
    private String Number = "";
    public String GetTextNumber()
    {
        return Number;
    }

    public int GetNumber()
    {
        if(Number == "")
            return 0;
        return Integer.getInteger(Number);
    }

    public void SetNumber()
    {
        Number = "";
    }

    public void SetNumber(int number)
    {
        Number = String.valueOf(number);
    }

    public void SetNumber(String number)
    {
        try
        {
            SetNumber(Integer.getInteger(number));
        }
        catch(Exception e)
        {
            SetNumber();
        }
    }

    public Link()
    {

    }

    public Link (Node a, Node b)
    {
        this();
        this.a = a;
        this.b = b;
    }

    public Link(Node a, Node b, String text)
    {

        this(a, b, text, true);
    }

    public Link(Node a, Node b, String text, Boolean isName)
    {
        this(a, b);
        if(isName) {
            Text = text;
        } else
        {
            SetNumber(text);
        }
    }

    public Link(Node a, Node b, int number)
    {
        this(a, b, String.valueOf(number), false);
    }

    public Link(Node a, Node b, String text, String number)
    {
        this(a, b, text);
        SetNumber(number);
    }

    public Link(Node a, Node b, String text, int number)
    {
        this(a, b, text);
        SetNumber(number);
    }

}
