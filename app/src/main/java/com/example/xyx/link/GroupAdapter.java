package com.example.xyx.link;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.xyx.link.Bean.DataBean;
import com.example.xyx.link.Bean.Group;
import com.example.xyx.link.Bean.User;

import java.util.ArrayList;

/**
 * Created by 陈钊燚 on 2018/6/2.
 * QQ 1215638092
 * Github FourfireChen
 */
public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    Context mContext;
    Group mGroup;
    DataUtil mDataUtil;
    DataBean mDataBean;

    public GroupAdapter(Context context, Group group) {
        mContext = context;
        mGroup = group;
        mDataUtil = new DataUtil(context);
        mDataBean = mDataUtil.getOriginData(mGroup);
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.generation_item, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        if (position == 0) {
            holder.lineTop.setVisibility(View.INVISIBLE);
        }
        if (position == mDataBean.size() - 1) {
            holder.lineBottom.setVisibility(View.INVISIBLE);
        }
        ArrayList<User> userList = mDataBean.get(position);
        GenerationAdapter generationAdapter = new GenerationAdapter(userList);
        holder.mRecyclerView.setAdapter(generationAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public int getItemCount() {
        return mDataBean.size();
    }

    class GroupViewHolder extends RecyclerView.ViewHolder {
        ImageView lineTop, lineBottom;
        Button point;
        RecyclerView mRecyclerView;

        public GroupViewHolder(View itemView) {
            super(itemView);
            lineBottom = itemView.findViewById(R.id.item_line_bottom);
            lineTop = itemView.findViewById(R.id.item_line_top);
            point = itemView.findViewById(R.id.generation_point);
            mRecyclerView = itemView.findViewById(R.id.generation_list);
        }
    }
}
