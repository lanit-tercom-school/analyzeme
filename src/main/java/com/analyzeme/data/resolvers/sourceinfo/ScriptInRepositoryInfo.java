package com.analyzeme.data.resolvers.sourceinfo;

import com.analyzeme.data.dataset.DataEntry;
import com.analyzeme.repository.filerepository.FileRepository;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Set;

public class ScriptInRepositoryInfo implements ISourceInfo {
    private final String uniqueNameInRepository;

    public ScriptInRepositoryInfo(final String uniqueNameInRepository) throws IllegalArgumentException {
        if (uniqueNameInRepository == null || uniqueNameInRepository.equals("")) {
            throw new IllegalArgumentException("ScriptInRepositoryInfo ctor: wrong uniqueNameInRepository");
        }
        this.uniqueNameInRepository = uniqueNameInRepository;
    }

    public ByteArrayInputStream getFileData() throws Exception {
        return FileRepository.getRepo().getFileByID(uniqueNameInRepository);
    }

    public String getToken() throws Exception {
        return uniqueNameInRepository;
    }


    public List<DataEntry> getByField(final String fieldName) throws Exception {
        throw new IllegalStateException("ScriptInRepositoryInfo getByField(): method is not supported");
    }

    public Set<String> getKeys() throws Exception {
        throw new IllegalStateException("ScriptInRepositoryInfo getKeys(): method is not supported");
    }
}
