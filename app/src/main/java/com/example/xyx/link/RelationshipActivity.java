package com.example.xyx.link;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.example.xyx.link.Bean.Group;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 陈钊燚 on 2018/6/2.
 * QQ 1215638092
 * Github FourfireChen
 */
public class RelationshipActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    GroupAdapter mGroupAdapter;
    @BindView(R.id.add_member)
    ImageView mAddMember;
    Group group;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relation);
        ButterKnife.bind(this);
        group = (Group) getIntent().getExtras().getSerializable("group");
        if (group != null) {
            mGroupAdapter = new GroupAdapter(this, group);
            mRecyclerView = findViewById(R.id.relation_recyclerview);
            mRecyclerView.setAdapter(mGroupAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @OnClick(R.id.add_member)
    public void onViewClicked() {
        Intent intent = new Intent(this, InviteActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("group", group);
        intent.putExtras(bundle);
        startActivityForResult(intent, 20);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 20) {

        }
    }
}
