package com.beijing.hekl.hekl.bean;

import com.socks.library.KLog;

/**
 * Created by Administrator on 2015/11/23.
 * App类
 */
public class AppInfo {

    public String appName = "";
    public String packageName = "";
    public String versionName = "";
    public int versionCode = 0;

    //    public Drawable appIcon=null;
    public void print() {
        KLog.v("app", "Name:" + appName + " Package:" + packageName);
        KLog.v("app", "Name:" + appName + " versionName:" + versionName);
        KLog.v("app", "Name:" + appName + " versionCode:" + versionCode);
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "appName='" + appName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", versionName='" + versionName + '\'' +
                ", versionCode=" + versionCode +
//                ", appIcon=" + appIcon +
                '}';
    }
}