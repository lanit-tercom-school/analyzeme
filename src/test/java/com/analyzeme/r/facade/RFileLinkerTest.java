package com.analyzeme.r.facade;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by lagroffe on 26.03.2016 21:07
 */

//TODO: add more tests
public class RFileLinkerTest {

	@Test
	public void testParse() {
		try {
			ArrayList<String> result = new ArrayList<String>();
			result.add("MyData.json");
			result.add("MyJson.excel");
			ArrayList<String> parsed = RFileLinker.parseForTests("here are some code \n {} : \n some other \n x_from_file.json_ \n y_from__repo__MyData.json__ \n y <- c(x_from__repo__MyJson.excel__)");
			assertTrue("RFileLinker doesn't work properly", result.get(0).equals(parsed.get(0)) && result.get(1).equals(parsed.get(1)));
		} catch (Exception e) {
			fail("RFileLinker doesn't work properly");
		}

	}
}
