package com.yurunsd.weatherstationmanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.litesuits.common.utils.TelephoneUtil;
import com.yurunsd.weatherstationmanager.base.BaseActivity;
import com.yurunsd.weatherstationmanager.login.LoginActivity;
import com.yurunsd.weatherstationmanager.login.store.CookieJarImpl;
import com.yurunsd.weatherstationmanager.login.store.PersistentCookieStore;
import com.yurunsd.weatherstationmanager.utils.HttpUtils;
import com.yurunsd.weatherstationmanager.utils.SPUtils;
import com.yurunsd.weatherstationmanager.utils.ToastUtils;

import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.CookieHandler;
import java.net.HttpCookie;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.yurunsd.weatherstationmanager.utils.GlobalConstants.UserLogin_URL;

public class StartupActivity extends BaseActivity {

    @Bind(R.id.iv_startup_pic)
    ImageView ivStartupPic;
    @Bind(R.id.tv_startup_info1)
    TextView tvStartupInfo1;
    @Bind(R.id.tv_startup_info2)
    TextView tvStartupInfo2;

    Handler mhandler = new Handler(Looper.getMainLooper());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            /*set it to be no title*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*set it to be full screen*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_startup);
        ButterKnife.bind(this);

        title_init();


        new Thread(r).start();

    }

    private void title_init() {
        tvStartupInfo1.setText("");
        tvStartupInfo2.setText("");
    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            login_pro();
//            mhandler.postDelayed(r, 1000);

        }
    };
    private PersistentCookieStore persistentCookieStore;

    private void login_pro() {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client;
        RequestBody body = new FormBody.Builder().build();
        persistentCookieStore = new PersistentCookieStore(getApplicationContext());
        CookieJarImpl cookieJarImpl = new CookieJarImpl(persistentCookieStore);

        Request request = new Request.Builder().post(body).url(UserLogin_URL).build();

        client = new OkHttpClient().newBuilder().cookieJar(cookieJarImpl).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                mhandler.post(new Runnable() {
                    public void run() {
                        ToastUtils.showShort(StartupActivity.this, "服务器超时");
                        Intent intent = new Intent(StartupActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
//                finish();

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
                            ToastUtils.showLong(StartupActivity.this, msg);
                        } else {
                            ToastUtils.showLong(StartupActivity.this, "数据解析错误");
                        }
                    }
                });
                if (StringUtils.equals(isSuccess, "y")) {
                    Intent intent = new Intent(StartupActivity.this, MainActivity.class);
                    startActivity(intent);

                    finish();
                } else if (StringUtils.equals(isSuccess, "n")) {
                    Intent intent = new Intent(StartupActivity.this, LoginActivity.class);
                    startActivity(intent);

                    finish();
                }


            }
        });


    }

    @OnClick(R.id.iv_startup_pic)
    public void onViewClicked() {
        ToastUtils.showShort(StartupActivity.this, "正在登陆...");


    }
}
