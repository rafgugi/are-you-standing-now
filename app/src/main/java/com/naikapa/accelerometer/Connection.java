package com.naikapa.accelerometer;

/**
 * Created by sg on 11/23/2015.
 */
public class Connection {

    public static void send(String name, String state, Object other) {
        String url = "http://10.151.36.32:3000/sensor?status=" + state;
        new Record().execute(url);
    }

    public static void send(String name, String state) {
        Connection.send(name, state, null);
    }

}
