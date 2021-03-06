package com.example.xyx.link.Bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

public class User extends BmobUser {

    private String name;

    private String ava;

    private String weChat;

    private String qq;

    private String phone;

    private BmobRelation mGroups;

    private int level;

    private String address;

    private int age;

    public User() {
        this.name = "username";
        this.weChat = "wechat";
        this.qq = "123456";
        this.phone = "15927157339";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAva() {
        return ava;
    }

    public void setAva(String ava) {
        this.ava = ava;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BmobRelation getGroups() {
        return mGroups;
    }

    public void setGroups(BmobRelation groups) {
        mGroups = groups;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
