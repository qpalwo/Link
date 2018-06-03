package com.example.xyx.link;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xyx.link.Bean.DataBean;
import com.example.xyx.link.Bean.Group;
import com.example.xyx.link.Bean.User;

import java.util.ArrayList;
import java.util.List;

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
        mDataUtil.getOriginData(mGroup, new CallBack<DataBean>() {
            @Override
            public void onSuccess(DataBean data) {
                mDataBean = data;
                notifyDataSetChanged();
            }
            @Override
            public void onFu(String msg) {
            }
        });

    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.generation_item, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        if (position == mDataBean.size() - 1) {
            holder.lineBottom.setVisibility(View.INVISIBLE);
        }
        ArrayList<User> userList = mDataBean.get(position);
        GenerationAdapter generationAdapter = new GenerationAdapter(userList, new GenerationAdapter.AvatorClickListener() {
            @Override
            void click(View v, int position) {
                if(holder.isExpanded){
                    TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                            0.0f, Animation.RELATIVE_TO_SELF, -1.0f,
                            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                            0.0f);
                    mHiddenAction.setDuration(500);
                    holder.detailInfo.startAnimation(mHiddenAction);
                    holder.detailInfo.setVisibility(View.GONE);
                    holder.isExpanded = false;
                }else {
                    holder.isExpanded = true;
                    TranslateAnimation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1.0f,
                            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                            0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    mShowAction.setDuration(500);
                    User user = userList.get(position);
                    holder.detailInfo.setAnimation(mShowAction);
                    holder.detailInfo.setVisibility(View.VISIBLE);
                    holder.weChat.setText(user.getWeChat());
                    holder.qq.setText(user.getQq());
                    holder.phonenumber.setText(user.getPhone());
                    mDataUtil.getAllRelation(mGroup, user, new CallBack<List<DataBean>>() {
                        @Override
                        public void onSuccess(List<DataBean> data) {
                            holder.relations.setAdapter(new LinksAdapter((ArrayList<DataBean>) data));
                        }
                        @Override
                        public void onFu(String msg) { }
                    });
                    holder.addrelation.setOnClickListener(a -> {
                        //todo:弹框框......
                        //todo:等你来.....
                        //todo:这里有position,写个relationname
                        mDataUtil.newRelation();
                    });
                }
            }
        });
        holder.mRecyclerView.setAdapter(generationAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public int getItemCount() {
        return mDataBean == null ? 0 : mDataBean.size();
    }

    class GroupViewHolder extends RecyclerView.ViewHolder {
        ImageView lineBottom;
        Button point, addrelation;
        RecyclerView mRecyclerView;
        RelativeLayout detailInfo;
        CardView mCardView;
        TextView weChat, qq, phonenumber;
        ListView relations;
        boolean isExpanded = false;

        public GroupViewHolder(View itemView) {
            super(itemView);
            lineBottom = itemView.findViewById(R.id.item_line_bottom);
            point = itemView.findViewById(R.id.generation_point);
            mRecyclerView = itemView.findViewById(R.id.generation_list);
            detailInfo = itemView.findViewById(R.id.detail_info);
            weChat = itemView.findViewById(R.id.wechat_info);
            qq = itemView.findViewById(R.id.qq_info);
            phonenumber = itemView.findViewById(R.id.phone_info);
            relations = itemView.findViewById(R.id.detail_relation);
            mCardView = itemView.findViewById(R.id.generation_card);
            addrelation = itemView.findViewById(R.id.add_relation);
        }


    }
}
