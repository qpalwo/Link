package com.example.xyx.link.Bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

public class UserRelation extends BmobObject {

    public Group getGroup() {
        return mGroup;
    }

    public void setGroup(Group group) {
        mGroup = group;
    }

    private Group mGroup;

    private User mUser;

    private List<Attribute> userAttr;

    private String name;

    private String relationId;

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public List<Attribute> getUserAttr() {
        return userAttr;
    }

    public void setUserAttr(List<Attribute> userAttr) {
        this.userAttr = userAttr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

}
