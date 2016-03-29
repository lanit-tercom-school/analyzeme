package com.analyzeme.R.facade;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertTrue;

/**
 * Created by lagroffe on 26.03.2016 21:07
 */
public class RParserTest {

	@Test
	public void testParse() {
		try {
			ArrayList<String> result = new ArrayList<String>();
			result.add("MyData.json");
			result.add("MyJson.excel");
			ArrayList<String> parsed = RParser.parseForTests("here are some code \n {} : \n some other \n x_from_file.json_ \n y_from__MyData.json__ \n y <- c(x_from__MyJson.excel__)");
			assertTrue("RParser doesn't work properly", result.get(0).equals(parsed.get(0)) && result.get(1).equals(parsed.get(1)));
		} catch (Exception e) {
			assertTrue("RParser doesn't work properly", false);
		}

	}
}
