package com.jingxi.chrome.plugin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jingxi.chrome.plugin.ManagerActivity;
import com.jingxi.chrome.plugin.R;
import com.jingxi.chrome.plugin.utils.FileUtils;
import com.jingxi.chrome.plugin.utils.ToastUtil;

public class ChangeUrlFragment extends Fragment implements View.OnClickListener{
    private EditText urlInput;
    private TextView check;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_url,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        urlInput = (EditText) view.findViewById(R.id.urlInput);
        urlInput.setText(FileUtils.readBaseUrl());
        check = (TextView) view.findViewById(R.id.check);
        check.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String data = urlInput.getText().toString();
        if(TextUtils.isEmpty(data)){
            ToastUtil.showMessage("请输入要设置的主页地址");
            return;
        }
        FileUtils.updateBaseUrl(data);
        ToastUtil.showMessage("主页设置成功");
        ManagerActivity.managerActivity.showManagerDetail();
    }
}
