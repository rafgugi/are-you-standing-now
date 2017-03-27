package com.naikapa.accelerometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import java.util.ArrayList;

public class AccelerometerListener implements SensorEventListener {

    private SensorActivity activity;
    private Sensor sensor;

    private HumanState state;
    private int counter;
    private ArrayList<Axis> myAxis;

    public AccelerometerListener(SensorActivity activity) {
        this.activity = activity;

        state = new HumanState(5);
        counter = 0;
        myAxis = new ArrayList<>();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // ngefilter, bisa aja ngecek sensor cahaya.
        if (activity.isBright) {
            activity.tx.setText("");
            activity.ty.setText("");
            activity.tz.setText("");
            activity.tState.setText("");

            return;
        }

        // Many sensors return 3 values, one for each axis.
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        //display values using TextView
        activity.tx.setText("X axis" + "\t\t" + x);
        activity.ty.setText("Y axis" + "\t\t" + y);
        activity.tz.setText("Z axis" + "\t\t" + z);

        myAxis.add(new Axis(x, y, z));
        counter++;
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
