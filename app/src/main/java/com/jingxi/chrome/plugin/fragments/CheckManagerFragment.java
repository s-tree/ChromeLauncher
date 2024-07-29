package com.jingxi.chrome.plugin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jingxi.chrome.plugin.ManagerActivity;
import com.jingxi.chrome.plugin.R;
import com.jingxi.chrome.plugin.utils.SPUtil;
import com.jingxi.chrome.plugin.utils.ToastUtil;
import com.jingxi.chrome.plugin.view.PhoneCode;

public class CheckManagerFragment extends Fragment implements View.OnClickListener{
    private PhoneCode checkPhoneCode;
    private TextView check;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_check,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkPhoneCode = (PhoneCode) view.findViewById(R.id.checkPhoneCode);
        check = (TextView) view.findViewById(R.id.check);
        check.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String data = checkPhoneCode.getPhoneCode();
        if(TextUtils.isEmpty(data) || data.length() < 6){
            ToastUtil.showMessage("请输入正确的密码");
            return;
        }
        if(!SPUtil.isManagerPass(data)){
            ToastUtil.showMessage("请输入正确的密码");
            return;
        }
        ManagerActivity.managerActivity.showManagerDetail();
        checkPhoneCode.showEmptyCode();
    }
}
