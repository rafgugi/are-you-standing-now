package com.naikapa.accelerometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by sg on 11/23/2015.
 */
public class LightListener implements SensorEventListener {

    private SensorActivity activity;
    private Sensor sensor;

    public LightListener(SensorActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            activity.lightStatus.setText("LIGHT: " + event.values[0]);

            activity.isBright = event.values[0] > 6.0;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
