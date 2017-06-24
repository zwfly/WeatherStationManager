package com.yurunsd.weatherstationmanager.login;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yurunsd.weatherstationmanager.MainActivity;
import com.yurunsd.weatherstationmanager.R;
import com.yurunsd.weatherstationmanager.base.BaseActivity;
import com.yurunsd.weatherstationmanager.login.store.CookieJarImpl;
import com.yurunsd.weatherstationmanager.utils.HttpUtils;
import com.yurunsd.weatherstationmanager.utils.ToastUtils;
import com.yurunsd.weatherstationmanager.utils.Utils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.yurunsd.weatherstationmanager.utils.GlobalConstants.UserLogin_URL;
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


    Handler mhandler = new Handler(Looper.getMainLooper());


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
        RequestBody body = new FormBody.Builder()
                .add("nickName", etRegisterUsername.getText().toString())
                .add("name", etRegisterphoneNum.getText().toString())
                .add("pwd", etRegisterConfirmPassword.getText().toString())
                .build();

        Request request = new Request.Builder().post(body).url(UserRegister_URL).build();

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                mhandler.post(new Runnable() {
                    public void run() {
                        ToastUtils.showShort(RegisterActivity.this, "服务器超时");

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                String bodystring = response.body().string();
                System.out.println("body " + bodystring);
                Type type = new TypeToken<Map<String, String>>() {
                }.getType();
                Map<String, String> map = null;
                try {
                    map = new Gson().fromJson(bodystring, type);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String isSuccess;
                if (map != null) {
                    isSuccess = map.get("isSuccess");
                } else {
                    isSuccess = "n";
                }
                final Map<String, String> finalMap = map;
                mhandler.post(new Runnable() {
                    public void run() {
                        String msg = finalMap.get("msg");
                        if (!StringUtils.equals(msg, null)) {
                            ToastUtils.showLong(RegisterActivity.this, msg);
                        } else {
                            ToastUtils.showLong(RegisterActivity.this, "数据解析错误");
                        }
                    }
                });
                if (StringUtils.equals(isSuccess, "y")) {

                    finish();

                } else if (StringUtils.equals(isSuccess, "n")) {


                }


            }
        });


    }


}
