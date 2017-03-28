package com.naikapa.accelerometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.naikapa.accelerometer.clasifier.Bayes;

public class SensorActivity extends AppCompatActivity {

    private SensorManager mSensorManager;

    public TextView title, tx, ty, tz;
    public TextView tState;

    private TextView tv_status;

    private String name;
    private String result;

    private AccelerometerListener accelerometer;
    private LightListener light;

    private Button btn_record;
    private Button btn_train;
    private Button btn_test;

    private ProgressBar pb_accelero;

    public boolean isBright;

    public SensorActivity() {
        name = "Cuppy Cake";

        accelerometer = new AccelerometerListener(this);
        light = new LightListener(this);

        isBright = false;
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_accelerometer);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        /* initialize Sensor foreach sensorlistener */
        accelerometer.setSensor(mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        light.setSensor(mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));

        title = (TextView) findViewById(R.id.name);

        tx = (TextView) findViewById(R.id.tv_xval);
        ty = (TextView) findViewById(R.id.tv_yval);
        tz = (TextView) findViewById(R.id.tv_zval);

        tv_status = (TextView)findViewById(R.id.status);

        btn_record = (Button) findViewById(R.id.btn_record);
        btn_test = (Button) findViewById(R.id.btn_test);
        btn_train = (Button) findViewById(R.id.btn_train);

        pb_accelero = (ProgressBar)findViewById(R.id.pb_accelero);

        title.setText("Accelerometer");

        setOnClick();
    }

    protected void setOnClick() {
        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status = btn_record.getText().toString();
                switch(status){
                    case "Record":
                        registerListener();
                        btn_record.setText("Stop");
                        pb_accelero.setVisibility(View.VISIBLE);
                        result = Bayes.getInstance().bayes();
                        break;
                    case "Stop":
                        unRegisterListener();
                        btn_record.setText("Record");
                        pb_accelero.setVisibility(View.GONE);
                        tv_status.setText(result);
                        break;
                    default :
                        Log.d("error", "gagal record");
                }
            }
        });
        btn_train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // train the dataset with trainer method
            }
        });
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // test the data based on trainer result
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    private void registerListener(){
        /* register listener */
        mSensorManager.registerListener(accelerometer, accelerometer.getSensor(), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(light, light.getSensor(), SensorManager.SENSOR_DELAY_NORMAL);

    }

    private void unRegisterListener(){
        mSensorManager.unregisterListener(accelerometer);
        mSensorManager.unregisterListener(light);

    }

}