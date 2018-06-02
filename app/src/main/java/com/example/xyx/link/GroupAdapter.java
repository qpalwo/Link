package com.example.xyx.link;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by 陈钊燚 on 2018/6/2.
 * QQ 1215638092
 * Github FourfireChen
 */
public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {


    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class GroupViewHolder extends RecyclerView.ViewHolder{
        ImageView lineTop, lineBottom;

        public GroupViewHolder(View itemView) {
            super(itemView);
        }
    }
}
