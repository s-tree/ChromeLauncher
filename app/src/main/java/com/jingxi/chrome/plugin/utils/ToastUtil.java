package com.jingxi.chrome.plugin.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.jingxi.chrome.plugin.MainApplication;

public class ToastUtil {

    private static Toast instance ;

    public static synchronized void showMessage(String message){
        if(TextUtils.isEmpty(message)){
            return;
        }
        if(instance == null){
            instance = Toast.makeText(MainApplication.application,message,Toast.LENGTH_SHORT);
        }
        instance.setText(message);
        instance.show();
    }
}
