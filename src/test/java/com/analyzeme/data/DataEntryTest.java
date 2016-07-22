package com.analyzeme.data;

import com.analyzeme.data.dataWithType.DataEntry;
import com.analyzeme.data.dataWithType.DataEntryType;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ilya on 7/15/16.
 */
public class DataEntryTest {

    @Test
    public void testCreationWithTypeSpecification() {
        Assert.assertEquals(DataEntryType.DOUBLE, new DataEntry(DataEntryType.DOUBLE, 10.).getType());
        Assert.assertEquals(DataEntryType.STRING, new DataEntry(DataEntryType.STRING, "some string").getType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreationWithTypeSpecificationInvalidType() {
       new DataEntry(DataEntryType.STRING, 10.);
    }
    @Test
    public void testCreationFromObject() {
        Assert.assertEquals(DataEntryType.DOUBLE, new DataEntry(10.).getType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreationFromObjectForInvalidType() {
        new DataEntry(100);//ints are not allowed
    }

    @Test
    public void testCreationFromString() {
        Assert.assertEquals(DataEntryType.DOUBLE, DataEntry.fromString("42.42").getType());
        Assert.assertEquals(DataEntryType.TIME, DataEntry.fromString("10:30").getType());
    }
}
