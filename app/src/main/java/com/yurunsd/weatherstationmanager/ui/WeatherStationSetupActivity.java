package com.yurunsd.weatherstationmanager.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yurunsd.weatherstationmanager.R;
import com.yurunsd.weatherstationmanager.utils.OkHttpUtils;
import com.yurunsd.weatherstationmanager.utils.ToastUtils;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.yurunsd.weatherstationmanager.utils.GlobalConstants.WeatherStationConfigUpdate_URL;


public class WeatherStationSetupActivity extends AppCompatActivity {

    @Bind(R.id.iv_return)
    ImageView ivReturn;
    @Bind(R.id.iv_add)
    ImageView ivAdd;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.et_deviceName)
    EditText etDeviceName;
    @Bind(R.id.et_deviceAddr)
    EditText etDeviceAddr;
    @Bind(R.id.et_phoneNum)
    EditText etPhoneNum;
    @Bind(R.id.btn_ok)
    Button btnOk;


    Map<String, Object> map;
    Handler mhandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_station_setup);
        ButterKnife.bind(this);


        param_init();
        title_init();
        body_init();

    }

    private void param_init() {
        map = (Map<String, Object>) getIntent().getExtras().getSerializable("map");


    }

    private void title_init() {

        tvTitle.setText("修改");
        ivReturn.setVisibility(View.VISIBLE);

        ivAdd.setImageResource(R.drawable.ic_right);
        ivAdd.setVisibility(View.VISIBLE);

        btnOk.setVisibility(View.INVISIBLE);

    }

    private void body_init() {

        etDeviceName.setText((map.get("deviceName") == null) ? "" : (String) map.get("deviceName"));
        etDeviceAddr.setText((map.get("deviceAddr") == null) ? "" : (String) map.get("deviceAddr"));
        etPhoneNum.setText((map.get("phoneNumber") == null) ? "" : (String) map.get("phoneNumber"));

    }

    @OnClick({R.id.iv_return, R.id.iv_add, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.iv_add:

                sendData();

                break;
            case R.id.btn_ok:
                break;
        }
    }


    private void sendData() {

        OkHttpUtils okHttpUtils = new OkHttpUtils(WeatherStationSetupActivity.this);
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("deviceId", this.map.get("deviceId"));
        map.put("deviceName", etDeviceName.getText().toString());
        map.put("deviceAddr", etDeviceAddr.getText().toString());
        map.put("phoneNumber", etPhoneNum.getText().toString());

        okHttpUtils.post(WeatherStationConfigUpdate_URL, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mhandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShort(WeatherStationSetupActivity.this, "服务器超时");
                        finish();
                    }
                }, 300);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mhandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 300);
                String s = response.body().string();
                System.out.println("response " + s);

                if (s == null) {
                    return;
                }

                Type type = new TypeToken<Map<String, Object>>() {
                }.getType();
                final Map<String, Object> map = new Gson().fromJson(s, type);


                mhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShort(WeatherStationSetupActivity.this, (String) map.get("msg"));
                        if (StringUtils.equals("y", (String) map.get("isSuccess"))) {
                            finish();
                        } else {

                        }
                    }
                });
            }
        });


    }
}
