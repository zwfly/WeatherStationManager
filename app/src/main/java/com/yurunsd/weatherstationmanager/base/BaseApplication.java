package com.yurunsd.weatherstationmanager.base;

import android.app.Application;

import com.blankj.utilcode.util.Utils;


/**
 * Created by admin on 2017/6/22.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        Utils.init(BaseApplication.this);
    }
}
