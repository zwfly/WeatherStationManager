package com.yurunsd.weatherstationmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;
import com.litesuits.common.utils.TelephoneUtil;
import com.yurunsd.weatherstationmanager.base.BaseActivity;
import com.yurunsd.weatherstationmanager.utils.HttpUtils;
import com.yurunsd.weatherstationmanager.utils.SPUtils;
import com.yurunsd.weatherstationmanager.utils.ToastUtils;

import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;
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
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        ButterKnife.bind(this);

        title_init();

//        login_pro();
    }

    private void title_init() {
        tvStartupInfo1.setText("");
        tvStartupInfo2.setText("");
    }

    private void login_pro() {
        String s = (String) SPUtils.get(StartupActivity.this, "LoginPhoneNumber", "");
        HttpUtils httpUtils = new HttpUtils();
        Map<String, Object> map = new HashMap<>();

        if (ObjectUtils.notEqual(s, "")) {
            map.put("LoginPhoneNumber", s);
        }
        s = (String) SPUtils.get(StartupActivity.this, "LoginPassword", "");
        if (ObjectUtils.notEqual(s, "")) {
            map.put("LoginPassword", s);
        }
//        s = TelephoneUtil.getIMEI(StartupActivity.this);
        s = "iiimei";
        s = ObjectUtils.defaultIfNull(s, "");
        if (ObjectUtils.notEqual(s, "")) {
            map.put("IMEI", s);
        }

        httpUtils.post(UserLogin_URL, map, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(Response response) {

                System.out.println("Set-Cookie1: " + response.header("Set-Cookie"));

                System.out.println("Set-Cookie2: " + response.header("cookie"));

            }

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
            }
        });

        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url, cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url);
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .build();
    }

    @OnClick(R.id.iv_startup_pic)
    public void onViewClicked() {
        ToastUtils.showShort(StartupActivity.this, "正在登陆...");

//        System.out.println(TelephoneUtil.printTelephoneInfo(StartupActivity.this));

        login_pro();
    }
}
