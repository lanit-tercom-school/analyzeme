package com.analyzeme.data.resolvers.sourceinfo;

import com.analyzeme.repository.filerepository.FileRepository;

import java.io.ByteArrayInputStream;

/**
 * Created by lagroffe on 30.03.2016 13:33
 */
public class JsonPointFileInRepositoryInfo implements ISourceInfo {
    private String uniqueNameInRepository;

    public JsonPointFileInRepositoryInfo(final String uniqueNameInRepository) throws IllegalArgumentException {
        if (uniqueNameInRepository == null || uniqueNameInRepository.equals(""))
            throw new IllegalArgumentException("JsonPointFileInRepositoryInfo ctor: wrong uniqueNameInRepository");
        this.uniqueNameInRepository = uniqueNameInRepository;
    }

    public ByteArrayInputStream getFileData() throws Exception {
        return FileRepository.getRepo().getFileByID(uniqueNameInRepository);
    }

    public String getToken() throws Exception {
        return uniqueNameInRepository;
    }
}
