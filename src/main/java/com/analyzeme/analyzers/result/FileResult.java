package com.analyzeme.analyzers.result;

import com.analyzeme.data.Data;
import com.analyzeme.data.DataArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static java.lang.Math.abs;

/**
 * Use this type of result for groups of vectors with names
 */

public class FileResult<T> implements IResult<Map<String, List<T>>> {
    private static final Logger LOGGER;
    private static final double EPS_FOR_DOUBLE = 0.0001;
    private final Map<String, List<T>> result;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.analyzers.result.FileResult");
    }

    public FileResult(final Map<String, List<T>> result) {
        this.result = result;
    }

    public Map<String, List<T>> getValue() {
        return result;
    }

    public String toJson() {
        LOGGER.debug("toJson(): method started");
        if (result == null) {
            return null;
        }
        DataArray<T> temp = new DataArray<T>();
        Iterator<String> it = result.keySet().iterator();
        int length = 0;
        if (it.hasNext()) {
            length = result.get(it.next()).size();
        }
        for (int i = 0; i < length; i++) {
            Map<String, T> tempMap = new HashMap<String, T>();
            for (Map.Entry<String, List<T>> entry : result.entrySet()) {
                tempMap.put(entry.getKey(),
                        entry.getValue().get(i));
            }
            Data<T> d = new Data<T>(tempMap);
            temp.addData(d);
        }
        LOGGER.debug("toJson(): DataArray created");
        return temp.toPointJson();
    }

    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof FileResult) {
            FileResult<T> that = (FileResult) other;
            result = that.getValue().keySet().equals(
                    this.getValue().keySet());
            if (result) {
                Set<String> names = this.getValue().keySet();
                for (String name : names) {
                    List<T> tempThis = this.getValue().get(name);
                    List<T> tempThat = that.getValue().get(name);
                    if (tempThis.size() != tempThat.size()) {
                        return false;
                    }
                    for (int i = 0; i < tempThis.size(); i++) {
                        T objThis = tempThis.get(i);
                        T objThat = tempThat.get(i);
                        //TODO: rewrite here to work not with double only
                        if (abs((Double) objThis - (Double) objThat)
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
