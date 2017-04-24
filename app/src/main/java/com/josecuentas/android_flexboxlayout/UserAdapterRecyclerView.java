package com.josecuentas.android_flexboxlayout;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jcuentas on 24/04/17.
 */

public class UserAdapterRecyclerView extends RecyclerView.Adapter<UserAdapterRecyclerView.ViewHolderUser>
    implements Filterable {


    private List<Item> mUserList;
    private List<Item> mUserFilterList;
    private List<Item> mUserTagList;
    private OnListener mOnListener;
    private UserFilter mUserFilter;

    public UserAdapterRecyclerView() {

    }

    public void setUserList(List<? extends Item> userList) {
        mUserList = (List<Item>) userList;
        mUserFilterList = new LinkedList<>(mUserList);
    }

    public void setOnListener(OnListener onListener) {
        mOnListener = onListener;
    }

    public void setUserTagList(List<? extends Item> userTagList) {
        mUserTagList = (List<Item>) userTagList ;
    }

    public void setUserFilterList(List<? extends Item> userFilterList) {
        mUserFilterList = (List<Item>) userFilterList;
    }

    public List<? extends Item> getUserFilterList() {
        return mUserFilterList;
    }

    @Override public ViewHolderUser onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolderUser(view);
    }

    @Override public void onBindViewHolder(final ViewHolderUser holder, final int position) {
        final Item user = mUserFilterList.get(holder.getAdapterPosition());
        //final User user = mUserList.get(position);
        holder.mTviItem.setText(user.getValue());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                //createChild(user.getName());
                mOnListener.onItemClick(user);
                mUserTagList.add(user);
                removeItem(holder.getAdapterPosition());
            }
        });
    }

    public void removeItem(int index) {
        mUserFilterList.remove(index);
        notifyItemRemoved(index);
    }

    public void refresh() {
        mUserFilterList.clear();
        mUserFilterList.addAll(mUserList);
        mUserFilterList.removeAll(mUserTagList); //todo: remove tags
        notifyDataSetChanged();
    }

    @Override public int getItemCount() {
        return mUserFilterList.size();
    }

    class UserFilter extends Filter {

        UserAdapterRecyclerView adapter;
        private List<Item> userOriginalList;
        private List<Item> userFilterList;

        public UserFilter(UserAdapterRecyclerView adapter, List<Item> userOriginalList) {
            this.adapter = adapter;
            this.userOriginalList = new LinkedList<>(userOriginalList);
            this.userFilterList = new ArrayList<>();
        }

        @Override protected FilterResults performFiltering(CharSequence constraint) {
            this.userFilterList.clear();
            FilterResults filterResults = new FilterResults();
            if (constraint.length() == 0) {
                this.userFilterList.addAll(this.userOriginalList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Item user : this.userOriginalList) {
                    String name = user.getValue().toLowerCase();
                    if (name.contains(filterPattern)) {
                        this.userFilterList.add(user);
                    }
                }
            }
            this.userFilterList.removeAll(mUserTagList); //todo: remove tags

            filterResults.values = this.userFilterList;
            filterResults.count = this.userFilterList.size();
            return filterResults;
        }

        @Override protected void publishResults(CharSequence constraint, FilterResults results) {
            this.adapter.mUserFilterList.clear();
            this.adapter.mUserFilterList.addAll((ArrayList<Item> ) results.values);
            this.adapter.notifyDataSetChanged();
        }
    }

    class ViewHolderUser extends RecyclerView.ViewHolder {

        TextView mTviItem;

        public ViewHolderUser(View itemView) {
            super(itemView);
            mTviItem = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

    public interface OnListener {
        void onItemClick(Item user);
    }

    @Override public Filter getFilter() {
        if (mUserFilter == null) {
            mUserFilter = new UserFilter(this, mUserList);
        }
        return mUserFilter;
    }
}
