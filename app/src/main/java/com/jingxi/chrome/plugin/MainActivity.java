package com.jingxi.chrome.plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jingxi.chrome.plugin.utils.FileUtils;
import com.jingxi.chrome.plugin.utils.PackageUtil;
import com.jingxi.chrome.plugin.utils.SPUtil;
import com.jingxi.chrome.plugin.view.NoVoiceView;

import java.lang.reflect.Field;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    WindowManager mWindowManager;
    private static final String chrome_package = "com.android.chrome";
    private int screenWidth;

    private static final int DISS = 0;
    private static final int SHOW = 1;

    private boolean isShowHideMenu = false;

    private final Handler handler = new Handler(){
        private int lastStatus = DISS;

        @Override
        public void handleMessage(Message msg) {
            if(lastStatus == msg.what){
                return;
            }
            if(msg.what == 0){
                hideView();
            }
            else{
                if(!isShowHideMenu){
                    return;
                }
                showHideView();
            }
            lastStatus = msg.what;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        mWindowManager.getDefaultDisplay().getSize(point);
        screenWidth = point.x;
        isShowHideMenu = SPUtil.isShowHide();

        registerReceiver();
        new Thread(){

            @Override
            public void run(){
                while (true){
                    String packageName = PackageUtil.getForegroundActivityName(MainActivity.this);
                    Log.w("test_bug","foreActivity = " + packageName + " isShowHideMenu = " + isShowHideMenu);
                    if(TextUtils.equals(packageName,chrome_package)){
                        if(isShowHideMenu){
                            handler.sendEmptyMessage(SHOW);
                        }
                    }
                    else{
                        handler.sendEmptyMessage(DISS);
                    }
                    SystemClock.sleep(200);
                }
            }
        }.start();

        setVolumeControlStream(AudioManager.RINGER_MODE_SILENT);
    }

    View menuView = null;
    View hideCloseView = null;
    private void showHideView() {
//        View urlInputHide = new View(this);
//        urlInputHide.setBackgroundColor(Color.GREEN);
//        urlInputHide.setOnClickListener(emptyClick);
//        mWindowManager.addView(urlInputHide,createParams(1050,60,100,0));

//        View newTab = new View(this);
//        newTab.setBackgroundColor(Color.BLUE);
//        newTab.setOnClickListener(emptyClick);
//        mWindowManager.addView(newTab,createParams(30,50,270,0));

        if(menuView == null){
            menuView = new NoVoiceView(this);
            menuView.setBackgroundColor(Color.TRANSPARENT);
            menuView.setOnClickListener(emptyClick);
        }
        if(menuView.getParent() == null){
            mWindowManager.addView(menuView, createParams(70, 100, screenWidth - 70, 0));
        }

        if(hideCloseView == null){
            hideCloseView = new NoVoiceView(this);
            hideCloseView.setBackgroundColor(Color.TRANSPARENT);
            hideCloseView.setOnClickListener(emptyClick);
        }
        if(hideCloseView.getParent() == null){
            mWindowManager.addView(hideCloseView, createParams(70, 100, 0, 0));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterReceiver();
    }

    private void hideView(){
        if(menuView == null){
            return;
        }
        try {
            mWindowManager.removeView(menuView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mWindowManager.removeView(hideCloseView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        launcherChrome();
    }

    private void launcherChrome() {
        String url = FileUtils.readBaseUrl();
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setShowTitle(true);
        if(isShowHideMenu){
            builder.setCloseButtonIcon(getTransBitmap());
        }
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));

        if(isShowHideMenu){
            showHideView();
        }
    }

    private void disEnableUrlBar(CustomTabsIntent.Builder builder){
        try {
            Field mIntent = builder.getClass().getDeclaredField("mIntent");
            mIntent.setAccessible(true);
            Intent intent = (Intent) mIntent.get(builder);
            intent.putExtra("android.support.customtabs.extra.ENABLE_URLBAR_HIDING",false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setPayMent(CustomTabsIntent.Builder builder){
        try {
            Field mIntent = builder.getClass().getDeclaredField("mIntent");
            mIntent.setAccessible(true);
            Intent intent = (Intent) mIntent.get(builder);
            intent.putExtra("org.chromium.chrome.browser.customtabs.EXTRA_UI_TYPE",6);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Bitmap getTransBitmap() {
        return Bitmap.createBitmap(24,24, Bitmap.Config.ARGB_8888);
    }

    private void testOpenGallery() {
        Uri uri = Uri.parse(TextUtils.concat("jingxi://", "gallery", "/gallery").toString());
        Intent intent = isUriEnable(this, uri);
        if (intent == null) {
            Log.w("test_bug", "intent is null");
            return;
        }
        startActivity(intent);
    }

    public static Intent isUriEnable(Context context, Uri uri) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isValid = !activities.isEmpty();
        if (isValid) {
            return intent;
        }
        return null;
    }

    private WindowManager.LayoutParams createParams(int width, int height, int x, int y) {
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        //获取的是WindowManagerImpl.CompatModeWrapper
        //设置window type
//        wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        //设置悬浮窗口长宽数据
        wmParams.width = width;
        wmParams.height = height;
        wmParams.x = x;
        wmParams.y = y;

        return wmParams;
    }

    private View.OnClickListener emptyClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    public static String ACTION = "com.jingxi.chrome.manager";
    public static String KEY_SHOW = "isShow";

    String KEY_UPDATE_URL = "baseUrl";

    private void registerReceiver(){
        IntentFilter filter = new IntentFilter(ACTION);
        registerReceiver(receiver,filter);
    }

    private void unRegisterReceiver(){
        unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent == null || intent.getExtras() == null){
                return;
            }
            if(intent.getExtras().containsKey(KEY_SHOW)){
                isShowHideMenu = intent.getBooleanExtra(KEY_SHOW,false);
                handler.sendEmptyMessage(isShowHideMenu ? SHOW : DISS);
            }
            if(intent.getExtras().containsKey(KEY_UPDATE_URL)){
                String url = intent.getStringExtra(KEY_UPDATE_URL);
                FileUtils.updateBaseUrl(url);
            }
        }
    };
}
