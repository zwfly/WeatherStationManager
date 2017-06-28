package com.yurunsd.weatherstationmanager.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.MapView;
import com.yurunsd.weatherstationmanager.R;
import com.yurunsd.weatherstationmanager.base.BaseFragment;

import org.apache.commons.lang3.ObjectUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/6/19.
 */

public class NearbyFragment extends BaseFragment {

    @Bind(R.id.iv_return)
    ImageView ivReturn;
    @Bind(R.id.iv_add)
    ImageView ivAdd;
    @Bind(R.id.tv_title)
    TextView tvTitle;

    @Bind(R.id.bmapView)
    MapView bmapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nearby, container, false);
        ButterKnife.bind(this, view);

        param_init();
        title_init();

        return view;
    }


    private void title_init() {

        tvTitle.setText("附近");
        ivReturn.setVisibility(View.INVISIBLE);
        ivAdd.setVisibility(View.INVISIBLE);
    }

    private void param_init() {


    }


    @OnClick({R.id.iv_return, R.id.iv_add, R.id.tv_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                break;
            case R.id.iv_add:
                break;
            case R.id.tv_title:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if (ObjectUtils.notEqual(bmapView,null)) {
            bmapView.onDestroy();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        bmapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        bmapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

