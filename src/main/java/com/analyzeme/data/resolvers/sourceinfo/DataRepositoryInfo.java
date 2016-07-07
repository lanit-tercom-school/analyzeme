package com.analyzeme.data.resolvers.sourceinfo;

import com.analyzeme.data.DataArray;
import com.analyzeme.parsers.ParserFactory;
import com.analyzeme.repository.filerepository.FileRepository;
import com.analyzeme.repository.filerepository.TypeOfFile;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Set;

/**
 * Created by ilya on 7/6/16.
 */
public class DataRepositoryInfo implements ISourceInfo {
    private final String uniqueNameInRepository;
    private final TypeOfFile fileType;

    public DataRepositoryInfo(final String uniqueNameInRepository, TypeOfFile fileType) throws IllegalArgumentException {
        this.fileType = fileType;
        this.uniqueNameInRepository = uniqueNameInRepository;
        if (uniqueNameInRepository == null || uniqueNameInRepository.equals("")) {
            throw new IllegalArgumentException("DataRepositoryInfo error: wrong uniqueNameInRepository");
        }
    }

    public ByteArrayInputStream getFileData() throws Exception {
        DataArray<Double> dataArray = getDataArray();
        return new ByteArrayInputStream(dataArray.toPointJson().getBytes());
    }

    public String getToken() throws Exception {
        return uniqueNameInRepository;
    }

    public Set<String> getKeys() throws Exception {
        DataArray<Double> dataArray = getDataArray();
        return dataArray.getData().get(0).getKeys();
    }

    public List<Double> getByField(String fieldName) throws Exception {
        DataArray<Double> dataArray = getDataArray();
        return dataArray.getByKey(fieldName);
    }

    private DataArray<Double> getDataArray() throws Exception {
        ByteArrayInputStream file = FileRepository.getRepo().getFileByID(uniqueNameInRepository);
        //        System.out.println(array);
        return ParserFactory.createParser(fileType).parse(file);
    }
}
