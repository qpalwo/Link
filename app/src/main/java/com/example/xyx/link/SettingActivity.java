package com.example.xyx.link;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xyx.link.Bean.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.name_edit)
    EditText mNameEdit;
    @BindView(R.id.wechat_edit)
    EditText mWechatEdit;
    @BindView(R.id.qq_edit)
    EditText mQqEdit;
    @BindView(R.id.phone_edit)
    EditText mPhoneEdit;
    @BindView(R.id.setting_bottom)
    Button mSettingBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.setting_bottom)
    public void onViewClicked() {
        User user = BmobUser.getCurrentUser(User.class);
        String name = mNameEdit.getText().toString();
        String wechat = mWechatEdit.getText().toString();
        String qq = mQqEdit.getText().toString();
        String phone = mPhoneEdit.getText().toString();
        if (name.isEmpty() || wechat.isEmpty() || qq.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "信息不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            user.setWeChat(wechat);
            user.setQq(qq);
            user.setPhone(phone);
            user.setName(name);
            user.update(user.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e != null) {
                        finish();
                    } else {
                        Toast.makeText(SettingActivity.this, "信息更改失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
