package com.analyzeme.repository.storage.serialization;


import com.analyzeme.data.dataWithType.DataWithType;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class for serialization Data
 */
public class DataModel {
    private Map<String, DataEntryModel> entries;

    public DataModel() {
    }

    public DataModel(Map<String, DataEntryModel> entries) {
        this.entries = new HashMap<>(entries);
    }

    public Map<String, DataEntryModel> getEntries() {
        return entries;
    }

    public void setEntries(Map<String, DataEntryModel> entries) {
        this.entries = entries;
    }

    /**
     * Deserialize DataEntryModel to DataEntry
     *
     * @return resulted DataEntry
     */
    public DataWithType deserialize() {
        return new DataWithType(entries.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().deserialize())
                ));
    }
}
