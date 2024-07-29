package com.jingxi.chrome.plugin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jingxi.chrome.plugin.fragments.ChangeUrlFragment;
import com.jingxi.chrome.plugin.fragments.CheckManagerFragment;
import com.jingxi.chrome.plugin.fragments.ManagerDetailFragment;
import com.jingxi.chrome.plugin.fragments.ResetManagerFragment;

public class ManagerActivity extends AppCompatActivity implements View.OnClickListener{
    public static ManagerActivity managerActivity;
    CheckManagerFragment checkFragment;
    ManagerDetailFragment detailFragment;
    ResetManagerFragment resetManagerFragment;
    ChangeUrlFragment changeUrlFragment;

    private Fragment lastFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        managerActivity = this;

        setContentView(R.layout.activity_main);
        findViewById(R.id.back).setOnClickListener(this);
        showManagerCheck();
    }

    public void showManagerCheck(){
        if(checkFragment == null){
            checkFragment = new CheckManagerFragment();
        }
        showFragment(checkFragment);
    }

    public void showManagerDetail(){
        if(detailFragment == null){
            detailFragment = new ManagerDetailFragment();
        }
        showFragment(detailFragment);
    }

    public void showManagerChange(){
        if(resetManagerFragment == null){
            resetManagerFragment = new ResetManagerFragment();
        }
        showFragment(resetManagerFragment);
    }

    public void showManagerUrl(){
        if(changeUrlFragment == null){
            changeUrlFragment = new ChangeUrlFragment();
        }
        showFragment(changeUrlFragment);
    }

    private void showFragment(Fragment fragment){
        if(lastFragment == fragment){
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.framelayout,fragment);
        if(lastFragment != null){
            transaction.remove(lastFragment);
        }
        transaction.commitAllowingStateLoss();
        lastFragment = fragment;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        managerActivity = null;
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onBackPressed() {
        if(lastFragment == changeUrlFragment
                || lastFragment == resetManagerFragment){
            showFragment(detailFragment);
        }
        else{
            finish();
        }
    }
}
