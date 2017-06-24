package com.yurunsd.weatherstationmanager.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yurunsd.weatherstationmanager.MainActivity;
import com.yurunsd.weatherstationmanager.R;
import com.yurunsd.weatherstationmanager.StartupActivity;
import com.yurunsd.weatherstationmanager.base.BaseActivity;
import com.yurunsd.weatherstationmanager.login.store.CookieJarImpl;
import com.yurunsd.weatherstationmanager.login.store.PersistentCookieStore;
import com.yurunsd.weatherstationmanager.utils.ToastUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.yurunsd.weatherstationmanager.utils.GlobalConstants.UserLogin_URL;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.et_loginPhoneNumber)
    EditText etLoginPhoneNumber;
    @Bind(R.id.et_LoginPassword)
    EditText etLoginPassword;
    @Bind(R.id.cb_rememberPassword)
    CheckBox cbRememberPassword;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.tv_register)
    TextView tvRegister;


    Handler mhandler = new Handler(Looper.getMainLooper());
    private PersistentCookieStore persistentCookieStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        param_init();
        title_init();
    }

    private void title_init() {


    }

    private void param_init() {

        persistentCookieStore = new PersistentCookieStore(getApplicationContext());
        List<Cookie> cookieList = persistentCookieStore.getCookies();

        for (int i = 0; i < cookieList.size(); i++) {
            Cookie cookie = cookieList.get(i);

            if (StringUtils.equals(cookie.name(), "name")) {
                etLoginPhoneNumber.setText(cookie.value());
            } else {
                etLoginPhoneNumber.setText("");
            }
            if (StringUtils.equals(cookie.name(), "pwd")) {
                etLoginPassword.setText(cookie.value());
            } else {
                etLoginPassword.setText("");
            }
        }

    }

    @OnClick({R.id.cb_rememberPassword, R.id.btn_login, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cb_rememberPassword:
                break;
            case R.id.btn_login:
                persistentCookieStore.removeAll();
                login_pro(etLoginPhoneNumber.getText().toString(), etLoginPassword.getText().toString()
                        , cbRememberPassword.isChecked());
                break;
            case R.id.tv_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }


    private void login_pro(String name, String pwd, boolean isSave) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        System.out.println(name + ", " + pwd + ", " + isSave);

        OkHttpClient client;
        RequestBody body = new FormBody.Builder()
                .add("name", name)
                .add("pwd", pwd)
                .add("isSave", isSave ? "y" : "n")
                .build();

        CookieJarImpl cookieJarImpl = new CookieJarImpl(persistentCookieStore);

        Request request = new Request.Builder().post(body).url(UserLogin_URL).build();

        client = new OkHttpClient().newBuilder().cookieJar(cookieJarImpl).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                mhandler.post(new Runnable() {
                    public void run() {
                        ToastUtils.showShort(LoginActivity.this, "服务器超时");

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String bodystring = response.body().string();
                System.out.println("body " + bodystring);
                Type type = new TypeToken<Map<String, String>>() {
                }.getType();
                final Map<String, String> map = new Gson().fromJson(bodystring, type);

                String isSuccess = map.get("isSuccess");
                mhandler.post(new Runnable() {
                    public void run() {
                        String msg = map.get("msg");
                        if (!StringUtils.equals(msg, null)) {
                            ToastUtils.showLong(LoginActivity.this, msg);
                        } else {
                            ToastUtils.showLong(LoginActivity.this, "数据解析错误");
                        }
                    }
                });
                if (StringUtils.equals(isSuccess, "y")) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                    finish();
                } else if (StringUtils.equals(isSuccess, "n")) {


                }


            }
        });


    }
}
