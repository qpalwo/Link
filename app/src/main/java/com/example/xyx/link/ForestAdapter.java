package com.example.xyx.link;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xyx.link.Bean.Group;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dizzylay on 2018/6/2.
 */
public class ForestAdapter extends RecyclerView.Adapter<ForestAdapter.ViewHolder> {

    private List<Group> groupList;

    private ItemClickedListener clickedListener;

    public ForestAdapter(ItemClickedListener clickedListener) {
        this.clickedListener = clickedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forest_item, parent,
                false);
        return new ViewHolder(view, clickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Group group = groupList.get(position);
        holder.groupName.setText(group.getName());
        String number = "共" + String.valueOf(group.getGroupNumber()) + "人";
        holder.memberAmount.setText(number);
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public void setGroupList(List<Group> groupList) {
        if (groupList == null) {
            groupList = new ArrayList<>();
        }
        this.groupList = groupList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView groupName;
        TextView memberAmount;

        ViewHolder(View itemView, ItemClickedListener clickedListener) {
            super(itemView);
            groupName = itemView.findViewById(R.id.group_name);
            memberAmount = itemView.findViewById(R.id.member_amount);
            itemView.setOnClickListener(v -> {
                clickedListener.onItemClicked(v, getAdapterPosition());
            });
        }
    }

    public interface ItemClickedListener {
        void onItemClicked(View view, int index);
    }
}
