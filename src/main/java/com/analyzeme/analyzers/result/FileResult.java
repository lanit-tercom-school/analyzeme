package com.analyzeme.analyzers.result;

import com.analyzeme.data.Data;
import com.analyzeme.data.DataArray;

import java.util.HashMap;
import java.util.Iterator;
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
                tempMap.put(entry.getKey(), entry.getValue().get(i));
            }
            Data<T> d = new Data<T>(tempMap);
            temp.addData(d);
        }
        return temp.toPointJson();
    }

    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof FileResult) {
            FileResult that = (FileResult) other;
            result = that.getValue().equals(this.getValue());
        }
        return result;
    }
}
