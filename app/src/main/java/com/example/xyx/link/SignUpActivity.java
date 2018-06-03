package com.example.xyx.link;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.xyx.link.Bean.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 陈钊燚 on 2018/6/2.
 * QQ 1215638092
 * Github FourfireChen
 */
public class SignUpActivity extends AppCompatActivity {
    @BindView(R.id.phonenumber)
    TextInputEditText mPhonenumber;
    @BindView(R.id.sign_password)
    TextInputEditText mSignPassword;
    @BindView(R.id.confirm_password)
    TextInputEditText mConfirmPassword;
    @BindView(R.id.verify_code)
    TextInputEditText mVerifyCode;
    @BindView(R.id.get_verifycode)
    Button mGetVerifycode;
    @BindView(R.id.sign)
    Button mSign;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.get_verifycode, R.id.sign})
    public void onViewClicked(View view) {
        String phonenumber = mPhonenumber.getText().toString();
        String password = mSignPassword.getText().toString();
        String confirmPassword = mConfirmPassword.getText().toString();
        String verifycode = mVerifyCode.getText().toString();
        switch (view.getId()) {
            case R.id.get_verifycode:
                if (!phonenumber.isEmpty()) {
                    BmobSMS.requestSMSCode(phonenumber, "11111", new QueryListener<Integer>() {
                        @Override
                        public void done(Integer integer, BmobException e) {
                            if (e == null) {
                                Toast.makeText(SignUpActivity.this, "发送成功", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(SignUpActivity.this, "发送失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;
            case R.id.sign:
                if (phonenumber.isEmpty() && verifycode.isEmpty() && password.isEmpty() && confirmPassword.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "信息不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    BmobSMS.verifySmsCode(phonenumber, verifycode, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                User user = new User();
                                user.setUsername(phonenumber);
                                user.setPassword(password);
                                user.signUp(new SaveListener<User>() {
                                    @Override
                                    public void done(User user, BmobException e) {
                                        if (e == null) {
                                            Intent intent = new Intent();
                                            Bundle bundle = new Bundle();
                                            bundle.putString("username", phonenumber);
                                            bundle.putString("password", password);
                                            intent.putExtras(bundle);
                                            SignUpActivity.this.setResult(20, intent);
                                            finish();
                                        } else {
                                            Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;
        }
    }
}
