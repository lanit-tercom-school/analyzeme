package com.analyzeme.analyzers.result;

import com.analyzeme.data.dataWithType.DataEntry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Use this type of result for anonymous vectors of some kind of objects
 */

public class ColumnResult implements IResult<List<DataEntry>> {
    private static final Logger LOGGER;
    private final JsonWriter writer = new JsonWriter();

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.analyzers.result.ColumnResult");
    }

    private final ObjectMapper mapper = new ObjectMapper();
    private final List<DataEntry> result;

    public ColumnResult(final List<DataEntry> result) {
        this.result = result;
    }

    public List<DataEntry> getValue() {
        return result;
    }

    public String toJson() throws JsonProcessingException {
        LOGGER.debug("toJson(): method started");
        if (result == null || result.isEmpty()) {
            return null;
        }
        try {
            return writer.toJson(result);
        } catch (Exception e) {
            LOGGER.info("toJson(): impossible to use custom writer");
            return mapper.writeValueAsString(result);
        }
    }

    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof ColumnResult) {
            ColumnResult that = (ColumnResult) other;
            if (that.getValue().size()
                    != this.getValue().size()) {
                return result;
            }
            for (int i = 0; i < that.getValue().size(); i++) {
                if (!that.getValue().get(i).equals(
                        this.getValue().get(i))) {
                    return result;
                }
            }
            result = true;
        }
        return result;
    }
}
