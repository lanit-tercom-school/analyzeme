package com.analyzeme.repository.storage.serialization;


import com.analyzeme.data.dataWithType.DataEntry;
import com.analyzeme.data.dataWithType.DataEntryType;

/**
 * Class for serialization DataEntry
 */
public class DataEntryModel {
    private DataEntry dataEntry;

    public DataEntryModel() {
    }

    public DataEntryModel(DataEntry dataEntry) {
        this.dataEntry = new DataEntry(dataEntry.getType(), dataEntry.getValue());
    }


    public String getValue() {
        return dataEntry.toString();
    }

    public void setValue(String value) {
        dataEntry = DataEntry.fromString(value);
    }

    public DataEntryType getType() {
        return dataEntry.getType();
    }

    /**
     * Deserialize DataEntryModel to DataEntry
     *
     * @return resulted DataEntry
     */
    public DataEntry deserialize() {
        return new DataEntry(dataEntry.getType(), dataEntry.getValue());
    }

    public void setType(DataEntryType type) {
    }
}
