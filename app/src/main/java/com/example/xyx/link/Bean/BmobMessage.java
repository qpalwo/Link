package com.example.xyx.link.Bean;

import cn.bmob.newim.bean.BmobIMExtraMessage;

public class BmobMessage extends BmobIMExtraMessage{
    @Override
    public String getMsgType() {
        return "Group";
    }

    @Override
    public boolean isTransient() {
        return true;
    }
}
