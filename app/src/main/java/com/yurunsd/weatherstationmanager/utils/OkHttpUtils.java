package com.yurunsd.weatherstationmanager.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.yurunsd.weatherstationmanager.login.store.CookieJarImpl;
import com.yurunsd.weatherstationmanager.login.store.PersistentCookieStore;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.yurunsd.weatherstationmanager.utils.GlobalConstants.UserLogin_URL;

/**
 * Created by admin on 2017/6/22.
 */

public class OkHttpUtils {

    public static String TAG = "debug-okhttp";
    public static boolean isDebug = true;

    private OkHttpClient client;
    // 超时时间
    public static final int TIMEOUT = 5;

    //json请求
    public static final MediaType JSON = MediaType
            .parse("application/json; charset=utf-8");

    private PersistentCookieStore persistentCookieStore;

    private Handler handler = new Handler(Looper.getMainLooper());

    public OkHttpUtils(Context context) {
        this.init(context);
    }

    private void init(Context context) {
        client = new OkHttpClient();
        // 设置超时时间
        client.newBuilder().connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
        persistentCookieStore = new PersistentCookieStore(context);
    }

    /**
     * post请求 map为body
     *
     * @param url
     * @param map
     * @param callback
     */
    public void post(String url, Map<String, Object> map, Callback callback) {
        FormBody.Builder builder = new FormBody.Builder();

        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
//                System.out.println("Key = " + entry.getKey() + ", Value = "
//                        + entry.getValue());
                builder.add(entry.getKey(), entry.getValue().toString());
            }
        }
        CookieJarImpl cookieJarImpl = new CookieJarImpl(persistentCookieStore);

        OkHttpClient client = new OkHttpClient().newBuilder().cookieJar(cookieJarImpl).build();

        Request request = new Request.Builder().post(builder.build()).url(UserLogin_URL).build();

        client.newCall(request).enqueue(callback);

    }


}
