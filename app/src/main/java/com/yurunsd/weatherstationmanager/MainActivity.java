package com.yurunsd.weatherstationmanager;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {


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

        switchFragment(0);
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
        ft = getSupportFragmentManager().beginTransaction();
        //    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.replace(R.id.fg_container, fragments.get(index));
        ft.commit();
    }
}
