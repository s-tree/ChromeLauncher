package com.jingxi.chrome.plugin.utils;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

    public static final String baseDir = "/sdcard/jxchrome";
    public static final String baseUrlFile = "/sdcard/jxchrome/baseurl";
    public static final String defaultUrl = "http://106.13.190.88/main/test_gallery1.html";

    public static String readBaseUrl(){
        File file = new File(baseUrlFile);
        if(!file.exists()){
            return defaultUrl;
        }
        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();
        try{
            inputStream = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int k = -1;
            while ((k = inputStream.read(bytes)) != -1){
                builder.append(new String(bytes,0,k));
            }
        }catch (Exception e){
            e.printStackTrace();
            return defaultUrl;
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }


    public static void updateBaseUrl(String baseUrl){
        if(TextUtils.isEmpty(baseUrl)){
            return;
        }
        File dir = new File(baseDir);
        if(!dir.exists()){
            dir.mkdirs();
        }
        baseUrl = baseUrl.trim();
        File file = new File(baseUrlFile);
        if(!file.exists()){
            try {
                file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        OutputStream outputStream = null;
        try{
            outputStream = new FileOutputStream(file);
            outputStream.write(baseUrl.getBytes());
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
