package com.example.xyx.link;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.xyx.link.Bean.Group;
import com.example.xyx.link.Bean.User;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private List<Group> groupList = new ArrayList<>();
    private DataUtil dataUtil;
    private ForestAdapter adapter;

    @BindView(R.id.avatar_toolbar)
    RoundedImageView avatarToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.group_recycler_view)
    RecyclerView groupRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "e4bdab8ef7e032628a681cf114e5f9fa");

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.setting:
                    Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                    startActivity(intent);
            }
            return true;
        });

        initData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //登录逻辑
        User user = BmobUser.getCurrentUser(User.class);
        if (user == null) {
            Log.e(TAG, "onResume: null");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void initData() {
        dataUtil = new DataUtil(this);
        dataUtil.getGroup(new CallBack<List<Group>>() {
            @Override
            public void onSuccess(List<Group> data) {
                groupList = data;
                adapter.setGroupList(groupList);
            }

            @Override
            public void onFu(String msg) {

            }
        });
    }

    private void initView() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.setting:
                    Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                    startActivity(intent);
            }
            return true;
        });
        adapter = new ForestAdapter((view, index) -> {

        });
        adapter.setGroupList(groupList);
        groupRecyclerView.setAdapter(adapter);
        groupRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @OnClick(R.id.avatar_toolbar)
    public void onViewClicked() {
        drawerLayout.openDrawer(GravityCompat.START);
    }
}
