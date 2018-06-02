package com.example.xyx.link;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 陈钊燚 on 2018/6/2.
 * QQ 1215638092
 * Github FourfireChen
 */
public class GenerationAdapter extends RecyclerView.Adapter<GenerationAdapter.GenerationViewHolder> {


    @NonNull
    @Override
    public GenerationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull GenerationViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
