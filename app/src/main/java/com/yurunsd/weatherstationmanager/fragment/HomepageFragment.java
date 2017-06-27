package com.yurunsd.weatherstationmanager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yurunsd.weatherstationmanager.R;
import com.yurunsd.weatherstationmanager.adapter.RecycAdapter;
import com.yurunsd.weatherstationmanager.base.BaseFragment;
import com.yurunsd.weatherstationmanager.login.LoginActivity;
import com.yurunsd.weatherstationmanager.login.RegisterActivity;
import com.yurunsd.weatherstationmanager.ui.AddDeviceActivity;
import com.yurunsd.weatherstationmanager.utils.OkHttpUtils;
import com.zwf.recyclerView.RecyclerViewWithEmptyView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.yurunsd.weatherstationmanager.utils.GlobalConstants.UserQueryDevices_URL;

public class HomepageFragment extends BaseFragment {

    @Bind(R.id.iv_return)
    ImageView ivReturn;
    @Bind(R.id.iv_add)
    ImageView ivAdd;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.rve_device_list)
    RecyclerViewWithEmptyView rveDeviceList;
    @Bind(R.id.sr_device_list)
    SwipeRefreshLayout srlDeviceList;

    Handler mhandler = new Handler();
    private RecycAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        ButterKnife.bind(this, view);

        toolbar_init();
        srl_init();
        rv_init();


        return view;
    }

    private void toolbar_init() {
//        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        tvTitle.setText("首页");
        ivReturn.setVisibility(View.INVISIBLE);
        ivAdd.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.iv_return, R.id.iv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                break;
            case R.id.iv_add:
                startActivity(new Intent(getActivity(), AddDeviceActivity.class));
                break;
        }
    }

    /**
     * SwipeRefreshLayout 初始化
     */

    private void srl_init() {
        srlDeviceList.setColorSchemeResources(R.color.Olympic_Rings_blue, R.color.Olympic_Rings_yellow, R.color.Olympic_Rings_black,
                R.color.Olympic_Rings_green, R.color.Olympic_Rings_red);

        srlDeviceList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                OkHttpUtils okHttpUtils = new OkHttpUtils(getActivity());
                Map<String, Object> map = new HashMap<String, Object>();

                //map.put("DeviceType", "ws");

                okHttpUtils.post(UserQueryDevices_URL, map, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        mhandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (srlDeviceList.isRefreshing()) {
                                    srlDeviceList.setRefreshing(false);
                                }
                            }
                        }, 300);

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        mhandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (srlDeviceList.isRefreshing()) {
                                    srlDeviceList.setRefreshing(false);
                                }
                            }
                        }, 300);


                    }
                });


            }
        });
    }

    /**
     * RecyclerView 初始化
     */
    List<String> mDataList;

    private void rv_init() {
        mDataList = new ArrayList<>();
        for (int i = 0; i <= 20; i++) {
            mDataList.add(String.valueOf(i));
        }
        //设置item动画
        rveDeviceList.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new RecycAdapter<>(getActivity(), mDataList);
//        adapterWrapper = new MultiSelectItemAdapterWrapper(mAdapter);
        rveDeviceList.setAdapter(mAdapter);
        //添加item点击事件监听
        mAdapter.setOnItemClickListener(new RecycAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {
                Toast.makeText(getActivity(), "click " + pos, Toast.LENGTH_SHORT).show();


            }
        });
        mAdapter.setOnItemLongClickListener(new RecycAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View itemView, int pos) {
                Toast.makeText(getActivity(), "long click " + pos, Toast.LENGTH_SHORT).show();


            }
        });
        //设置布局样式LayoutManager
        rveDeviceList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        recyclerView.addItemDecoration(new ItemDividerDecoration(MainActivity.this, OrientationHelper.VERTICAL));

    }

    /**
     * RecyclerView 点击
     */
    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            System.out.println("onItemClick " + position);
//            Intent intent = new Intent(HomepageFragment.this, MainActivityPlug.class);
//            Bundle bundle = new Bundle();
//            bundle.putInt("position", position);
//            intent.putExtras(bundle);
//            startActivity(intent);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
