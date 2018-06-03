package com.example.xyx.link;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xyx.link.Bean.DataBean;

import java.util.ArrayList;

/**
 * Created by 陈钊燚 on 2018/6/3.
 * QQ 1215638092
 * Github FourfireChen
 */
public class LinksAdapter extends BaseAdapter {
    ArrayList<DataBean> mDataBeans;

    public LinksAdapter(ArrayList<DataBean> dataBeans) {
        mDataBeans = dataBeans;
    }

    @Override
    public int getCount() {
        return mDataBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.textview, parent, false);
        TextView textView = view.findViewById(R.id.relation_name);
        textView.setText(mDataBeans.get(position).getRelationName());
        return view;
    }
}
