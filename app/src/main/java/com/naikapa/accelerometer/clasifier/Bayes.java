package com.naikapa.accelerometer.clasifier;

import android.net.Uri;
import android.util.Log;

import com.naikapa.accelerometer.Axis;

import java.util.ArrayList;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

/**
 * Created by Hendro E. Prabowo on 24/03/2017.
 */

public class Bayes {

    private static Bayes instance;
    private InputStream trainStream, testStream;
    private Instances train, test;
    private double label;
    private boolean acceptData = true;
    private ArrayList<Axis> sensorData = new ArrayList<>();
    private String currentPath;
    private Uri trainFile, testFile;
    private boolean fileExist = false;

    private Bayes() {
    }

    public static Bayes getInstance() {
        if (instance == null) {
            instance = new Bayes();
        }
        return instance;
    }

    public void setTrain(InputStream stream) {
        trainStream = stream;
    }

    public void setTest(InputStream stream) {
        testStream = stream;
    }

    /**
     * Masukan data sensor ke record
     */
    public void record(float x, float y, float z) {
        record(new Axis(x, y, z));
    }

    /**
     * Masukan data sensor ke record
     */
    public void record(Axis axis) {
        if (acceptData) {
            sensorData.add(axis);
        }
    }

    /**
     * Hapus smua data rekaman.
     */
    public void resetData() {
        sensorData.clear();
    }

    public void start() {
        acceptData = true;
    }

    public void stop() {
        acceptData = false;
    }

    /**
     * Ngubah dari file path menjadi Instances. Root directorynya ada di
     * root aplikasi.
     */
    public Instances fileToInstances(InputStream stream) {
        Instances dataset = null;

        try {
            // InputStream ins = getResources().openRawResource(
            //     getResources().getIdentifier(path, "raw", getPackageName()));
            // ConverterUtils.DataSource source = new ConverterUtils.DataSource(ins);
            ConverterUtils.DataSource source = new ConverterUtils.DataSource(stream);
            dataset = source.getDataSet();

            if (dataset.classIndex() == -1) {
                dataset.setClassIndex(dataset.numAttributes() - 1);
            }
        } catch (Exception e) {
            Log.d("error", e.toString());
        }
        return new Instances(dataset);
    }

    /**
     * Ngubah sensorData menjadi data yang dibutuhkan weka
     */
    public double[] getDataFromRaw() {
        double[] vals = new double[test.numAttributes()];
        int n = sensorData.size();
        vals[0] = 1;
        vals[1] = 1;
        vals[35] = 0; // nilai max x
        vals[36] = 0; // nilai max y
        vals[37] = 0; // nilai max z
        vals[38] = 0; // absol dev x
        vals[39] = 0; // absol dev y
        vals[40] = 0; // absol dev z
        vals[41] = 0; // std dev x
        vals[42] = 0; // std dev y
        vals[43] = 0; // std dev z
        vals[44] = 0; // resultan

        Axis avg = Axis.average(sensorData);
        for (int i = 0; i < n; i++) {
            Axis axis = sensorData.get(i);
            if (i < 10) {
                vals[2 + i] = axis.x;
                vals[12 + i] = axis.y;
                vals[22 + i] = axis.z;
            }
            if (axis.x > vals[35]) {
                vals[35] = axis.x;
            }
            if (axis.y > vals[36]) {
                vals[36] = axis.y;
            }
            if (axis.z > vals[37]) {
                vals[37] = axis.z;
            }

            vals[38] += Math.abs(axis.x - avg.x); // absol dev x
            vals[39] += Math.abs(axis.y - avg.y); // absol dev y
            vals[40] += Math.abs(axis.z - avg.z); // absol dev z
            vals[41] += Math.pow(axis.x - avg.x, 2); // std dev x
            vals[42] += Math.pow(axis.y - avg.y, 2); // std dev y
            vals[43] += Math.pow(axis.z - avg.z, 2); // std dev z
        }
        vals[32] = avg.x;
        vals[33] = avg.y;
        vals[34] = avg.z;
        vals[38] /= n; // absol dev x
        vals[39] /= n; // absol dev y
        vals[40] /= n; // absol dev z
        vals[41] = Math.sqrt(vals[41]) / n; // std dev x
        vals[42] = Math.sqrt(vals[42]) / n; // std dev y
        vals[43] = Math.sqrt(vals[43]) / n; // std dev z
        vals[44] = Math.sqrt(avg.x * avg.x + avg.y * avg.y + avg.z * avg.z);

        return vals;
    }

    /**
     * Proses ngitung dengan data training dan dataset
     */
    public String bayes() {
        /* Init, buka file nya */
        if (!fileExist) {
            // trainFile = Uri.parse("dataset");
            // testFile = Uri.parse("template");
            train = fileToInstances(trainStream);
            test = fileToInstances(testStream);
            fileExist = true;
        }

        /* Data boleh lebih dari 10, tapi ga boleh kurang*/

        if (sensorData.size() < 10) {
            return "Data sensor masih kurang dari 10 (" + sensorData.size() + ")";
        }

        /* proses dulu sensor data nya menjadi data sesuai data set attribute
         * lengkap */


        try {
            double[] vals = getDataFromRaw();
            Log.d("vals", vals.toString());
            test.delete(0);
            test.add(new DenseInstance(1.0, vals));

            NaiveBayes naiveBayes = new NaiveBayes();
            naiveBayes.buildClassifier(train);

            // for evalutaion
            Evaluation eval = new Evaluation(train);
            eval.predictions();

            // for prediction
            label = naiveBayes.classifyInstance(test.instance(0));
            test.instance(0).setClassValue(label);

            Log.d("Prediction", "Class predicted : " + test.classAttribute().value((int) label));

        } catch (Exception e) {
            Log.d("error", e.toString());
        }

        return test.classAttribute().value((int) label);
    }
}
