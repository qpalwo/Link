package com.example.xyx.link;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.xyx.link.Bean.Group;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 陈钊燚 on 2018/6/3.
 * QQ 1215638092
 * Github FourfireChen
 */
public class InviteActivity extends AppCompatActivity {
    @BindView(R.id.invite_toolbar)
    Toolbar mInviteToolbar;
    @BindView(R.id.invite_search)
    SearchView mInviteSearch;
    @BindView(R.id.invite_search_result)
    RecyclerView mInviteSearchResult;
    private ActionBar mActionBar;
    private Group mGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        ButterKnife.bind(this);
        setSupportActionBar(mInviteToolbar);
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
        mGroup = (Group) getIntent().getExtras().getSerializable("group");
        InviteResultAdapter inviteResultAdapter = new InviteResultAdapter();
        mInviteSearchResult.setAdapter(inviteResultAdapter);
        mInviteSearchResult.setLayoutManager(new LinearLayoutManager(this));
        mInviteSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //todo:查数据获得list
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(mInviteSearchResult.getVisibility() != View.VISIBLE){
                    mInviteSearchResult.setVisibility(View.VISIBLE);
                }
                //todo:查数据
                return true;
            }
        });
        mInviteSearch.setOnCloseListener(() -> {
            mInviteSearchResult.setVisibility(View.GONE);
            return true;
        });
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
