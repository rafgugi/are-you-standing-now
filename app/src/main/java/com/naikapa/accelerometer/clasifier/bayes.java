package com.naikapa.accelerometer.clasifier;

import android.util.Log;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

/**
 * Created by Hendro E. Prabowo on 24/03/2017.
 */

public class bayes{

    private  Instances dataset, train;
    private double label;

    public String bayes(Instances test) {
        String currentPath = System.getProperty("user.dir");
        String csvFile = currentPath + "\\database\\dataset.arff";

        try {
            ConverterUtils.DataSource source = new ConverterUtils.DataSource(csvFile);
            dataset = source.getDataSet();

            if (dataset.classIndex() == -1){
                dataset.setClassIndex(dataset.numAttributes() - 1);
            }
        }catch (Exception e){
            Log.d("error", e.toString());
        }

        train = new Instances(dataset);

        try{
            NaiveBayes naiveBayes = new NaiveBayes();
            naiveBayes.buildClassifier(train);

            // for evalutaion
            Evaluation eval = new Evaluation(train);
            eval.predictions();

            // for prediction
            label = naiveBayes.classifyInstance(test.instance(0));
            test.instance(0).setClassValue(label);

            Log.d("Prediction", "Class predicted : " + test.classAttribute().value((int)label));

        }catch (Exception e){
            Log.d("error", e.toString());
        }

        return test.classAttribute().value((int)label);
    }
}
