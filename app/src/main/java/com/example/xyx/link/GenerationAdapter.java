package com.example.xyx.link;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.ActivityChooserView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xyx.link.Bean.User;

import java.util.ArrayList;

/**
 * Created by 陈钊燚 on 2018/6/2.
 * QQ 1215638092
 * Github FourfireChen
 */
public class GenerationAdapter extends RecyclerView.Adapter<GenerationAdapter.GenerationViewHolder> {
    ArrayList<User> mUsers = new ArrayList<>();

    public GenerationAdapter(ArrayList<User> users) {
        mUsers = users;
    }

    @NonNull
    @Override
    public GenerationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_avator, parent, false);
        return new GenerationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenerationViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    class GenerationViewHolder extends RecyclerView.ViewHolder{
        ImageView avator;
        TextView username;
        public GenerationViewHolder(View itemView) {
            super(itemView);
            avator = itemView.findViewById(R.id.generation_useravator);
            username = itemView.findViewById(R.id.generation_username);
        }
    }
}
