package com.yurunsd.weatherstationmanager.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yurunsd.weatherstationmanager.R;
import com.yurunsd.weatherstationmanager.base.BaseFragment;

/**
 * Created by admin on 2017/6/19.
 */

public class MessageFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        return view;
    }
}
