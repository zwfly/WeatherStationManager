package com.yurunsd.weatherstationmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;

import com.yurunsd.weatherstationmanager.base.BaseActivity;
import com.yurunsd.weatherstationmanager.base.BaseFragment;
import com.yurunsd.weatherstationmanager.fragment.HomepageFragment;
import com.yurunsd.weatherstationmanager.fragment.MessageFragment;
import com.yurunsd.weatherstationmanager.fragment.MineFragment;
import com.yurunsd.weatherstationmanager.fragment.NearbyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @Bind(R.id.rb_homepage)
    RadioButton rbHomepage;
    @Bind(R.id.rb_near)
    RadioButton rbNear;
    @Bind(R.id.rb_message)
    RadioButton rbMessage;
    @Bind(R.id.rb_mine)
    RadioButton rbMine;

    private FragmentTransaction ft = null;
    private List<BaseFragment> fragments = new ArrayList<BaseFragment>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fragments.add(new HomepageFragment());
        fragments.add(new NearbyFragment());
        fragments.add(new MessageFragment());
        fragments.add(new MineFragment());

        setDefaultFragment(fragments.get(0));
    }

    @OnClick({R.id.rb_homepage, R.id.rb_near, R.id.rb_message, R.id.rb_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_homepage:
                switchFragment(0);
                break;
            case R.id.rb_near:
                switchFragment(1);
                break;
            case R.id.rb_message:
                switchFragment(2);
                break;
            case R.id.rb_mine:
                switchFragment(3);
                break;
        }
    }

    private void switchFragment(int index) {


        switchContent(fragments.get(index));

    }

    private FragmentManager mFm;
    private Fragment mContent;

    /**
     * 设置默认的fragment，即//第一次加载界面;
     */
    private void setDefaultFragment(Fragment fm) {
        mFm = getSupportFragmentManager();
        FragmentTransaction mFragmentTrans = mFm.beginTransaction();

        mFragmentTrans.add(R.id.fg_container, fm).commit();

        mContent = fm;
    }

    /**
     * 修改显示的内容 不会重新加载 *
     */
    public void switchContent(Fragment to) {
        if (mContent != to) {
            FragmentTransaction transaction = mFm.beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(mContent).add(R.id.fg_container, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(mContent).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
            mContent = to;
        }
    }

    /**
     * 修改显示的内容 但会重新加载 *
     */
    public void switchContent2(Fragment to) {
        FragmentTransaction transaction = mFm.beginTransaction();
        transaction.replace(R.id.fg_container, to);
        //transaction.addToBackStack(null);
        transaction.commit();
    }
}
