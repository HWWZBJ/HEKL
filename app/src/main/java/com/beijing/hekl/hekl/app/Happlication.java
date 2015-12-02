package com.beijing.hekl.hekl.app;

import android.app.Application;

import com.socks.library.KLog;

/**
 * Created by HEKL on 15/12/1.
 * Used for
 */
public class Happlication extends Application {
    private static final String TAG = "Happlication";
    private static Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        KLog.init(true);
    }

    public static Application getApplication() {
        return mApplication;
    }
}
