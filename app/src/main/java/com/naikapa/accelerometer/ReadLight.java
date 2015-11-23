package com.naikapa.accelerometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;

/**
 * Created by sg on 11/23/2015.
 */

public class ReadLight extends Activity implements SensorEventListener {

    private String available;
    private String reading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        Sensor LightSensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (LightSensor != null) {
            available = "Sensor.TYPE_LIGHT Available";
            sm.registerListener(this, LightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            available = "Sensor.TYPE_LIGHT NOT Available";
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            reading = "LIGHT: " + event.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
    }
}