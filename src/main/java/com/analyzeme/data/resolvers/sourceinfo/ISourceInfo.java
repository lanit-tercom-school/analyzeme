package com.analyzeme.data.resolvers.sourceinfo;

import com.analyzeme.data.dataset.DataArray;

import java.io.ByteArrayInputStream;

public interface ISourceInfo {
    ByteArrayInputStream getFileData() throws Exception;

    String getToken() throws Exception;

    DataArray getDataArray() throws Exception;
}
