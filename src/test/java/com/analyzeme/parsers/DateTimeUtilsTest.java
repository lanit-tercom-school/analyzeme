package com.analyzeme.parsers;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ilya on 7/14/16.
 */
public class DateTimeUtilsTest {
    @Test()
    public void testIsForValidDates() {
        Assert.assertTrue(DateAndTimeUtils.isValidTime("10:20"));
        Assert.assertTrue(DateAndTimeUtils.isValidTime("10:20:30"));
    }

    @Test()
    public void testIsValidDateValidFormatButInvalidDate() {
        Assert.assertFalse(DateAndTimeUtils.isValidTime("30:20"));
    }

    @Test()
    public void testIsValidDateInvalidDateFormat() {
        Assert.assertFalse(DateAndTimeUtils.isValidTime("10:e3"));
    }
}
