package com.analyzeme.repository.storage.serialization;


import com.analyzeme.data.dataWithType.DataWithTypeArray;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for serialization DataEntryArray
 */
public class DataArrayModel {
    private List<DataModel> vectors;

    public DataArrayModel() {
    }

    public DataArrayModel(List<DataModel> vectors) {
        this.vectors = vectors;
    }

    public List<DataModel> getVectors() {
        return vectors;
    }

    public void setVectors(List<DataModel> vectors) {
        this.vectors = vectors;
    }

    /**
     * Deserialize DataArrayModel to DataArray
     *
     * @return resulted DataArray
     */
    public DataWithTypeArray deserialize() {
        return new DataWithTypeArray(vectors.stream().map(DataModel::deserialize).
                collect(Collectors.toCollection(ArrayList::new))
        );
    }
}
