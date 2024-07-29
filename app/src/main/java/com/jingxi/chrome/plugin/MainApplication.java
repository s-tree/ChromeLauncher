package com.jingxi.chrome.plugin;

import android.app.Application;

public class MainApplication extends Application {
    public static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }
}
