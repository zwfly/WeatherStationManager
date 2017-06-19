package com.yurunsd.weatherstationmanager.fragment;

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
import com.zwf.recyclerView.RecyclerViewWithEmptyView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

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
        ivAdd.setVisibility(View.INVISIBLE);
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


                mhandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (srlDeviceList.isRefreshing()) {
                            srlDeviceList.setRefreshing(false);
                        }
                    }
                }, 2000);

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
