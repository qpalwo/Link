package com.example.xyx.link;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.Toast;

import com.example.xyx.link.Bean.Attribute;
import com.example.xyx.link.Bean.DataBean;
import com.example.xyx.link.Bean.Group;
import com.example.xyx.link.Bean.User;
import com.example.xyx.link.Bean.UserRelation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class DataUtil {

    private static final String TAG = "DataUtil";

    private Context mContext;

    public DataUtil(Context context) {
        mContext = context;
    }

    public void checkGroup(List<Group> groups) {
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<UserRelation> query = new BmobQuery<>();
        query.addWhereEqualTo("mUser", user);
        query.findObjects(new FindListener<UserRelation>() {
            @Override
            public void done(List<UserRelation> list, BmobException e) {

                for (UserRelation userRelation : list) {
                    for (Group group : groups) {
                        if (!userRelation.getGroup().getObjectId().equals(group.getObjectId())) {
                            BmobRelation bmobRelation = new BmobRelation();
                            bmobRelation.add(group);
                            user.setGroups(bmobRelation);
                        }
                    }
                }
            }
        });
    }

    public void getGroup(CallBack<List<Group>> callBack) {
        //List<Group> groups = BmobUser.getCurrentUser(User.class).getGroups();
        User temp = BmobUser.getCurrentUser(User.class);
        BmobRelation relation = BmobUser.getCurrentUser(User.class).getGroups();
        List<Group> groups = new ArrayList<>();
        BmobQuery<Group> queryG = new BmobQuery<>();
        queryG.addWhereRelatedTo("mGroups", new BmobPointer(temp));
        queryG.findObjects(new FindListener<Group>() {
            @Override
            public void done(List<Group> list, BmobException e) {
                if (e == null) {
                    groups.addAll(list);
                    checkGroup(groups);
                    callBack.onSuccess(groups);
                }
            }
        });

    }

    public void getGroupMemberNumber(Group group, CallBack<String> callBack) {
        getUserRelations(group, new CallBack<List<UserRelation>>() {
            @Override
            public void onSuccess(List<UserRelation> data) {
                callBack.onSuccess(data.size() + "");
            }

            @Override
            public void onFu(String msg) {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUserRelations(Group group, CallBack<List<UserRelation>> callBack) {
        BmobQuery<UserRelation> userRelationBmobQuery = new BmobQuery<>();
        userRelationBmobQuery.addWhereEqualTo("mGroup", new BmobPointer(group));
        userRelationBmobQuery.findObjects(new FindListener<UserRelation>() {
            @Override
            public void done(List<UserRelation> list, BmobException e) {
                if (e == null) {
                    callBack.onSuccess(list);
                } else {
                    callBack.onFu(e.getMessage());
                }

            }
        });
    }

    private void setUserRelations(Group group, UserRelation userRelation) {
        userRelation.setGroup(group);
        userRelation.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Log.d(TAG, "done: userRelation add successful");
                }
            }
        });
    }

    public void newGroupAddUser(Group group, User user, int level) {
        UserRelation userRelation = new UserRelation();

        List<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute(0, level));
        userRelation.setUser(user);
        userRelation.setUserAttr(attributes);
        setUserRelations(group, userRelation);
    }

    public void newGroup(String name, String relationName, CallBack<Group> callBack) {
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
        newGroup.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    BmobRelation bmobRelation = new BmobRelation();
                    bmobRelation.add(newGroup);
                    User user = new User();
                    user.setGroups(bmobRelation);
                    user.setName("aaabb");
                    user.update(BmobUser.getCurrentUser().getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                setUserRelations(newGroup, relation);
                                callBack.onSuccess(newGroup);
                            }
                            Log.d(TAG, "done: " + e.getMessage());
                        }
                    });
                } else {
                    Log.e(TAG, "done: " + e.getMessage());
                }
            }
        });
    }

    public void joinRelation(Group group, DataBean dataBean, int level) {
        User user = BmobUser.getCurrentUser(User.class);
        getUserRelations(group, new CallBack<List<UserRelation>>() {
            @Override
            public void onSuccess(List<UserRelation> data) {
                UserRelation targetRelation = null;
                for (UserRelation userRelation : data) {
                    if (userRelation.getUser().getObjectId().equals(user.getObjectId())) {
                        targetRelation = userRelation;
                    }
                }
                if (targetRelation != null) {
                    targetRelation.getUserAttr().add(new Attribute(dataBean.getType(), level));
                    targetRelation.update(targetRelation.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Log.d(TAG, "done: relation add success");
                            }
                        }
                    });
                }
            }

            @Override
            public void onFu(String msg) {

            }
        });
    }

    public void quitGroup(Group group) {
        User user = BmobUser.getCurrentUser(User.class);
    }

    public void quitLink(Group group, DataBean dataBean) {
    }


    /**
     * 新建關係
     *
     * @param group        當前羣組
     * @param user         目標用戶
     * @param relationName 關係名字
     * @param position     ==0, 同級； >0 , 目標用戶高於當前用戶； <0 , 目標用戶低於當前用戶
     */
    public void newRelation(Group group, User user, String relationName, int position) {
        getUserRelations(group, new CallBack<List<UserRelation>>() {
            int type = 0;
            UserRelation targetRelation = null;
            UserRelation nowRelation = null;
            User nowUser = BmobUser.getCurrentUser(User.class);

            @Override
            public void onSuccess(List<UserRelation> data) {
                for (UserRelation userRelation : data) {
                    List<Attribute> attributes = userRelation.getUserAttr();
                    for (Attribute attribute : attributes) {
                        if (attribute.getType() > type) {
                            type = attribute.getType();
                        }
                        if (userRelation.getUser().getObjectId().equals(user.getObjectId())) {
                            targetRelation = userRelation;
                        }
                        if (userRelation.getUser().getObjectId().equals(nowUser.getObjectId())) {
                            nowRelation = userRelation;
                        }
                    }
                }

                Attribute me = new Attribute();
                Attribute he = new Attribute();
                me.setType(type);
                he.setType(type);
                if (position == 0) {
                    me.setLevel(0);
                    he.setLevel(0);
                } else if (position > 0) {
                    he.setLevel(0);
                    me.setLevel(1);
                } else {
                    me.setLevel(0);
                    he.setLevel(1);
                }

                if (nowRelation != null && targetRelation != null) {
                    nowRelation.getUserAttr().add(me);
                    targetRelation.getUserAttr().add(he);
                }
            }

            @Override
            public void onFu(String msg) {

            }
        });

    }

    public void getAllRelation(Group group, User user, CallBack<List<DataBean>> callBack) {
        List<DataBean> dataBeans = new ArrayList<>();
        User nowUser = BmobUser.getCurrentUser(User.class);

        getUserRelations(group, new CallBack<List<UserRelation>>() {
            UserRelation target = null;
            UserRelation now = null;

            @Override
            public void onSuccess(List<UserRelation> data) {
                for (UserRelation userRelation : data) {
                    if (userRelation.getUser().getObjectId().equals(user.getObjectId())) {
                        target = userRelation;
                    } else if (userRelation.getUser().getObjectId().equals(nowUser.getObjectId())) {
                        now = userRelation;
                    }
                }
                if (target != null && now != null) {
                    List<Attribute> targetUserAttribute = target.getUserAttr();
                    List<Attribute> nowUserAttribute = now.getUserAttr();

                    for (Attribute i : targetUserAttribute) {
                        for (Attribute j : nowUserAttribute) {
                            if (i.getType().equals(j.getType())) {
                                dataBeans.add(getDataBean(i.getType(), data));
                            }
                        }
                    }
                } else {
                    callBack.onFu("get fault");
                }
                callBack.onSuccess(dataBeans);

            }

            @Override
            public void onFu(String msg) {

            }
        });

    }

    public void getOriginData(Group group, CallBack<DataBean> callBack) {
        getUserRelations(group, new CallBack<List<UserRelation>>() {
            @Override
            public void onSuccess(List<UserRelation> data) {
                callBack.onSuccess(getDataBean(0, data));
            }

            @Override
            public void onFu(String msg) {

            }
        });
    }

    private DataBean getDataBean(int type, List<UserRelation> userRelations) {
        DataBean dataBean = new DataBean();
        int level = 0;
        Map<Integer, DataBean.Hor> user = new ArrayMap<>();
        for (UserRelation userRelation : userRelations) {
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
