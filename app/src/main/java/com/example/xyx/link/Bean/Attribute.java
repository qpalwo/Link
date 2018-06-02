package com.example.xyx.link.Bean;

import cn.bmob.v3.BmobUser;

public class Attribute extends BmobUser{

    public Attribute(){

    }

    public Attribute(int type, int level){
        this.type = type;
        this.level = level;
    }

    private Integer type;

    private Integer level;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }





}
