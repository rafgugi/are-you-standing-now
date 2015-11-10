package com.naikapa.accelerometer;

/**
 * Created by sg on 11/10/2015.
 */
public class Axis implements Comparable {

    public float x;
    public float y;
    public float z;

    public Axis(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Axis(double x, double y, double z) {
        this((float) x, (float) y, (float) z);
    }

    @Override
    public int compareTo(Object another) {
        return 0;
    }

    @Override
    public String toString() {
        return "x = " + x + ", y = " + y + ", z = " + z;
    }
}
