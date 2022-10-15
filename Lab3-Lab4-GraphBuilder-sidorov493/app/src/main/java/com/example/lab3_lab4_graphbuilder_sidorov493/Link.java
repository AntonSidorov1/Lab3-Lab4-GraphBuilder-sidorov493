package com.example.lab3_lab4_graphbuilder_sidorov493;

public class Link {
    public Node Start, End;
    public String Text = "";
    private String Number = "";
    public String GetTextNumber()
    {
        return Number;
    }

    public boolean IsNotErrow()
    {
        return LinkErrow == com.example.lab3_lab4_graphbuilder_sidorov493.LinkErrow.None;
    }

    public boolean IsErrow()
    {
        return !IsNotErrow();
    }

    public boolean IsStartErrow()
    {
        return LinkErrow == com.example.lab3_lab4_graphbuilder_sidorov493.LinkErrow.Start;
    }

    public boolean IsEndtErrow()
    {
        return LinkErrow == com.example.lab3_lab4_graphbuilder_sidorov493.LinkErrow.End;
    }

    public LinkErrow LinkErrow = com.example.lab3_lab4_graphbuilder_sidorov493.LinkErrow.None;

    public LinkErrow GetErrow()
    {
        return  LinkErrow;
    }

    public void SetErrow(LinkErrow errow)
    {
        LinkErrow = errow;
    }

    public void SetStartErrow() {
        LinkErrow = LinkErrow.Start;
    }

    public void SetEndErrow() {
        LinkErrow = LinkErrow.End;
    }

    public void SetNotErrow() {
        LinkErrow = LinkErrow.None;
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

    public Link (Node start, Node end)
    {
        this();
        Start = start;
        End = end;
    }

    public Link(Node start, Node end, String text)
    {

        this(start, end, text, true);
    }

    public Link(Node start, Node end, String text, Boolean isName)
    {
        this(start, end);
        if(isName) {
            Text = text;
        } else
        {
            SetNumber(text);
        }
    }

    public Link(Node start, Node end, int number)
    {
        this(start, end, String.valueOf(number), false);
    }

    public Link(Node start, Node end, String text, String number)
    {
        this(start, end, text);
        SetNumber(number);
    }

    public Link(Node start, Node end, String text, int number)
    {
        this(start, end, text);
        SetNumber(number);
    }

}
