package com.example.xyx.link;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.xyx.link.Bean.DataBean;
import com.example.xyx.link.Bean.Group;

/**
 * Created by 陈钊燚 on 2018/6/2.
 * QQ 1215638092
 * Github FourfireChen
 */
public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    DataBean mDataBean;
    //todo:获得代数
    DataUtil mDataUtil;
    Context mContext;

    public GroupAdapter(Context context, Group group) {
        mDataUtil = new DataUtil(context);
        mDataBean = mDataUtil.getOriginData(group);
        mContext = context;
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
        } else if (position == mDataBean.size()) {
            holder.lineBottom.setVisibility(View.INVISIBLE);
        }
        holder.mGenerationAdapter = new GenerationAdapter(mDataBean.get(position), mContext);
        holder.mRecyclerView.setAdapter(holder.mGenerationAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public int getItemCount() {
        return mDataBean.size();
    }

    class GroupViewHolder extends RecyclerView.ViewHolder {
        ImageView lineTop, lineBottom, point;
        RecyclerView mRecyclerView;
        GenerationAdapter mGenerationAdapter;

        public GroupViewHolder(View itemView) {
            super(itemView);
            lineBottom = itemView.findViewById(R.id.item_line_bottom);
            lineTop = itemView.findViewById(R.id.item_line_top);
            point = itemView.findViewById(R.id.generation_point);
            mRecyclerView = itemView.findViewById(R.id.generation_list);
        }
    }
}
