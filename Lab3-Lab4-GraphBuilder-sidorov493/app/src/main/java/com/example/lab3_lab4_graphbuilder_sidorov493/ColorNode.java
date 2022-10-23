package com.example.lab3_lab4_graphbuilder_sidorov493;

import android.graphics.Color;

public class ColorNode {
    int fill = Color.rgb(255, 255, 255);
    public int GetBorderColor()
    {
        return fill;
    }

    public int GetFillColor()
    {
        int color = fill;
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(50,red, green, blue);
    }

    public int GetBorderRed()
    {
        return Color.red(GetBorderColor());
    }

    public int GetBorderGreen()
    {
        return Color.green(GetBorderColor());
    }

    public int GetBorderBlue()
    {
        return Color.green(GetBorderColor());
    }

    public int GetFillRed()
    {
        return Color.red(GetFillColor());
    }

    public int GetFillGreen()
    {
        return Color.green(GetFillColor());
    }

    public int GetFillBlue()
    {
        return Color.green(GetFillColor());
    }

    public void SetFillColor(int color)
    {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        fill = Color.rgb(red, green, blue);
    }

    public void SetFillColor(int red, int green, int blue)
    {
        SetFillColor(Color.rgb(red, green, blue));
    }

    public ColorNode()
    {

    }

    public ColorNode(int color)
    {
        this();
        SetFillColor(color);
    }


    public ColorNode(int red, int green, int blue)
    {
        this();
        SetFillColor(Color.rgb(red, green, blue));
    }

}
