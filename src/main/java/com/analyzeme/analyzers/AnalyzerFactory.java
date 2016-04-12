package com.analyzeme.analyzers;

import org.renjin.invoke.codegen.ArgumentException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ольга on 10.04.2016.
 */
public class AnalyzerFactory {

    private Map<String, IAnalyzer> analyzers = new HashMap<String, IAnalyzer>();

    public IAnalyzer getAnalyzer(String analyzerName) throws ArgumentException {
        if (!analyzers.containsKey(analyzerName)) {
            createAnalyzer(analyzerName);
        }
        return analyzers.get(analyzerName);
    }


    //todo add all new analyzers here
    private void createAnalyzer(String name) throws ArgumentException {
//        if(name.equals("GlobalMax")) {
//            //analyzers.put(name,new Analyzer());
//            return;
//        }
//        if(name.equals("GlobalMin")) {
//            //analyzers.put(name,new Analyzer());
//            return;
//        }
//        if(name.equals("SampleMeanOfMultiplication")) {
//            //analyzers.put(name,new Analyzer());
//            return;
//        }
//        if(name.equals("SampleCorrelationMoment")) {
//            //analyzers.put(name,new Analyzer());
//            return;
//        }
//        if(name.equals("PPMCorrelationCoefficient")) {
//            //analyzers.put(name,new Analyzer());
//            return;
//        }
        if (name.equals("LinearRegression")) {
            analyzers.put(name, new LinearRegressionAnalyzer());
            return;
        }
//        if(name.equals("TimeSeriesAnalysis")) {
//            //analyzers.put(name,new Analyzer());
//            return;
//        }
//        if(name.equals("MovingAverage")) {
//            //analyzers.put(name,new Analyzer());
//            return;
//        }
//        if(name.equals("ExponentialSmoothing")) {
//            //analyzers.put(name,new Analyzer());
//            return;
//        }
        throw new ArgumentException("Incorrect name");
    }
}
