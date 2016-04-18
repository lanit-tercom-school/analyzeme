package com.analyzeme.data;

import java.io.ByteArrayInputStream;

/**
 * Created by lagroffe on 30.03.2016 13:25
 */

public interface ISourceInfo {
	ByteArrayInputStream getFileData() throws Exception;

	String getToken() throws Exception;
}
