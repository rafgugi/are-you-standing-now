package com.naikapa.accelerometer;

import java.util.ArrayList;

/**
 * Created by sg on 11/10/2015.
 */
public class Axis {

    public float x;
    public float y;
    public float z;

    public Axis(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Axis(double x, double y, double z) {
        this((float) x, (float) y, (float) z);
    }

    public static Axis average(ArrayList<Axis> data) {
        Axis ans = new Axis(0, 0, 0);
        for (Axis axis : data) {
            ans.x = ans.x + axis.x;
            ans.y = ans.y + axis.z;
            ans.z = ans.z + axis.z;
        }
        ans.x = ans.x / data.size();
        ans.y = ans.y / data.size();
        ans.z = ans.z / data.size();

        return ans;
    }

    @Override
    public String toString() {
        return "x = " + x + ", y = " + y + ", z = " + z;
    }
}
