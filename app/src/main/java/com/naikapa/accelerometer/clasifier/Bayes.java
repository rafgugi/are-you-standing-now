package com.naikapa.accelerometer.clasifier;

import android.util.Log;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

/**
 * Created by Hendro E. Prabowo on 24/03/2017.
 */

public class Bayes {

    private Bayes instance;
    private Instances train, test;
    private double label;
    private ArrayList<Axis> sensorData = new ArrayList<>();

    private Bayes() {
        train = fileToInstances("\\database\\dataset.arff");
        test = fileToInstances("\\database\\template.arff");
    }

    public static Bayes getInstance() {
        if (instance == null) {
            instance = new Bayes();
        }
        return instance;
    }

    /**
     * Masukan data sensor ke record
     */
    public void record(float x, float y, float x) {
        record(new Axis(x, y, z));
    }

    /**
     * Masukan data sensor ke record
     */
    public void record(Axis axis) {
        sensorData.add(axis);
    }

    /**
     * Ngubah dari file path menjadi Instances. Root directorynya ada di
     * root aplikasi.
     */
    public Instances fileToInstances(String path) {
        String currentPath = System.getProperty("user.dir");
        String csvFile = currentPath + path;
        Instances dataset;

        try {
            ConverterUtils.DataSource source = new ConverterUtils.DataSource(csvFile);
            dataset = source.getDataSet();

            if (dataset.classIndex() == -1){
                dataset.setClassIndex(dataset.numAttributes() - 1);
            }
        } catch (Exception e){
            Log.d("error", e.toString());
        }
        return new Instances(dataset);
    }

    /**
     * Ngubah sensorData menjadi data yang dibutuhkan weka
     */
    public double[] getDataFromRaw() {
        vals = new double[data.numAttributes()];
        vals[0] = 1;
        vals[1] = 1;
        vals[35] = 0; // nilai max x
        vals[36] = 0; // nilai max y
        vals[37] = 0; // nilai max z
        for (int i = 0; i < sensorData.length; i++) {
            Axis axis = sensorData.get(i);
            if (i < 10) {
                vals[2 + i] = axis.x;
                vals[12 + i] = axis.y;
                vals[22 + i] = axis.z;
            }
            if (axis.x > vals[35]) {
                vals[35] = axis.x
            }
            if (axis.y > vals[36]) {
                vals[36] = axis.y
            }
            if (axis.z > vals[37]) {
                vals[37] = axis.z
            }
        }
        Axis avg = Axis.average(sensorData);
        vals[32] = avg.x;
        vals[33] = avg.y;
        vals[34] = avg.z;

        return vals;
    }

    /**
     * Proses ngitung dengan data training dan dataset
     */
    public String bayes() {
        if (sensorData.length < 10) {
            return "Data sensor masih kurang dari 10 (" + sensorData.length + ")";
        }

        /* proses dulu sensor data nya menjadi data sesuai data set attribute lengkap */
        double[] vals = getDataFromRaw();
        test.delete(0);
        test.add(new Instances(1.0, vals));

        try {
            NaiveBayes naiveBayes = new NaiveBayes();
            naiveBayes.buildClassifier(train);

            // for evalutaion
            Evaluation eval = new Evaluation(train);
            eval.predictions();

            // for prediction
            label = naiveBayes.classifyInstance(test.instance(0));
            test.instance(0).setClassValue(label);

            Log.d("Prediction", "Class predicted : " + test.classAttribute().value((int)label));

        } catch (Exception e) {
            Log.d("error", e.toString());
        }

        return test.classAttribute().value((int)label);
    }
}
