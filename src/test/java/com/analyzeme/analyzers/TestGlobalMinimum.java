package com.analyzeme.analyzers;

import com.analyzeme.analyzers.result.ScalarResult;
import com.analyzeme.data.dataWithType.DataEntry;
import com.analyzeme.data.dataWithType.DataEntryType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertTrue;

public class TestGlobalMinimum {
	private final double e = 0.001;

	@Test
	public void testGlobalMin() throws Exception {
		double x;
		List<DataEntry> y = new ArrayList<DataEntry>();
		for (int i = 0; i < 1001; i++) {
			x = 4 - i * 0.008;
			y.add(new DataEntry(DataEntryType.DOUBLE, Math.sin(x * x)));
		}
		Map<String, List<DataEntry>> data = new HashMap();
		data.put("y", y);
		IAnalyzer tester = AnalyzerFactory.getAnalyzer("Global Minimum");
		assertTrue("Global minimum of y=sin(x^2)is wrong",
				Math.abs(-1 - ((ScalarResult)tester.analyze(data)).getValue().getDoubleValue()) < e);
	}
}

