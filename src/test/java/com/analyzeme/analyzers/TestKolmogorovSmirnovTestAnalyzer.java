package com.analyzeme.analyzers;

import com.analyzeme.analyzers.result.ScalarResult;
import com.analyzeme.data.dataset.DataEntry;
import com.analyzeme.data.dataset.DataEntryType;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertTrue;
/**
 * Created by Alexander on 05.07.2016.
 */
public class TestKolmogorovSmirnovTestAnalyzer {
    @Test
    public void simpleTest() throws Exception {
        //for this data sets Smirnov Statistic should equal to approx. 0.66 (valuable info for debugging)
        IAnalyzer ks = AnalyzerFactory.getAnalyzer("Kolmogorov Smirnov Test");
        double[] a = {17, 22, 3, 5, 15, 2, 0, 7, 13, 97, 66, 14};
        List<DataEntry> first = new ArrayList<DataEntry>(a.length);
        for (double d : a) first.add(new DataEntry(DataEntryType.DOUBLE, d));
        double[] b = {47, 30, 2, 15, 1, 21, 25, 7, 44, 29, 33, 11, 6, 15};
        List<DataEntry> second = new ArrayList<DataEntry>(b.length);
        for (double d : b) second.add(new DataEntry(DataEntryType.DOUBLE, d));
        Map<String, List<DataEntry>> input = new HashMap();
        input.put("x", first);
        input.put("y", second);

        assertTrue(((ScalarResult)ks.analyze(input)).getValue().getBooleanValue());
    }

}
