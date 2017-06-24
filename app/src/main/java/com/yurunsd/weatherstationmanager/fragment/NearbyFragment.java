package com.yurunsd.weatherstationmanager.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yurunsd.weatherstationmanager.R;
import com.yurunsd.weatherstationmanager.base.BaseFragment;

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
}
