package com.analyzeme.r.call;

import com.analyzeme.data.dataset.DataArray;
import com.analyzeme.data.dataset.DataEntry;
import com.analyzeme.data.dataset.DataEntryType;
import com.analyzeme.data.dataset.DataSet;
import com.analyzeme.scripts.InputType;

import javax.script.ScriptEngine;
import java.util.List;
import java.util.Map;

public class RenjinInputHandler {
    private static void insertDoubleVectors(final ScriptEngine engine, final List<DataSet> dataFiles) throws Exception {
        for (DataSet set : dataFiles) {
            for (String field : set.getFields()) {
                List<DataEntry> value = set.getByField(field);
                double[] v1 = new double[value.size()];
                int i = 0;
                for (DataEntry v : value) {
                    v1[i++] = v.getDoubleValue();
                }
                engine.put(field + "_from__repo__" +
                        set.getReferenceName() + "__", v1);
            }
        }
    }

    private static void insertTimeSeriesOneDim(final ScriptEngine engine, final List<DataSet> dataFiles) {
         //TODO
    }

    static void insertData(final ScriptEngine engine, final List<DataSet> dataFiles, final InputType input)
            throws Exception {
        if (input == InputType.VECTORS) {
            insertDoubleVectors(engine, dataFiles);
        } else if (input == InputType.TIME_SERIES_ONE_DIM) {
            insertTimeSeriesOneDim(engine, dataFiles);
        }
    }

    private static void insertDoubleVectors(final ScriptEngine engine, final DataArray data) {
        for (Map.Entry<String, List<DataEntry>> entry : data.getMap().entrySet()) {
            if (entry.getValue().size() != 0 && entry.getValue().get(0).getType() == DataEntryType.DOUBLE) {
                double[] array = new double[entry.getValue().size()];
                for (int i = 0; i < entry.getValue().size(); i++) {
                    array[i] = entry.getValue().get(i).getDoubleValue();
                }
                engine.put(entry.getKey(), array);
            }
        }
    }

    //now it is supposed here that all series have one period only
    private static void insertTimeSeriesOneDim(final ScriptEngine engine, final DataArray data) throws Exception {
        List<DataEntry> time = data.getDate();
        int freq = TimeUtils.getFrequency(time);
        List<Double> doubleList = data.getDouble();
        double[] doubleArray = new double[doubleList.size()];
        for (int i = 0; i < doubleList.size(); i++) {
            doubleArray[i] = doubleList.get(i);
        }
        engine.put("my_double_vector", doubleArray);
        engine.eval("ts_0 <- ts(my_double_vector, frequency = " + freq + ")");
    }

    static void insertData(final ScriptEngine engine, final DataArray data, final InputType input) throws Exception {
        if (input == InputType.VECTORS) {
            insertDoubleVectors(engine, data);
        } else if (input == InputType.TIME_SERIES_ONE_DIM) {
            insertTimeSeriesOneDim(engine, data);
        }
    }
}
