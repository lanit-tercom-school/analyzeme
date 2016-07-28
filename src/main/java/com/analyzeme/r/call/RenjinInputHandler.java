package com.analyzeme.r.call;

import com.analyzeme.data.dataset.DataSet;
import com.analyzeme.data.dataset.DataEntry;
import com.analyzeme.data.dataset.DataEntryType;
import com.analyzeme.scripts.InputType;

import javax.script.ScriptEngine;
import java.util.List;
import java.util.Map;

public class RenjinInputHandler {

    public static void insertData(final ScriptEngine engine, final List<DataSet> dataFiles, final InputType input)
            throws Exception {
        for (DataSet set : dataFiles) {
            for (String field : set.getFields()) {
                //TODO: refactor to work with other types, not only double
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

    public static void insertData(final ScriptEngine engine, final Map<String, List<DataEntry>> data, final InputType input) throws Exception {
        for (Map.Entry<String, List<DataEntry>> entry : data.entrySet()) {
            //TODO: refactor to work with other types, not only double \
            if (entry.getValue().size() != 0  && entry.getValue().get(0).getType() == DataEntryType.DOUBLE) {
                double[] array = new double[entry.getValue().size()];
                for (int i = 0; i < entry.getValue().size(); i++) {
                    array[i] = entry.getValue().get(i).getDoubleValue();
                }
                engine.put(entry.getKey(), array);
            }
        }
    }
}
