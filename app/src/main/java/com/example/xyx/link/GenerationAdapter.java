package com.example.xyx.link;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xyx.link.Bean.User;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by 陈钊燚 on 2018/6/2.
 * QQ 1215638092
 * Github FourfireChen
 */
public class GenerationAdapter extends RecyclerView.Adapter<GenerationAdapter.GenerationViewHolder> {
    ArrayList<User> mUsers = new ArrayList<>();
    AvatorClickListener mOnClickListener;
    Random mRandom = new Random();

    public GenerationAdapter(ArrayList<User> users, AvatorClickListener clickListener) {
        mUsers = users;
        mOnClickListener = clickListener;
    }

    @NonNull
    @Override
    public GenerationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_avator, parent, false);
        GenerationViewHolder generationViewHolder = new GenerationViewHolder(view);
        int avator = mRandom.nextInt() % 5;
        switch (avator){
            case 0:
                avator = R.mipmap.logo2;
                break;
            case 1:
                avator = R.mipmap.logo1;
                break;
            case 2:
                avator = R.mipmap.someone;
                break;
            case 3:
                avator = R.mipmap.username;
                break;
            case 4:
                avator = R.mipmap.password;
                break;
            default:
                avator = R.mipmap.image3;
        }
        Bitmap bitmap = BitmapFactory.decodeResource(parent.getResources(), avator);
        generationViewHolder.avator.setImageBitmap(bitmap);
        return generationViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GenerationViewHolder holder, int position) {
        holder.avator.setOnClickListener(v -> {
            mOnClickListener.click(v, position);
        });
        User user = mUsers.get(position);
        if (user.getName() != null && !user.getName().isEmpty()){
            holder.username.setText(user.getName());
        }


    }

    @Override
    public int getItemCount() {
        return mUsers == null ? 0 : mUsers.size();
    }

    class GenerationViewHolder extends RecyclerView.ViewHolder {
        ImageView avator;
        TextView username;

        public GenerationViewHolder(View itemView) {
            super(itemView);
            avator = itemView.findViewById(R.id.generation_useravator);
            username = itemView.findViewById(R.id.generation_username);
        }
    }

    abstract static class AvatorClickListener implements View.OnClickListener {

        abstract void click(View v, int position);

        @Override
        public void onClick(View v) {}
    }
}
