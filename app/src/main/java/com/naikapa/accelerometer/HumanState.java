package com.naikapa.accelerometer;

import java.util.ArrayList;

/**
 * Created by sg on 11/10/2015.
 */
public class HumanState {

    public ArrayList<Axis> sitstate;
    public ArrayList<Axis> standstate;
    public int treshold;

    public HumanState(int treshold) {
        sitstate = new ArrayList<>();
        standstate = new ArrayList<>();

        initSitDataset();
        initStandDataset();

        this.treshold = treshold;
    }

    public HumanState() {
        this(24);
    }

    public void initSitDataset() {
        sitstate.add(new Axis(-1.019, -2.118, 9.198));
        sitstate.add(new Axis(-1.078, -2.177, 9.218));
        sitstate.add(new Axis(-1.098, -2.196, 9.277));
        sitstate.add(new Axis(-1.098, -2.216, 9.218));
        sitstate.add(new Axis(-1.059, -2.216, 9.237));
        sitstate.add(new Axis(-1.098, -2.216, 9.296));
        sitstate.add(new Axis(-1.117, -2.216, 9.237));
        sitstate.add(new Axis(-1.078, -2.196, 9.218));
        sitstate.add(new Axis(-1.059, -2.177, 9.159));
        sitstate.add(new Axis(-1.117, -2.196, 9.12));
        sitstate.add(new Axis(-1.117, -2.216, 9.277));
        sitstate.add(new Axis(-1.117, -2.137, 9.179));
        sitstate.add(new Axis(-1.137, -2.177, 9.179));
        sitstate.add(new Axis(-1.059, -2.196, 9.159));
        sitstate.add(new Axis(-1.059, -2.157, 9.179));
        sitstate.add(new Axis(-1.078, -2.059, 9.179));
        sitstate.add(new Axis(-1.059, -2.098, 9.159));
        sitstate.add(new Axis(-1.078, -2.157, 9.218));
        sitstate.add(new Axis(-1.098, -2.177, 9.257));
        sitstate.add(new Axis(-1.157, -2.157, 9.277));
        sitstate.add(new Axis(-1.117, -2.157, 9.237));
        sitstate.add(new Axis(-1.098, -2.216, 9.257));
        sitstate.add(new Axis(-1.098, -2.255, 9.198));
        sitstate.add(new Axis(-1.117, -2.177, 9.257));
    }

    public void initStandDataset() {
        standstate.add(new Axis(-1.333, -9.688, 1.274));
        standstate.add(new Axis(-1.294, -9.688, 1.333));
        standstate.add(new Axis(-1.196, -9.649, 1.353));
        standstate.add(new Axis(-1.274, -9.669, 1.431));
        standstate.add(new Axis(-1.255, -9.669, 1.392));
        standstate.add(new Axis(-1.216, -9.688, 1.412));
        standstate.add(new Axis(-1.235, -9.767, 1.451));
        standstate.add(new Axis(-1.255, -9.767, 1.47));
        standstate.add(new Axis(-1.255, -9.787, 1.49));
        standstate.add(new Axis(-1.235, -9.747, 1.451));
        standstate.add(new Axis(-1.274, -9.747, 1.451));
        standstate.add(new Axis(-1.353, -9.708, 1.647));
        standstate.add(new Axis(-1.392, -9.669, 1.588));
        standstate.add(new Axis(-1.451, -9.61, 1.51));
        standstate.add(new Axis(-1.412, -9.571, 1.49));
        standstate.add(new Axis(-1.529, -9.688, 1.549));
        standstate.add(new Axis(-1.569, -9.688, 1.529));
        standstate.add(new Axis(-1.529, -9.747, 1.569));
        standstate.add(new Axis(-1.47, -9.747, 1.608));
        standstate.add(new Axis(-1.47, -9.787, 1.647));
        standstate.add(new Axis(-1.451, -9.708, 1.588));
        standstate.add(new Axis(-1.353, -9.708, 1.608));
        standstate.add(new Axis(-1.333, -9.61, 1.647));
        standstate.add(new Axis(-1.274, -9.512, 1.647));
    }

    public String determineState(ArrayList<Axis> data) {
        Axis avg = Axis.average(data);
        Axis avgsit = Axis.average(sitstate);
        Axis avgstand = Axis.average(standstate);

        double sit = Math.sqrt(Math.pow(avg.x - avgsit.x, 2) + Math.pow(avg.y - avgsit.y, 2) + Math.pow(avg.z - avgsit.z, 2));
        double stand = Math.sqrt(Math.pow(avg.x - avgstand.x, 2) + Math.pow(avg.y - avgstand.y, 2) + Math.pow(avg.z - avgstand.z, 2));

        return sit < stand ? "Sit" : "Stand";
    }
}
