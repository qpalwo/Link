package com.example.xyx.link;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.xyx.link.Bean.User;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

/**
 * Created by 陈钊燚 on 2018/6/3.
 * QQ 1215638092
 * Github FourfireChen
 */
public class InviteResultAdapter extends RecyclerView.Adapter<InviteResultAdapter.ViewHolder> {
    ArrayList<User> mUsers;

    public InviteResultAdapter() {}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.usersummary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTextView.setText(mUsers.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return mUsers == null ? 0 : mUsers.size();
    }

    public void setUsers(ArrayList<User> users) {
        mUsers = users;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView mRoundedImageView;
        TextView mTextView;
        Button mButton;

        public ViewHolder(View itemView) {
            super(itemView);
            mRoundedImageView = itemView.findViewById(R.id.search_avator);
            mTextView = itemView.findViewById(R.id.search_username);
            mButton = itemView.findViewById(R.id.invite);
        }
    }

}
