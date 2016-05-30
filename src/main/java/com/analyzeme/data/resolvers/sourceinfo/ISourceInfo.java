package com.analyzeme.data.resolvers.sourceinfo;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Set;

/**
 * Created by lagroffe on 30.03.2016 13:25
 */

public interface ISourceInfo {
    ByteArrayInputStream getFileData() throws Exception;

    String getToken() throws Exception;

    Set<String> getKeys() throws Exception;

    List<Double> getByField(final String fieldName) throws Exception;
}
