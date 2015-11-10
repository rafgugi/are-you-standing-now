package com.naikapa.accelerometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ReadAccelerometer extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    private HumanState state;
    private int counter;
    private ArrayList<Axis> myAxis;

    TextView title,tx,ty,tz;
    TextView tSit, tStand;
    TextView tState;
    RelativeLayout layout;

    public ReadAccelerometer() {
        state = new HumanState(5);
        counter = 0;
        myAxis = new ArrayList<>();
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_accelerometer);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // get layout
        layout = (RelativeLayout)findViewById(R.id.relative);

        // get textviews
        title=(TextView)findViewById(R.id.name);
        tx=(TextView)findViewById(R.id.xval);
        ty=(TextView)findViewById(R.id.yval);
        tz=(TextView)findViewById(R.id.zval);

        tSit=(TextView)findViewById(R.id.sitaxis);
        tStand=(TextView)findViewById(R.id.standaxis);
        tState=(TextView)findViewById(R.id.state);

//        tSit.setText("Average sit = " + state.average(state.sitstate));
//        tStand.setText("Average stand = " + state.average(state.standstate));
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) { }

    @Override
    public final void onSensorChanged(SensorEvent event)
    {
        if (counter == state.treshold) {
            String hasil = state.determineState(myAxis);
            tState.setText(hasil);

            myAxis.clear();
            counter = 0;
        }
        // Many sensors return 3 values, one for each axis.
        float x =  event.values[0];
        float y =  event.values[1];
        float z =  event.values[2];

        //display values using TextView
        title.setText(R.string.app_name);
        tx.setText("X axis" + "\t\t" + x);
        ty.setText("Y axis" + "\t\t" + y);
        tz.setText("Z axis" + "\t\t" + z);

        myAxis.add(new Axis(x, y, z));
        counter++;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}