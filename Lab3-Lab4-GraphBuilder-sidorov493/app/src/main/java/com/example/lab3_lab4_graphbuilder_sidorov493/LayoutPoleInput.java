package com.example.lab3_lab4_graphbuilder_sidorov493;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LayoutPoleInput extends LinearLayout {

    TextView signaturePole;
    EditText inputPole;

    public TextView SignaturePole()
    {
        return signaturePole;
    }

    public EditText InputPole()
    {
        return inputPole;
    }

    Context context;
    public Context GetContext()
    {
        return context;
    }

    public LayoutPoleInput(Context context) {
        super(context);
        this.context = context;
        signaturePole = new TextView(this.context);
        signaturePole.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        signaturePole.setTextSize(16f);
        inputPole = new EditText(this.context);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        signaturePole.setLayoutParams(params);
        inputPole.setLayoutParams(params);

        addView(signaturePole);
        addView(inputPole);
    }
}
