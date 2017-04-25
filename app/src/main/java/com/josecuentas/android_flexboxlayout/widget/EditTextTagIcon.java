package com.josecuentas.android_flexboxlayout.widget;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.josecuentas.android_flexboxlayout.Item;
import com.josecuentas.android_flexboxlayout.R;
import com.josecuentas.android_flexboxlayout.User;
import com.josecuentas.android_flexboxlayout.UserAdapterRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcuentas on 25/04/17.
 */

public class EditTextTagIcon extends ConstraintLayout implements UserAdapterRecyclerView.OnListener {

    private FlexboxLayout flexContainer;
    private EditText mEteName;
    private RecyclerView mRviContainer;
    private List<Item> mUserList = new ArrayList<>();
    private List<Item> mUserTagList = new ArrayList<>();
    private UserAdapterRecyclerView adapter;

    public EditTextTagIcon(Context context) {
        super(context);
        init();
    }

    public EditTextTagIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextTagIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View inflate = inflate(getContext(), R.layout.activity_flexbox, this);
        flexContainer = (FlexboxLayout) inflate.findViewById(R.id.flexContainer);
        mEteName = (EditText) inflate.findViewById(R.id.eteName);
        mRviContainer = (RecyclerView) inflate.findViewById(R.id.rviContainer);

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
        adapter.setOnListener(this);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRviContainer.setLayoutManager(llm);
        mRviContainer.setHasFixedSize(true);
        mRviContainer.setAdapter(adapter);
        mRviContainer.setItemAnimator(null);
    }

    public void createChild(Item user) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view =  inflater.inflate(R.layout.edit_icon_tag, flexContainer, false);
        view.setTag(user);
        TextView tviItem = (TextView) view.findViewById(R.id.row_tviItem);
        tviItem.setText(user.getValue());
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

    public void setUserList(List<? extends Item> userList) {
        mUserList = (List<Item>) userList;
        adapter = new UserAdapterRecyclerView();
        adapter.setUserList(mUserList);
        adapter.setUserTagList(mUserTagList);
        adapter.setOnListener(this);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRviContainer.setLayoutManager(llm);
        mRviContainer.setHasFixedSize(true);
        mRviContainer.setAdapter(adapter);
        mRviContainer.setItemAnimator(null);
    }

    @Override public void onItemClick(Item user) {
        createChild(user);
    }
}
