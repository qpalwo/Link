package com.example.xyx.link.Bean;

import java.util.ArrayList;

public class DataBean extends ArrayList<DataBean.Hor> {

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    int type;

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    String relationName;

    public static class Hor extends ArrayList<User> {

    }
}
