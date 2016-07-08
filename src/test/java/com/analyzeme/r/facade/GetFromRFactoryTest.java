package com.analyzeme.r.facade;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by lagroffe on 05.07.2016 12:19
 */
public class GetFromRFactoryTest {
     /*
    @Test
    public void testFactoryDefault() {
        GetFromR<String> link = GetFromRFactory.getLinkToR(TypeOfReturnValue.STRING);
        assertTrue(link instanceof DefaultFromR);
    }

    @Test
    public void testFactoryDouble() {
        GetFromR<Double> link = GetFromRFactory.getLinkToR(TypeOfReturnValue.DOUBLE);
        assertTrue(link instanceof ScalarFromR);
    }

    @Test
    public void testFactoryPoint() {
        GetFromR<Point> link = GetFromRFactory.getLinkToR(TypeOfReturnValue.POINT);
        assertTrue(link instanceof VectorFromR);
    }

    @Test
    public void testFactoryPoints() {
        GetFromR<List<Point>> link = GetFromRFactory.getLinkToR(TypeOfReturnValue.POINTS);
        assertTrue(link instanceof FileFromR);
    }

    @Test(expected = NullPointerException.class)
    public void testFactoryNull() throws Exception {
        GetFromRFactory.getLinkToR((TypeOfReturnValue)null);
    } */
}
