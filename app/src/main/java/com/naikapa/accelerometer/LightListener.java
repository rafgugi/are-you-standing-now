package com.naikapa.accelerometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class LightListener implements SensorEventListener {

    private SensorActivity activity;
    private Sensor sensor;

    public LightListener(SensorActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
//        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
//            int lux = (int) event.values[0];
//            activity.lightStatus.setText("LIGHT: " + lux);
//
//            boolean isBright = lux > 10;
//            activity.isBright = isBright;
//
//            activity.lightTitle.setText(isBright ? "Bright" : "Dark");
//        }
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
