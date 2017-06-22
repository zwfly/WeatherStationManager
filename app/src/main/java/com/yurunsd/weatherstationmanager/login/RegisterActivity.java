package com.yurunsd.weatherstationmanager.login;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yurunsd.weatherstationmanager.R;
import com.yurunsd.weatherstationmanager.base.BaseActivity;
import com.yurunsd.weatherstationmanager.utils.HttpUtils;
import com.yurunsd.weatherstationmanager.utils.ToastUtils;
import com.yurunsd.weatherstationmanager.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

import static com.yurunsd.weatherstationmanager.utils.GlobalConstants.UserRegister_URL;

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.iv_return)
    ImageView ivReturn;
    @Bind(R.id.iv_add)
    ImageView ivAdd;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.et_RegisterUsername)
    EditText etRegisterUsername;
    @Bind(R.id.et_RegisterphoneNum)
    EditText etRegisterphoneNum;
    @Bind(R.id.et_RegisterPassword)
    EditText etRegisterPassword;
    @Bind(R.id.et_RegisterConfirmPassword)
    EditText etRegisterConfirmPassword;
    @Bind(R.id.cb_clause)
    CheckBox cbClause;
    @Bind(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        title_init();

    }

    private void title_init() {

        ivAdd.setVisibility(View.INVISIBLE);

        tvTitle.setText("用户注册");

    }

    @OnClick({R.id.iv_return, R.id.iv_add, R.id.cb_clause, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.iv_add:
                break;
            case R.id.cb_clause:
                if (cbClause.isChecked()) {
                    btnSubmit.setEnabled(true);
                } else {
                    btnSubmit.setEnabled(false);
                }
                break;
            case R.id.btn_submit:
                submit();
                break;
        }
    }

    private ProgressDialog progressDialog = null;

    private void submit() {

        if (!etRegisterUsername.getText().toString().matches("^[A-Za-z][A-Za-z0-9_-]{3,19}")) {
            ToastUtils.showShort(this, "用户名格式不正确");
        } else if (!Utils.isPhoneNumber(etRegisterphoneNum.getText().toString())) {
            ToastUtils.showShort(this, "手机号不合法");
        } else if (!etRegisterPassword.getText().toString().matches("[A-Za-z0-9_-]{6,20}")) {
            ToastUtils.showShort(this, "密码格式不正确");
        } else if (!etRegisterConfirmPassword.getText().toString().equals(etRegisterPassword.getText().toString())) {
            ToastUtils.showShort(this, "密码不匹配");
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("确认注册？");
            builder.setPositiveButton("确认",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //setTitle("点击了对话框上的Button1")
                            progressDialog = ProgressDialog.show(RegisterActivity.this, "Loading...", "请稍等...", true, false);

                            send();
                        }
                    });
            builder.setNeutralButton("取消",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //setTitle("点击了对话框上的Button2");

                        }
                    });
    /*    builder.setNegativeButton("Button3",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        setTitle("点击了对话框上的Button3");
                    }
                });*/
            builder.create().show();


        }

    }

    private void send() {

        HttpUtils httpUtils = new HttpUtils();

        Map<String, Object> map = new HashMap<>();

        httpUtils.post(UserRegister_URL, map, new HttpUtils.HttpCallback() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(Response response) {




            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                ToastUtils.showShort(RegisterActivity.this, "服务器超时");
            }


        });


    }


}
