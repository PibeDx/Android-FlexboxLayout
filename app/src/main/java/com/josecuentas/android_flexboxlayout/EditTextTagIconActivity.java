package com.josecuentas.android_flexboxlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.josecuentas.android_flexboxlayout.widget.EditTextTagIcon;

import java.util.ArrayList;
import java.util.List;

public class EditTextTagIconActivity extends AppCompatActivity {

    EditTextTagIcon mEditTextTagIcon;
    private List<User> mUserList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text_tag_icon);
        mEditTextTagIcon = (EditTextTagIcon) findViewById(R.id.etetiName);

        for (int i = 0; i < 10; i++) {
            mUserList.add(new User("" + (i + 1), "user " + (i+1)));
        }

        mEditTextTagIcon.setUserList(mUserList);
    }
}
