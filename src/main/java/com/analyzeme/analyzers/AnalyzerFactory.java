package com.analyzeme.analyzers;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ольга on 10.04.2016.
 */
public class AnalyzerFactory {

	private static Map<String, IAnalyzer> analyzers = new HashMap<String, IAnalyzer>();

	public static IAnalyzer getAnalyzer(String analyzerName) {
		if (!analyzers.containsKey(analyzerName)) {
			createAnalyzer(analyzerName);
		}
		return analyzers.get(analyzerName);
	}


	//todo add all new analyzers here
	private static void createAnalyzer(String name) {
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
		throw new IllegalArgumentException("Incorrect name");
	}
}
