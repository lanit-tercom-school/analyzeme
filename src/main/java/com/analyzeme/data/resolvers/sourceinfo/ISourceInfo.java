package com.analyzeme.data.resolvers.sourceinfo;

import com.analyzeme.data.dataset.DataEntry;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Set;

public interface ISourceInfo {
    ByteArrayInputStream getFileData() throws Exception;

    String getToken() throws Exception;

    Set<String> getKeys() throws Exception;

    List<DataEntry> getByField(final String fieldName) throws Exception;
}
