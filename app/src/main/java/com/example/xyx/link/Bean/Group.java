package com.example.xyx.link.Bean;

import cn.bmob.v3.BmobObject;

public class Group extends BmobObject {

    private int groupNumber;

    private String name;

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
