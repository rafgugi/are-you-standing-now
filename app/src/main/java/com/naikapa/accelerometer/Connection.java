package com.naikapa.accelerometer;

/**
 * Created by sg on 11/23/2015.
 */
public class Connection {

    public void send(String name, String state, Object other) {
        // ngirim data nama dan state ke server. ga tau servernya gimana
    }

    public void send(String name, String state) {
        this.send(name, state, null);
    }

}
