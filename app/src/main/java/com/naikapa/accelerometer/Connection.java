package com.naikapa.accelerometer;

/**
 * Created by sg on 11/23/2015.
 */
public class Connection {

    public static void send(String name, String state, Object other) {
        // ngirim data nama dan state ke server. ga tau servernya gimana
    }

    public static void send(String name, String state) {
        Connection.send(name, state, null);
    }

}
