package com.analyzeme.parsers;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

/**
 * Created by ilya on 7/14/16.
 */
public class DateTimeUtilsTest {
    /*
     * Time
     */
    @Test()
    public void testIsValidTime() {
        Assert.assertTrue(DateAndTimeUtils.isValidTime("10:20:30"));
    }

    @Test()
    public void testIsValidTimeForValidFormatInvalidTime() {
        Assert.assertFalse(DateAndTimeUtils.isValidTime("30:20"));
    }

    @Test()
    public void testIsValidTimeForInvalidFormat() {
        Assert.assertFalse(DateAndTimeUtils.isValidTime("10:e3"));
    }

    @Test()
    public void testParseTime() {
        Assert.assertEquals(LocalTime.of(10, 20, 30), DateAndTimeUtils.parseTime("10:20:30"));
    }

    /*
     * Date
     */
    @Test()
    public void testIsForValidDate() {
        Assert.assertTrue(DateAndTimeUtils.isValidDate("10-10-2010"));
    }

    @Test()
    public void testIsValidDateForValidFormatInvalidDate() {
        Assert.assertFalse(DateAndTimeUtils.isValidTime("30-2-2010"));
    }

    @Test()
    public void testIsValidDateForInvalidFormat() {
        Assert.assertFalse(DateAndTimeUtils.isValidTime("30.2-2010"));
    }

    @Test()
    public void testParseDate() {
        Assert.assertEquals(LocalDate.of(2010, Month.OCTOBER, 10), DateAndTimeUtils.parseDate("10-10-2010"));
    }

    /*
    * DateTime
    */
    @Test()
    public void testIsForValidDateTime() {
        Assert.assertTrue(DateAndTimeUtils.isValidDateTime("10-10-2010 10:10"));
    }

    @Test()
    public void testIsValidDateTimeForValidFormatInvalidDateTime() {
        Assert.assertFalse(DateAndTimeUtils.isValidTime("30-2-2010 40:40:12"));
    }

    @Test()
    public void testIsValidDatetimeForInvalidFormat() {
        Assert.assertFalse(DateAndTimeUtils.isValidTime("30.2-2010 12:03"));
    }

    @Test()
    public void testParseDateTime() {
        Assert.assertEquals(LocalDateTime.of(2010, Month.OCTOBER, 10, 10, 10),
                DateAndTimeUtils.parseDateTime("10-10-2010 10:10"));
    }


}
