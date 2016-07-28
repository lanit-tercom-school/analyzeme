package com.analyzeme.data.resolvers.sourceinfo;

import com.analyzeme.data.dataset.DataArray;
import com.analyzeme.repository.filerepository.FileRepository;

import java.io.ByteArrayInputStream;

public class ScriptInRepositoryInfo implements ISourceInfo {
    private final String uniqueNameInRepository;

    public ScriptInRepositoryInfo(
            final String uniqueNameInRepository) throws IllegalArgumentException {
        if (uniqueNameInRepository == null
                || uniqueNameInRepository.equals("")) {
            throw new IllegalArgumentException(
                    "ScriptInRepositoryInfo ctor: wrong uniqueNameInRepository");
        }
        this.uniqueNameInRepository = uniqueNameInRepository;
    }

    public ByteArrayInputStream getFileData() throws Exception {
        return FileRepository.getRepo()
                .getFileByID(uniqueNameInRepository);
    }

    public String getToken() throws Exception {
        return uniqueNameInRepository;
    }

    public DataArray getDataArray() throws Exception {
        throw new IllegalStateException(
                "ScriptInRepositoryInfo getDataArray(): method is not supported");
    }
}
