package com.josecuentas.android_flexboxlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.flexbox.FlexboxLayout;

public class MainActivity extends AppCompatActivity {

    private FlexboxLayout flexContainer;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexbox);

        flexContainer = (FlexboxLayout) findViewById(R.id.flexContainer);

        createChild();
        createChild();
        createChild();
        createChild();
        createChild();
        createChild();
    }

    public void createChild() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view =  inflater.inflate(R.layout.edit_icon_tag, flexContainer, false);
        FlexboxLayout.LayoutParams layoutParams = (FlexboxLayout.LayoutParams) view.getLayoutParams();
        view.setLayoutParams(layoutParams);
        //view.setOnLongClickListener(this);
        //view.setOnClickListener(this);
        flexContainer.addView(view);
    }
}
