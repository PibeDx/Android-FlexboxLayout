package com.josecuentas.android_flexboxlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

/*
* Info: http://stackoverflow.com/a/34466167
* */

public class MainActivity extends AppCompatActivity implements UserAdapterRecyclerView.OnListener {

    private FlexboxLayout flexContainer;
    private EditText mEteName;
    private RecyclerView mRviContainer;
    private List<User> mUserList = new ArrayList<>();
    private List<User> mUserRealList = new ArrayList<>();
    private List<User> mUserFilterList = new ArrayList<>();
    private List<User> mUserTagList = new ArrayList<>();
    private UserAdapterRecyclerView adapter;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexbox);

        flexContainer = (FlexboxLayout) findViewById(R.id.flexContainer);
        mEteName = (EditText) findViewById(R.id.eteName);
        mRviContainer = (RecyclerView) findViewById(R.id.rviContainer);


        for (int i = 0; i < 10; i++) {
            mUserList.add(new User("" + (i + 1), "user " + (i+1)));
        }

        mEteName.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

                adapter.getFilter().filter(s);
            }

            @Override public void afterTextChanged(Editable s) {

            }
        });

        adapter = new UserAdapterRecyclerView();
        adapter.setUserList(mUserList);
        adapter.setUserTagList(mUserTagList);
        //adapter.setUserFilterList(mUserFilterList);
        adapter.setOnListener(this);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRviContainer.setLayoutManager(llm);
        mRviContainer.setHasFixedSize(true);
        mRviContainer.setAdapter(adapter);
        mRviContainer.setItemAnimator(null);
    }

    public void createChild(User user) {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view =  inflater.inflate(R.layout.edit_icon_tag, flexContainer, false);
        view.setTag(user);
        TextView tviItem = (TextView) view.findViewById(R.id.row_tviItem);
        tviItem.setText(user.getName());
        View vieClose = view.findViewById(R.id.iviClose);
        vieClose.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                flexContainer.removeView(view);
                User sUser = (User) view.getTag();
                mUserTagList.remove(sUser); // remove taggeable
                //adapter.getUserFilterList().add(sUser); // add list
                //adapter.notifyItemInserted(adapter.getUserFilterList().size());
                adapter.refresh();
            }
        });
        FlexboxLayout.LayoutParams layoutParams = (FlexboxLayout.LayoutParams) view.getLayoutParams();
        view.setLayoutParams(layoutParams);
        //view.setOnLongClickListener(this);
        //view.setOnClickListener(this);
        flexContainer.addView(view);
        //mUserTagList.add(user);
    }

    @Override public void onItemClick(User user) {
        createChild(user);
    }
}
