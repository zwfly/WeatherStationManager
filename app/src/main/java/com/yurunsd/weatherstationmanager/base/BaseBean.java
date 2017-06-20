package com.yurunsd.weatherstationmanager.base;

import java.io.Serializable;

/**
 * Created by admin on 2017/6/20.
 */

public class BaseBean implements Serializable {

    private boolean success;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
