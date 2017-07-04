package com.yurunsd.weatherstationmanager.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yurunsd.weatherstationmanager.R;
import com.yurunsd.weatherstationmanager.base.BaseFragment;
import com.yurunsd.weatherstationmanager.utils.HttpUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by admin on 2017/6/19.
 */

public class MineFragment extends BaseFragment {

    @Bind(R.id.iv_return)
    ImageView ivReturn;
    @Bind(R.id.iv_add)
    ImageView ivAdd;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.button2)
    Button button2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);

        param_init();
        title_init();


        return view;
    }


    private void title_init() {

        tvTitle.setText("我的");
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

    @OnClick({R.id.button2, R.id.iv_return, R.id.iv_add, R.id.tv_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button2:

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        OkHttpClient client = new OkHttpClient();
                        // 设置超时时间
                        client.newBuilder().connectTimeout(6, TimeUnit.SECONDS)
                                .writeTimeout(6, TimeUnit.SECONDS)
                                .readTimeout(6, TimeUnit.SECONDS)
                                .build();

                        Request.Builder builder = new Request.Builder();

                        Request request = builder.url("http://www.baidu.com").build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                System.out.println("onFailure: ");

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                                System.out.println("onResponse: " + response.body().string());
                            }
                        });
                    }
                }).start();


                break;
            case R.id.iv_return:
                break;
            case R.id.iv_add:
                break;
            case R.id.tv_title:
                break;
        }
    }

}
