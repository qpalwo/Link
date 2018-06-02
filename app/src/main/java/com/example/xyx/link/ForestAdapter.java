package com.example.xyx.link;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xyx.link.Bean.Group;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dizzylay on 2018/6/2.
 */
public class ForestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Group> groupList;

    private ItemClickedListener clickedListener;
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_TAIL = 1;

    public ForestAdapter(ItemClickedListener clickedListener) {
        this.clickedListener = clickedListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forest_item,
                    parent, false);
            return new ViewHolder(view, clickedListener);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forest_add_item,
                parent, false);
        return new TailViewHolder(view, clickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position == groupList.size()) {
            return;
        }
        Group group = groupList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.groupName.setText(group.getName());
        if (group.getUserRelations() != null) {
            String number = "共" + String.valueOf(group.getUserRelations().size()) + "人";
            viewHolder.memberAmount.setText(number);
        }


        if (position % 3 == 0) {
            viewHolder.image4.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return groupList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == groupList.size()) {
            return TYPE_TAIL;
        }
        return TYPE_NORMAL;
    }

    public void setGroupList(List<Group> groupList) {
        if (groupList == null) {
            groupList = new ArrayList<>();
        }
        this.groupList = groupList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView groupName;
        TextView memberAmount;
        ImageView image4;

        ViewHolder(View itemView, ItemClickedListener clickedListener) {
            super(itemView);
            groupName = itemView.findViewById(R.id.group_name);
            memberAmount = itemView.findViewById(R.id.member_amount);
            image4 = itemView.findViewById(R.id.image4);
            itemView.setOnClickListener(v -> {
                clickedListener.onItemClicked(v, getAdapterPosition());
            });
        }
    }

    class TailViewHolder extends RecyclerView.ViewHolder {

        TailViewHolder(View itemView, ItemClickedListener clickedListener) {
            super(itemView);
            itemView.setOnClickListener(clickedListener::onTailItemClicked);
        }
    }

    public interface ItemClickedListener {
        void onItemClicked(View view, int index);

        void onTailItemClicked(View view);
    }
}
