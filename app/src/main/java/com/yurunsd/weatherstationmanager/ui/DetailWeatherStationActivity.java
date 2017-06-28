package com.yurunsd.weatherstationmanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yurunsd.weatherstationmanager.R;
import com.yurunsd.weatherstationmanager.login.store.PersistentCookieStore;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailWeatherStationActivity extends AppCompatActivity {

    @Bind(R.id.iv_return)
    ImageView ivReturn;
    @Bind(R.id.iv_add)
    ImageView ivAdd;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.lv_bottom)
    ListView lvBottom;
    @Bind(R.id.iv_header)
    ImageView ivHeader;
    @Bind(R.id.tv_left_info1)
    TextView tvLeftInfo1;
    @Bind(R.id.tv_right_info1)
    TextView tvRightInfo1;
    @Bind(R.id.layout_right_info1)
    RelativeLayout layoutRightInfo1;
    @Bind(R.id.tv_right_info2)
    TextView tvRightInfo2;
    @Bind(R.id.layout_right_info2)
    RelativeLayout layoutRightInfo2;
    @Bind(R.id.tv_right_info3)
    TextView tvRightInfo3;
    @Bind(R.id.layout_right_info3)
    RelativeLayout layoutRightInfo3;
    @Bind(R.id.tv_right_info4)
    TextView tvRightInfo4;
    @Bind(R.id.layout_right_info4)
    RelativeLayout layoutRightInfo4;
    @Bind(R.id.tv_right_info5)
    TextView tvRightInfo5;
    @Bind(R.id.layout_right_info5)
    RelativeLayout layoutRightInfo5;
    @Bind(R.id.tv_right_info6)
    TextView tvRightInfo6;
    @Bind(R.id.layout_right_info6)
    RelativeLayout layoutRightInfo6;


    Map<String, Object> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_weather_station);
        ButterKnife.bind(this);


        param_init();
        title_init();
        body_init();

    }


    private void param_init() {

        map = (Map<String, Object>) getIntent().getExtras().getSerializable("map");

    }

    private void title_init() {

        String s = (String) map.get("deviceName");
        if (s == null) {
            s = StringUtils.right((String) map.get("deviceId"), 6);
            if (s == null) {
                s = "无名";
            }
        }
        tvTitle.setText(s);
        ivReturn.setVisibility(View.VISIBLE);

        ivAdd.setImageResource(R.drawable.ic_setup);
        ivAdd.setVisibility(View.VISIBLE);

    }

    private void body_init() {

        String type = (String) map.get("deviceType");
        if (type == null) {
            type = "0";
        }
        switch (type) {
            case "2":
                weatherStation_pro();

                break;
            default:
                break;
        }


    }

    private void weatherStation_pro() {

        tvLeftInfo1.setText((map.get("deviceAddr") == null) ? "" : (String) map.get("deviceAddr"));

        tvRightInfo1.setText((map.get("temperature") == null) ? "" : "温度: " + map.get("temperature") + " ℃");
        tvRightInfo2.setText((map.get("humidity") == null) ? "" : "湿度: " + map.get("humidity") + " %");

        tvRightInfo3.setText((map.get("PM2d5") == null) ? "" : "PM2.5: " + ((Double) map.get("PM2d5")).intValue() + " ug/m3");
        tvRightInfo4.setText((map.get("PM10") == null) ? "" : "PM10 : " + ((Double) map.get("PM10")).intValue() + " ug/m3");

        tvRightInfo5.setText((map.get("windSpeed") == null) ? "" : "风速: " + map.get("windSpeed") + "m/s");
        tvRightInfo6.setText((map.get("windDirection") == null) ? "" : "风向: " + map.get("windDirection"));


    }

    @OnClick({R.id.iv_return, R.id.iv_add, R.id.layout_right_info1, R.id.layout_right_info2, R.id.layout_right_info3, R.id.layout_right_info4, R.id.layout_right_info5, R.id.layout_right_info6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.iv_add:
                Intent intent = new Intent(DetailWeatherStationActivity.this, WeatherStationSetupActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_right_info1:
                break;
            case R.id.layout_right_info2:
                break;
            case R.id.layout_right_info3:
                break;
            case R.id.layout_right_info4:
                break;
            case R.id.layout_right_info5:
                break;
            case R.id.layout_right_info6:
                break;
        }
    }
}
