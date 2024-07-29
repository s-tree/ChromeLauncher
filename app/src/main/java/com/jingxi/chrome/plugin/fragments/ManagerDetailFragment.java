package com.jingxi.chrome.plugin.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.jingxi.chrome.plugin.MainActivity;
import com.jingxi.chrome.plugin.ManagerActivity;
import com.jingxi.chrome.plugin.R;
import com.jingxi.chrome.plugin.utils.SPUtil;

public class ManagerDetailFragment extends Fragment implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{
    View changePass,changeUrl;
    Switch menuSwitch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_detail,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        changePass = view.findViewById(R.id.changePass);
        changePass.setOnClickListener(this);
        changeUrl = view.findViewById(R.id.changeUrl);
        changeUrl.setOnClickListener(this);
        menuSwitch = (Switch) view.findViewById(R.id.menuSwitch);
        menuSwitch.setChecked(!SPUtil.isShowHide());
        menuSwitch.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.changePass){
            ManagerActivity.managerActivity.showManagerChange();
        }
        else if(v.getId() == R.id.changeUrl){
            ManagerActivity.managerActivity.showManagerUrl();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SPUtil.setShowHide(!isChecked);
        Intent intent = new Intent(MainActivity.ACTION);
        intent.putExtra(MainActivity.KEY_SHOW,!isChecked);
        getActivity().sendBroadcast(intent);
    }
}
