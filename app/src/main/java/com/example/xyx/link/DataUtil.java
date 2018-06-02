package com.example.xyx.link;

import android.content.Context;
import android.util.ArrayMap;

import com.example.xyx.link.Bean.Attribute;
import com.example.xyx.link.Bean.DataBean;
import com.example.xyx.link.Bean.Group;
import com.example.xyx.link.Bean.UserRelation;

import java.util.List;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class DataUtil {

    private Context mContext;

    public DataUtil(Context context) {
        mContext = context;
    }

    public void getGroup() {
        BmobUser.getCurrentUser();
    }

    public void newGroup(String name, String relationName) {

    }

    public DataBean getOriginData(Group group) {
        List<UserRelation> relations = group.getUserRelations();
        DataBean dataBean = new DataBean();
        int level = 0;
        Map<Integer, DataBean.Hor> user = new ArrayMap<>();
        for (UserRelation userRelation : relations) {
            List<Attribute> attributes = userRelation.getUserAttr();
            for (Attribute attribute : attributes) {
                if (attribute.getType() == 0) {
                    if (user.get(attribute.getLevel()) != null) {
                        user.get(attribute.getLevel()).add(userRelation.getUser());
                    } else {
                        DataBean.Hor temp = new DataBean.Hor();
                        temp.add(userRelation.getUser());
                        user.put(attribute.getLevel(), temp);
                        level++;
                    }
                    break;
                }
            }
        }
        for (int i = 0; i < level; i++){
            dataBean.add(user.get(i));
        }
        return dataBean;
    }


}
