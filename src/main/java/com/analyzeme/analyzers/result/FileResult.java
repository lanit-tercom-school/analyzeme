package com.analyzeme.analyzers.result;

import com.analyzeme.data.dataWithType.DataEntry;
import com.analyzeme.data.dataWithType.DataWithType;
import com.analyzeme.data.dataWithType.DataWithTypeArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static java.lang.Math.abs;

/**
 * Use this type of result for groups of vectors with names
 */

public class FileResult implements IResult<Map<String, List<DataEntry>>> {
    private static final Logger LOGGER;
    private static final double EPS_FOR_DOUBLE = 0.0001;
    private final Map<String, List<DataEntry>> result;
    private final JsonWriter writer = new JsonWriter();

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.analyzers.result.FileResult");
    }

    public FileResult(final Map<String, List<DataEntry>> result) {
        this.result = result;
    }

    public Map<String, List<DataEntry>> getValue() {
        return result;
    }

    public String toJson() {
        LOGGER.debug("toJson(): method started");
        if (result == null) {
            return null;
        }
        DataWithTypeArray temp = new DataWithTypeArray();
        Iterator<String> it = result.keySet().iterator();
        int length = 0;
        if (it.hasNext()) {
            String tempS = it.next();
            try {
                return writer.toJson(result);
            } catch (Exception e) {
                LOGGER.info("toJson(): impossible to use custom writer");
            }
            length = result.get(tempS).size();
        }
        for (int i = 0; i < length; i++) {
            Map<String, DataEntry> tempMap = new HashMap<String, DataEntry>();
            for (Map.Entry<String, List<DataEntry>> entry : result.entrySet()) {
                tempMap.put(entry.getKey(),
                        entry.getValue().get(i));
            }
            DataWithType d = new DataWithType(tempMap);
            temp.addData(d);
        }
        LOGGER.debug("toJson(): DataArray created");
        return temp.toPointJson();
    }

    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof FileResult) {
            FileResult that = (FileResult) other;
            result = that.getValue().keySet().equals(
                    this.getValue().keySet());
            if (result) {
                Set<String> names = this.getValue().keySet();
                for (String name : names) {
                    List<DataEntry> tempThis = this.getValue().get(name);
                    List<DataEntry> tempThat = that.getValue().get(name);
                    if (tempThis.size() != tempThat.size()) {
                        return false;
                    }
                    for (int i = 0; i < tempThis.size(); i++) {
                        DataEntry objThis = tempThis.get(i);
                        DataEntry objThat = tempThat.get(i);
                        //TODO: rewrite here to work not with double only
                        if (abs(objThis.getDoubleValue() - objThat.getDoubleValue())
                                > EPS_FOR_DOUBLE) {
                            return false;
                        }
                    }
                }
            }
        }
        return result;
    }
}
