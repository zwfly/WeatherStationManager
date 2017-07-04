package com.yurunsd.weatherstationmanager.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yurunsd.weatherstationmanager.R;
import com.yurunsd.weatherstationmanager.utils.OkHttpUtils;
import com.yurunsd.weatherstationmanager.utils.ToastUtils;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.yurunsd.weatherstationmanager.utils.GlobalConstants.WeatherStationConfigUpdate_URL;
import static com.yurunsd.weatherstationmanager.utils.GlobalConstants.WeatherStationRecordQuery_URL;

public class HistoryActivity extends AppCompatActivity {

    @Bind(R.id.iv_return)
    ImageView ivReturn;
    @Bind(R.id.iv_add)
    ImageView ivAdd;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.spn_hours)
    Spinner spnHours;
    @Bind(R.id.btn_query)
    Button btnQuery;
    @Bind(R.id.chart)
    LineChartView chart;

    Handler mhandler = new Handler();
    Map<String, Object> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);

        param_init();
        title_init();
        body_init();

    }

    private void param_init() {

        map = (Map<String, Object>) getIntent().getExtras().getSerializable("map");

    }

    String target;

    private void title_init() {
        String s;
        target = (String) map.get("target");
        if (target == null) {
            target = "未知";
        }

        switch (target) {
            case "temperature":
                tvTitle.setText("查询温度");
                break;
            case "humidity":
                tvTitle.setText("查询湿度");
                break;
            case "PM2d5":
                tvTitle.setText("查询PM2.5");
                break;
            case "PM10":
                tvTitle.setText("查询PM10");
                break;
            case "windSpeed":
                tvTitle.setText("查询风速");
                break;
            case "windDirection":
                tvTitle.setText("查询风向");
                break;
            default:
                tvTitle.setText(target);
                break;
        }

        ivReturn.setVisibility(View.VISIBLE);

//        ivAdd.setImageResource(R.drawable.ic_setup);
        ivAdd.setVisibility(View.INVISIBLE);

    }

    private void body_init() {

        ArrayAdapter<String> ada = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.hoursHistory));
        ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnHours.setAdapter(ada);
        spnHours.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnHours.setSelection(0, true);


        drawable_data2chart();
        chart.setOnValueTouchListener(new ValueTouchListener());
    }

    @OnClick({R.id.iv_return, R.id.iv_add, R.id.btn_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.iv_add:
                break;
            case R.id.btn_query:
                query_pro();
                break;
        }
    }


    private void query_pro() {

        OkHttpUtils okHttpUtils = new OkHttpUtils(HistoryActivity.this);
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("deviceId", this.map.get("deviceId"));
        map.put("aheadHours", spnHours.getSelectedItemPosition() + 1);
        map.put(target, "y");

        okHttpUtils.post(WeatherStationRecordQuery_URL, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mhandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShort(HistoryActivity.this, "服务器超时");
//                        finish();
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
                System.out.println("response: " + s);

                if (s == null) {
                    return;
                }

                Type type = new TypeToken<Map<String, Object>>() {
                }.getType();
                final Map<String, Object> map;
                try {
                    map = new Gson().fromJson(s, type);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                final List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("list");


                mhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShort(HistoryActivity.this, (String) map.get("msg"));
                        if (StringUtils.equals("y", (String) map.get("isSuccess"))) {

                            list_data.clear();
                            for (int i = 0; i < list.size(); i++) {
                                try {
                                    list_data.add(Float.valueOf(String.valueOf(list.get(i).get(target))));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            drawable_data2chart();

                        } else {

                        }
                    }
                });
            }
        });


    }


    private class ValueTouchListener implements LineChartOnValueSelectListener {

        @Override
        public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
            Toast.makeText(getApplicationContext(), "Selected: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }

    private LineChartData data;
    List<Float> list_data = new Vector<Float>();

    private void drawable_data2chart() {

        List<Line> lines = new ArrayList<Line>();

        List<PointValue> values_delay = new ArrayList<PointValue>();
        for (int j = 0; j < list_data.size(); j++) {
            values_delay.add(new PointValue(j, list_data.get(j)));
        }

        Line line_delay = new Line(values_delay);
        line_delay.setStrokeWidth(2);
        line_delay.setColor(ChartUtils.COLORS[0]);
        line_delay.setShape(ValueShape.CIRCLE); //折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line_delay.setCubic(true);  //曲线是否平滑，即是曲线还是折线
        line_delay.setFilled(false); //是否填充曲线的面积
        line_delay.setHasLabels(true); //曲线的数据坐标是否加上备注
        line_delay.setHasLabelsOnlyForSelected(true); //点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line_delay.setHasLines(true); //是否用线显示。如果为false 则没有曲线只有点显示
        line_delay.setHasPoints(true); //是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        // line.setHasGradientToTransparent(hasGradientToTransparent);
        line_delay.setPointRadius(3);
        line_delay.setPointColor(ChartUtils.COLORS[2]);
        lines.add(line_delay);

        data = new LineChartData(lines);

        Axis axisX = new Axis();
        axisX.setHasTiltedLabels(false);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.BLACK);  //设置字体颜色
        //axisX.setName("date");  //表格名称
        axisX.setTextSize(12);//设置字体大小
        // axisX.setMaxLabelChars(8); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线
        axisX.setName("时间");


        Axis axisY = new Axis().setHasLines(true);
        axisY.setTextSize(12);//设置字体大小
        //data.setAxisYRight(axisY);  //y轴设置在右边
        axisY.setTextColor(Color.BLACK);  //设置字体颜色


        switch (target) {
            case "temperature":
                axisY.setName("摄氏度/℃");
                break;
            case "humidity":
                axisY.setName("湿度/%");
                break;
            case "PM2d5":
                axisY.setName("PM2.5/ug/m3");
                break;
            case "PM10":
                axisY.setName("PM10/ug/m3");
                break;
            case "windSpeed":
                axisY.setName("风速/m/s");
                break;
            case "windDirection":
                axisY.setName("风向");
                break;
            default:
                axisY.setName("值");
                break;
        }


        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);


        data.setBaseValue(Float.NEGATIVE_INFINITY);

        chart.setViewportCalculationEnabled(false);

        chart.setZoomEnabled(true);
        chart.setInteractive(true);//设置图表是否可以与用户互动
        chart.setValueSelectionEnabled(true);//设置图表数据是否选中进行显示
        chart.setLineChartData(data);//为图表设置数据，数据类型为LineChartData
        //    chart.startDataAnimation();
        chart.setViewportCalculationEnabled(false);
        resetViewport();
    }

    private void resetViewport() {
        // Reset viewport height range to (0,100)
        final Viewport v = new Viewport(chart.getMaximumViewport());
        v.bottom = 0;
        float max = 100;

        switch (target) {
            case "temperature":
                max = 30.0f;
                break;
            case "humidity":
                max = 30.0f;
                break;
            case "PM2d5":
                max = 10.0f;
                break;
            case "PM10":
                max = 10.0f;
                break;
            case "windSpeed":
                max = 1.0f;
                break;
            case "windDirection":

                break;
            default:
                max = 10.0f;
                break;
        }


        for (int j = 0; j < list_data.size(); j++) {
            if (list_data.get(j) > max) {
                max = list_data.get(j);
            }
        }
        v.top = max + 10;
        v.left = 0;
        if (list_data.size() < 10) {
            v.right = 9;
        } else {
            v.right = list_data.size() - 1;
        }
        chart.setMaximumViewport(v);
        chart.setCurrentViewport(v);
    }
}
