package com.example.xyx.link;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;

import com.example.xyx.link.Bean.Attribute;
import com.example.xyx.link.Bean.DataBean;
import com.example.xyx.link.Bean.Group;
import com.example.xyx.link.Bean.User;
import com.example.xyx.link.Bean.UserRelation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class DataUtil {

    private static final String TAG = "DataUtil";

    private Context mContext;

    public DataUtil(Context context) {
        mContext = context;
    }

    public void getGroup(CallBack<List<Group>> callBack) {
        List<Group> groups = BmobUser.getCurrentUser(User.class).getGroups();
        if (groups != null) {
            callBack.onSuccess(groups);
        } else {
            callBack.onFu("獲取group失敗");
        }
    }

    public void newGroupAddUser(Group group, User user, int level) {
        UserRelation userRelation = new UserRelation();

        List<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute(0, level));

        userRelation.setUser(user);
        userRelation.setUserAttr(attributes);

        group.getUserRelations().add(userRelation);
        group.save();

    }

    public void newGroup(String name, String relationName) {
        Group newGroup = new Group();
        List<UserRelation> relations = new ArrayList<>();
        UserRelation relation = new UserRelation();
        List<Attribute> attributes = new ArrayList<>();

        Attribute attribute = new Attribute();
        attribute.setType(0);
        attribute.setLevel(0);
        attributes.add(attribute);

        relation.setName(relationName);
        relation.setUser(BmobUser.getCurrentUser(User.class));
        relation.setUserAttr(attributes);
        relations.add(relation);
        newGroup.setName(name);
        newGroup.setUserRelations(relations);

        List<Group> groups = BmobUser.getCurrentUser(User.class).getGroups();
        if (groups == null) {
            groups = new ArrayList<Group>();
        }
        groups.add(newGroup);
        User user = new User();
        user.setGroups(groups);
        user.update(BmobUser.getCurrentUser().getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                Log.d(TAG, "done: " + e.getMessage());
            }
        });

        newGroup.save();
    }

    public void joinRelation(DataBean dataBean, int level){

    }

    public void newRelation(Group group, User user, String relationName){

    }

    public List<DataBean> getAllRelation(Group group, User user) {
        List<DataBean> dataBeans = new ArrayList<>();
        User nowUser = BmobUser.getCurrentUser(User.class);
        List<UserRelation> userRelations = group.getUserRelations();
        UserRelation target = null;
        UserRelation now = null;
        for (UserRelation userRelation : userRelations) {
            if (userRelation.getUser().getObjectId().equals(user.getObjectId())) {
                target = userRelation;
            } else if (userRelation.getUser().getObjectId().equals(nowUser.getObjectId())){
                now = userRelation;
            }
        }
        if (target != null && now != null) {
            List<Attribute> targetUserAttribute = target.getUserAttr();
            List<Attribute> nowUserAttribute = now.getUserAttr();

            for (Attribute i : targetUserAttribute){
                for (Attribute j : nowUserAttribute){
                    if (i.getType().equals(j.getType())){
                        dataBeans.add(getDataBean(i.getType(), group));
                    }
                }
            }
        } else {
            return null;
        }

        return dataBeans;
    }

    public DataBean getOriginData(Group group) {
        return getDataBean(0, group);
//        List<UserRelation> relations = group.getUserRelations();
//        DataBean dataBean = new DataBean();
//        int level = 0;
//        Map<Integer, DataBean.Hor> user = new ArrayMap<>();
//        for (UserRelation userRelation : relations) {
//            List<Attribute> attributes = userRelation.getUserAttr();
//            for (Attribute attribute : attributes) {
//                if (attribute.getType() == 0) {
//                    if (user.get(attribute.getLevel()) != null) {
//                        user.get(attribute.getLevel()).add(userRelation.getUser());
//                    } else {
//                        DataBean.Hor temp = new DataBean.Hor();
//                        temp.add(userRelation.getUser());
//                        user.put(attribute.getLevel(), temp);
//                        level++;
//                    }
//                    break;
//                }
//            }
//        }
//        for (int i = 0; i < level; i++) {
//            dataBean.add(user.get(i));
//        }
//        return dataBean;
    }

    private DataBean getDataBean(int type, Group group){
        List<UserRelation> relations = group.getUserRelations();
        DataBean dataBean = new DataBean();
        int level = 0;
        Map<Integer, DataBean.Hor> user = new ArrayMap<>();
        for (UserRelation userRelation : relations) {
            List<Attribute> attributes = userRelation.getUserAttr();
            for (Attribute attribute : attributes) {
                if (attribute.getType() == type) {
                    if (user.get(attribute.getLevel()) != null) {
                        user.get(attribute.getLevel()).add(userRelation.getUser());
                        dataBean.setRelationName(userRelation.getName());
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
        for (int i = 0; i < level; i++) {
            dataBean.add(user.get(i));
        }
        dataBean.setType(type);
        return dataBean;
    }


}
