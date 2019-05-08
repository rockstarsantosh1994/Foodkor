package com.example.xxovek.foodkor;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DynamicViews {

    Context ctx;

    public DynamicViews(Context ctx) {
        this.ctx = ctx;
    }

    public EditText destextview(Context context, String text){

        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final EditText edittext = new EditText(context);
        edittext.setLayoutParams(lparams);
        edittext.setTextSize(10);
        edittext.setText( text );
        Toast.makeText(context, "222"+edittext.getText().toString(), Toast.LENGTH_SHORT).show();

        return edittext;
    }
}
