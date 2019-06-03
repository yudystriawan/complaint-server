package com.yudystriawan.complaintserver.prediction;

import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.output.prediction.PlainText;
import weka.classifiers.meta.Bagging;
import weka.core.*;
import weka.core.converters.ConverterUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Classification {

    private final File model = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("bagging.model")).getFile());
    private final File trainFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("dataset_training.arff")).getFile());
    private final String modelPath = model.toString();
    private final String trainPath = trainFile.toString();
    private Instances testInstances;
    private String instance;
    private double percent;

    public Classification(String text) {
        Preprocessing p = new Preprocessing(text);
        try {
            Bagging bagging = (Bagging) SerializationHelper.read(modelPath);
            testInstances = customInstances(p.getText(), "Dinas Lingkungan Hidup Dan Kebersihan Kota Denpasar");

            if (testInstances.classIndex() == -1) {
                testInstances.setClassIndex(testInstances.numAttributes() - 1);
            }

            double classValue = bagging.classifyInstance(testInstances.lastInstance());
            instance = testInstances.classAttribute().value((int) classValue);

            evaluation(p.getText(), instance);


        } catch (Exception ex) {
            Logger.getLogger(Classification.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Instances customInstances(String text, String prediction) {
        FastVector fastVector = new FastVector();
        fastVector.addElement("Dinas Lingkungan Hidup Dan Kebersihan Kota Denpasar");
        fastVector.addElement("Dinas Pekerjaan Umum Dan Penataan Ruang Kota Denpasar");
        fastVector.addElement("Dinas Perhubungan Kota Denpasar");
        fastVector.addElement("PDAM Kota Denpasar");

        Attribute a = new Attribute("isi pengaduan", true);
        Attribute a1 = new Attribute("instansi tujuan", fastVector);

        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(a);
        attributes.add(a1);

        Instances testing = new Instances("testing", attributes, 0);

        double[] values = new double[testing.numAttributes()];
        values[0] = testing.attribute(0).addStringValue(text);
        values[1] = fastVector.indexOf(prediction);

        testing.add(new DenseInstance(1.0, values));

        return testing;
    }

    private void evaluation(String text, String instance) {

        try {
            Bagging bagging = (Bagging) SerializationHelper.read(modelPath);

            ConverterUtils.DataSource source = new ConverterUtils.DataSource(trainPath);
            Instances trainInstance = source.getDataSet();
            if (trainInstance.classIndex() == -1) {
                trainInstance.setClassIndex(trainInstance.numAttributes() - 1);
            }

            Instances newInstances = customInstances(text, instance);

            if (newInstances.classIndex() == -1) {
                newInstances.setClassIndex(newInstances.numAttributes() - 1);
            }

            Evaluation evaluation = new Evaluation(trainInstance);
            StringBuffer stringBuffer = new StringBuffer();
            PlainText plainText = new PlainText();

            plainText.setBuffer(stringBuffer);
            plainText.setHeader(trainInstance);

            evaluation.evaluateModel(bagging, newInstances, plainText);
            String s = stringBuffer.toString();
            s = s.substring(s.length() - 9);
            s = s.trim();
            percent = Double.parseDouble(s);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getPercent() {
        return percent;
    }

    public String getInstance() {
        return instance;
    }
}
