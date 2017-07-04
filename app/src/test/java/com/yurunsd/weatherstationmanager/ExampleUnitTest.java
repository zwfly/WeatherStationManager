package com.yurunsd.weatherstationmanager;

import com.yurunsd.weatherstationmanager.utils.HttpUtils;

import org.junit.Test;

import java.io.IOException;

import okhttp3.Response;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void exp1(){


        HttpUtils httpUtils = new HttpUtils();

        httpUtils.get("www.baidu.com", null, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(Response response) {
                try {
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}