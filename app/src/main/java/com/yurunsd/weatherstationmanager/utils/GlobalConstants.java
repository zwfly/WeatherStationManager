package com.yurunsd.weatherstationmanager.utils;

/**
 *
 * Created by admin on 2017/6/20.
 */

public class GlobalConstants {
    public static final String SERVER_URL = "http://1g7514058j.imwork.net:8090/yurunsd";
//    public static final String SERVER_URL = "http://192.168.1.10:8080/yurunsd";

    public static final String UserLogin_URL = SERVER_URL + "/user/login";
    public static final String UserRegister_URL = SERVER_URL + "/user/register";
    public static final String UserInfo_URL = SERVER_URL + "/user/info";
    public static final String UserDeviceAdd_URL = SERVER_URL + "/user/device/add";
    public static final String UserDeviceItem_URL = SERVER_URL + "/user/device/item";


    //    public static final String WeatherStationConfigQuery_URL = SERVER_URL + "/weatherstation/config/query";
    public static final String WeatherStationConfigUpdate_URL = SERVER_URL + "/weatherstation/config/update";


    public static final String WeatherStationRecordQuery_URL = SERVER_URL + "/weatherstation/record/query";
    public static final String WeatherStationRecordUpdate_URL = SERVER_URL + "/weatherstation/record/update";


}
