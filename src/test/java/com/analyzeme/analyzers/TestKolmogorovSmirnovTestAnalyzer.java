package com.analyzeme.analyzers;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertTrue;
/**
 * Created by Alexander on 05.07.2016.
 */
public class TestKolmogorovSmirnovTestAnalyzer {
    @Test
    public void simpleTest() {
        IAnalyzer ks = AnalyzerFactory.getAnalyzer("KolmogorovSmirnovTest");
        double[] a = {17, 22, 3, 5, 15, 2, 0, 7, 13, 97, 66, 14};
        List<Double> fst = new ArrayList<Double>(a.length);
        for (double d : a) fst.add(d);
        double[] b = {47, 30, 2, 15, 1, 21, 25, 7, 44, 29, 33, 11, 6, 15};
        List<Double> snd = new ArrayList<Double>(b.length);
        for (double d : b) snd.add(d);
        List<List<Double>> input = new ArrayList<List<Double>>(2);
        input.add(fst);
        input.add(snd);

        assertTrue((Boolean) ks.analyze(input).getValue());
    }

}
