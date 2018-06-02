package com.example.xyx.link;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.xyx.link.Bean.User;

import java.util.ArrayList;

/**
 * Created by 陈钊燚 on 2018/6/2.
 * QQ 1215638092
 * Github FourfireChen
 */
public class GenerationAdapter extends RecyclerView.Adapter<GenerationAdapter.GenerationViewHolder> {
    ArrayList<User> mUsers;
    Context mContext;

    public GenerationAdapter(ArrayList<User> users, Context context) {
        mUsers = users;
        mContext = context;
    }

    @NonNull
    @Override
    public GenerationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_avator, parent, false);
        return new GenerationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenerationViewHolder holder, int position) {
        holder.username.setText(mUsers.get(position).getName());
        holder.avator.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, DetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", mUsers.get(position));
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    class GenerationViewHolder extends RecyclerView.ViewHolder {
        ImageView avator;
        Button username;

        public GenerationViewHolder(View itemView) {
            super(itemView);
            avator = itemView.findViewById(R.id.generation_useravator);
            username = itemView.findViewById(R.id.generation_username);
        }
    }
}
