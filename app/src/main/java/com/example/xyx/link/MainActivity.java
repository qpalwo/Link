package com.example.xyx.link;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.title_toolbar)
    TextView titleToolbar;
    @BindView(R.id.phone_image)
    ImageView phoneImage;
    @BindView(R.id.phone_number)
    TextView phoneNumber;
    @BindView(R.id.qq_image)
    ImageView qqImage;
    @BindView(R.id.qq_number)
    TextView qqNumber;
    @BindView(R.id.wechat_image)
    ImageView wechatImage;
    @BindView(R.id.wechat_number)
    TextView wechatNumber;
    @BindView(R.id.setting_image)
    ImageView settingImage;
    @BindView(R.id.exit_image)
    ImageView exitImage;
    @BindView(R.id.nav_view)
    NavigationView navView;
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
        getWindow().setEnterTransition(new Fade().setDuration(2000));
        getWindow().setExitTransition(new Fade().setDuration(2000));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            titleToolbar.setText("Group");
        }
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
        } else {
            initData();
            initView();
        }
    }

    private void initData() {
        dataUtil = new DataUtil(this);
        dataUtil.getGroup(new CallBack<List<Group>>() {
            @Override
            public void onSuccess(List<Group> data) {
                groupList = data;
                if (adapter != null){
                    adapter.setGroupList(data);
                }
            }

            @Override
            public void onFu(String msg) {
            }
        });
        adapter = new ForestAdapter(new ForestAdapter.ItemClickedListener() {
            @Override
            public void onItemClicked(View view, int index) {
                Intent intent = new Intent(MainActivity.this, RelationshipActivity.class);
                intent.putExtra("group", groupList.get(index));
                startActivity(intent);
            }

            @Override
            public void onTailItemClicked(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                final View dialogView = LayoutInflater.from(MainActivity.this)
                        .inflate(R.layout.create_dialog, null);
                ImageView back = dialogView.findViewById(R.id.back_dialog);
                Button finish = dialogView.findViewById(R.id.finish);
                EditText nameEdit = dialogView.findViewById(R.id.name_edit);
                alert.setView(dialogView);
                AlertDialog dialog = alert.create();
                back.setOnClickListener(v -> {
                    dialog.dismiss();
                });
                finish.setOnClickListener(v -> {
                    if (TextUtils.isEmpty(nameEdit.getText().toString())) {
                        Toast.makeText(MainActivity.this, "name can't be null", Toast
                                .LENGTH_SHORT).show();
                        return;
                    }
                    String name = nameEdit.getText().toString();
                    dataUtil.newGroup(name,name);
                    dialog.dismiss();
                });
                dialog.show();
            }
        }, this);
        adapter.setGroupList(groupList);
        groupRecyclerView.setAdapter(adapter);
        groupRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
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
    }

    @OnClick(R.id.avatar_toolbar)
    public void onViewClicked() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @OnClick({R.id.setting_image, R.id.exit_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting_image: {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.exit_image: {
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(this, LoginActivity.class);
                BmobUser.getCurrentUser().logOut();
                startActivity(intent);
                break;
            }
        }
    }
}
