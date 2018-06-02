package com.example.xyx.link.Bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

public class Group extends BmobObject{

    private List<UserRelation> mUserRelations;

    private int groupNumber;

    private String name;

    public List<UserRelation> getUserRelations() {
        return mUserRelations;
    }

    public void setUserRelations(List<UserRelation> userRelations) {
        mUserRelations = userRelations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }


}
