package com.example.lab3_lab4_graphbuilder_sidorov493;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

public class TextVisibleView extends LinearLayout {

    Context context;
    public Context GetContext()
    {
        return context;
    }

    private EditText inputText;
    public EditText InputText()
    {
        return inputText;
    }

    public String GetText()
    {
        return InputText().getText().toString();
    }

    public void SetText(String text)
    {
        InputText().setText(text);
    }

    private CheckBox checkBoxVisible;
    public CheckBox CheckBoxVisible()
    {
        return checkBoxVisible;
    }

    public boolean IsTextVisible()
    {
        return CheckBoxVisible().isChecked();
    }

    public void SetTextVisible(Boolean visible)
    {
        CheckBoxVisible().setChecked(visible);
    }

    public TextVisibleView(Context context) {
        super(context);
        this.context = context;

        setOrientation(HORIZONTAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        this.setLayoutParams(params1);

        inputText = new EditText(GetContext());
        inputText.setLayoutParams(params1);
        this.addView(inputText);


        checkBoxVisible = new CheckBox(GetContext());
        checkBoxVisible.setLayoutParams(params1);
        checkBoxVisible.setText("Отображать");
        this.addView(checkBoxVisible);


    }
}
