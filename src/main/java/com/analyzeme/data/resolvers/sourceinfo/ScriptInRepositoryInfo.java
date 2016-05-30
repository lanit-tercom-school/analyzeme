package com.analyzeme.data.resolvers.sourceinfo;

import com.analyzeme.repository.filerepository.FileRepository;

import java.io.ByteArrayInputStream;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Set;

/**
 * Created by lagroffe on 28.05.2016 14:28
 */
public class ScriptInRepositoryInfo implements ISourceInfo {
    private String uniqueNameInRepository;

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


    public List<Double> getByField(final String fieldName) throws Exception {
        throw new IllegalStateException("ScriptInRepositoryInfo getByField(): method is not supported");
    }

    public Set<String> getKeys() throws Exception {
        throw new IllegalStateException("ScriptInRepositoryInfo getKeys(): method is not supported");
    }
}
