package com.example.xyx.link;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xyx.link.Bean.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 陈钊燚 on 2018/6/2.
 * QQ 1215638092
 * Github FourfireChen
 */
public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.usrname)
    TextInputEditText mUsrname;
    @BindView(R.id.password)
    TextInputEditText mPassword;
    @BindView(R.id.login)
    Button mLogin;
    @BindView(R.id.new_user)
    TextView mNewUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.login, R.id.new_user})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                String username = mUsrname.getText().toString();
                String password = mPassword.getText().toString();
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.login(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if(e == null){
                            finish();
                        }else {
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
            case R.id.new_user:
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivityForResult(intent, 20);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 20){
            if (data != null) {
                String phonenumber = data.getExtras().getString("username");
                String password = data.getExtras().getString("password");
                mUsrname.setText(phonenumber);
                mPassword.setText(password);
            }
        }
    }
}
