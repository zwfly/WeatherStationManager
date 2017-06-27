package com.yurunsd.weatherstationmanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yurunsd.weatherstationmanager.MainActivity;
import com.yurunsd.weatherstationmanager.R;
import com.yurunsd.weatherstationmanager.login.LoginActivity;
import com.yurunsd.weatherstationmanager.login.store.CookieJarImpl;
import com.yurunsd.weatherstationmanager.login.store.PersistentCookieStore;
import com.yurunsd.weatherstationmanager.utils.ToastUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.yurunsd.weatherstationmanager.utils.GlobalConstants.UserDeviceAdd_URL;

public class AddDeviceActivity extends AppCompatActivity {

    @Bind(R.id.iv_return)
    ImageView ivReturn;
    @Bind(R.id.iv_add)
    ImageView ivAdd;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.textView5)
    TextView textView5;
    @Bind(R.id.et_uid)
    EditText etUid;
    @Bind(R.id.btn_add)
    Button btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        ButterKnife.bind(this);


        param_init();
        title_init();


        body_init();
    }


    private void param_init() {
        persistentCookieStore = new PersistentCookieStore(getApplicationContext());
    }

    private void title_init() {

        tvTitle.setText("添加设备");
        ivReturn.setVisibility(View.VISIBLE);
        ivAdd.setVisibility(View.INVISIBLE);
    }

    private void body_init() {

    }


    @OnClick({R.id.iv_return, R.id.iv_add, R.id.tv_title, R.id.btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.iv_add:
                break;
            case R.id.tv_title:
                break;
            case R.id.btn_add:


                if (etUid.getText().toString().length() < 10) {
                    ToastUtils.showShort(getApplicationContext(), "输入有误");
                } else {
                    addDevice(etUid.getText().toString());

                    btnAdd.setText(btnAdd.getText() + "...");
                    btnAdd.setEnabled(false);
                }


                break;
        }
    }

    Handler mhandler = new Handler(Looper.getMainLooper());
    private PersistentCookieStore persistentCookieStore;

    private void addDevice(String uid) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = new FormBody.Builder()
                .add("uid", uid)
                .build();

        CookieJarImpl cookieJarImpl = new CookieJarImpl(persistentCookieStore);
        Request request = new Request.Builder().post(body).url(UserDeviceAdd_URL).build();
        OkHttpClient client = new OkHttpClient().newBuilder().cookieJar(cookieJarImpl).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                btnAdd.setText("添加");
                btnAdd.setEnabled(true);

                mhandler.post(new Runnable() {
                    public void run() {
                        ToastUtils.showShort(AddDeviceActivity.this, "服务器超时");

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String bodystring = response.body().string();
                System.out.println("body " + bodystring);
                Type type = new TypeToken<Map<String, String>>() {
                }.getType();
                final Map<String, String> map = new Gson().fromJson(bodystring, type);

//                final String isSuccess = map.get("isSuccess");
                mhandler.post(new Runnable() {
                    public void run() {
                        if (map == null) {
                            ToastUtils.showLong(AddDeviceActivity.this, "数据发生错误");
                            return;
                        }
                        String msg = map.get("msg");
                        if (!StringUtils.equals(msg, null)) {
                            ToastUtils.showShort(AddDeviceActivity.this, msg);
                        }


                        btnAdd.setText("添加");
                        btnAdd.setEnabled(true);
                    }
                });

                String isOK = map.get("isSuccess");

                if (StringUtils.equals(isOK, "y")) {

                    finish();
                }
            }
        });


    }
}
