package com.analyzeme.data.dataWithType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lagroffe on 25.07.2016 14:11
 */
public class ListHandler {

    public static List<Double> toDoubleList(List<DataEntry> data) {
        List<Double> res = new ArrayList<>();
        for (DataEntry entry : data) {
            res.add(entry.getDoubleValue());
        }
        return res;
    }
}
