package com.yurunsd.weatherstationmanager;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.yurunsd.weatherstationmanager.utils.HttpUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import okhttp3.Response;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {



        HttpUtils httpUtils = new HttpUtils();
        System.out.println("http start");
        httpUtils.get("http://www.baidu.com", null, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(Response response) {
                try {
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("http onSuccess");
            }
        });

    }
}
