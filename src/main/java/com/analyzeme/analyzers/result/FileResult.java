package com.analyzeme.analyzers.result;

import com.analyzeme.data.Data;
import com.analyzeme.data.DataArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Use this type of result for groups of vectors with names
 */

public class FileResult<T> implements IResult<Map<String, List<T>>> {
    private final Map<String, List<T>> result;

    public FileResult(final Map<String, List<T>> result) {
        this.result = result;
    }

    public Map<String, List<T>> getValue() {
        return result;
    }

    public String toJson() {
        if (result == null || result.get(0) == null) {
            return null;
        }
        DataArray<T> temp = new DataArray<T>();
        for(int i = 0; i < result.get(0).size(); i++) {
            Map<String, T> tempMap = new HashMap<String, T>();
            for(Map.Entry<String, List<T>> entry : result.entrySet()) {
                tempMap.put(entry.getKey(), entry.getValue().get(i));
            }
            temp.addData(new Data<T>(tempMap));
        }
        return temp.toPointJson();
    }
}
