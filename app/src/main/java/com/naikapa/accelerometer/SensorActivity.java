package com.naikapa.accelerometer;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import java.util.ArrayList;

public class SensorActivity extends Activity {

    private SensorManager mSensorManager;

    public TextView title, tx, ty, tz;
    public TextView tSit, tStand;
    public TextView tState;

    public TextView lightTitle;
    public TextView lightStatus;

    private String name;

    private AccelerometerListener accelerometer;
    private LightListener light;

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

        accelerometer.setSensor(mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        light.setSensor(mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));

        title = (TextView) findViewById(R.id.name);

        tx = (TextView) findViewById(R.id.xval);
        ty = (TextView) findViewById(R.id.yval);
        tz = (TextView) findViewById(R.id.zval);

        tSit = (TextView) findViewById(R.id.sitaxis);
        tStand = (TextView) findViewById(R.id.standaxis);
        tState = (TextView) findViewById(R.id.state);

        lightTitle = (TextView) findViewById(R.id.lightname);
        lightStatus = (TextView) findViewById(R.id.lightstatus);

        title.setText("Accelerometer");
        lightTitle.setText("Light");

        if (light != null) {
            lightStatus.setText("Sensor.TYPE_LIGHT Available");
        } else {
            lightStatus.setText("Sensor.TYPE_LIGHT NOT Available");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(accelerometer, accelerometer.getSensor(), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(light, light.getSensor(), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(accelerometer);
        mSensorManager.unregisterListener(light);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}