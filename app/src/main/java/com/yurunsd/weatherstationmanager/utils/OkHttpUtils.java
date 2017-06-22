package com.yurunsd.weatherstationmanager.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * Created by admin on 2017/6/22.
 */

public class OkHttpUtils {

    public static String TAG = "debug-okhttp";
    public static boolean isDebug = true;

    private OkHttpClient client;
    // 超时时间
    public static final int TIMEOUT = 10;

    //json请求
    public static final MediaType JSON = MediaType
            .parse("application/json; charset=utf-8");

    private Handler handler = new Handler(Looper.getMainLooper());

    public OkHttpUtils() {
        this.init();
    }

    private void init() {
        client = new OkHttpClient();
        // 设置超时时间
        client.newBuilder().connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();

    }



}
