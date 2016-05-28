package com.analyzeme.data.resolvers.sourceinfo;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by lagroffe on 30.03.2016 13:25
 */

public interface ISourceInfo {
    ByteArrayInputStream getFileData() throws Exception;

    String getToken() throws Exception;

    List<Double> getByField(final String fieldName) throws Exception;
}
