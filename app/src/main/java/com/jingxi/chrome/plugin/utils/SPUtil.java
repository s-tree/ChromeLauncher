package com.jingxi.chrome.plugin.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.jingxi.chrome.plugin.MainApplication;

public class SPUtil {

    public static SharedPreferences instance = MainApplication.application.getSharedPreferences("app", Context.MODE_PRIVATE);

    /**
     * 管理员密码
     */
    public static final String KEY_MANAGER_PASS = "KEY_MANAGER_PASS";
    public static final String VALUE_DEFAULT_MANAGER = "000000";

    public static final String KEY_MANAGER_HIDE_MENU = "KEY_MANAGER_HIDE_MENU";

    public static boolean isManagerPass(String pass){
        return TextUtils.equals(instance.getString(KEY_MANAGER_PASS,VALUE_DEFAULT_MANAGER),pass);
    }

    public static void setManagerPass(String pass){
        if(TextUtils.isEmpty(pass) || pass.length() != 6){
            return;
        }
        instance.edit().putString(KEY_MANAGER_PASS,pass).apply();
    }

    public static boolean isShowHide(){
        return instance.getBoolean(KEY_MANAGER_HIDE_MENU,true);
    }

    public static void setShowHide(boolean isShow){
        instance.edit().putBoolean(KEY_MANAGER_HIDE_MENU,isShow).apply();
    }
}
