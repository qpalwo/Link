package com.example.xyx.link;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.xyx.link.Bean.Group;
import com.example.xyx.link.Bean.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

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
    DataUtil dataUtil;
    @BindView(R.id.relation_toolbar)
    Toolbar mRelationToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relation);
        ButterKnife.bind(this);
        setSupportActionBar(mRelationToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        group = (Group) getIntent().getExtras().getSerializable("group");
        if (group != null) {
            mGroupAdapter = new GroupAdapter(this, group);
            mRecyclerView = findViewById(R.id.relation_recyclerview);
            mRecyclerView.setAdapter(mGroupAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        dataUtil = new DataUtil(this);
        /*DataUtil dataUtil = new DataUtil(this);
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("username", "876");
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                dataUtil.newGroupAddUser(group, list.get(0), 1);
            }
        });*/

    }

    @OnClick(R.id.add_member)
    public void onViewClicked() {
        AlertDialog.Builder alert = new AlertDialog.Builder(RelationshipActivity.this);
        final View dialogView = LayoutInflater.from(RelationshipActivity.this).inflate(R.layout.invite_dialog, null);
        ImageView back = dialogView.findViewById(R.id.back_dialog);
        Button finish = dialogView.findViewById(R.id.finish_invite);
        EditText userphoneEdit = dialogView.findViewById(R.id.userphone_edit);
        EditText levelEdit = dialogView.findViewById(R.id.level_edit);
        alert.setView(dialogView);
        AlertDialog dialog = alert.create();
        back.setOnClickListener(v -> {
            dialog.dismiss();
        });
        finish.setOnClickListener(v -> {
            String userphone = userphoneEdit.getText().toString();
            if (TextUtils.isEmpty(userphone) || TextUtils.isEmpty(levelEdit.getText().toString())) {
                Toast.makeText(RelationshipActivity.this, "can't be null", Toast.LENGTH_SHORT).show();
                return;
            }
            int level = Integer.valueOf(levelEdit.getText().toString());
            BmobQuery<User> bmobUserBmobQuery = new BmobQuery<>();
            bmobUserBmobQuery.addWhereEqualTo("username", userphone);
            bmobUserBmobQuery.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> list, BmobException e) {
                    if(list == null && list.size() == 0){
                        Toast.makeText(RelationshipActivity.this, "没有找到人", Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        dataUtil.newGroupAddUser(group, list.get(0), level);
                        dialog.dismiss();
                        RelationshipActivity.this.finish();
                    }

                }
            });
        });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 20) {

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
