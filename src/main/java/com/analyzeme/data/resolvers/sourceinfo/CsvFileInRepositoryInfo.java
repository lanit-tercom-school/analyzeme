package com.analyzeme.data.resolvers.sourceinfo;

import com.analyzeme.data.DoubleDataArray;
import com.analyzeme.parsers.CsvParserForDoubleData;
import com.analyzeme.repository.filerepository.FileRepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by lagroffe on 27.05.2016 20:00
 */
public class CsvFileInRepositoryInfo implements ISourceInfo {
    private String uniqueNameInRepository;
    private DoubleDataArray file;

    public CsvFileInRepositoryInfo(final String uniqueNameInRepository) throws IllegalArgumentException, IOException {
        if (uniqueNameInRepository == null || uniqueNameInRepository.equals("")) {
            throw new IllegalArgumentException("CsvFileInRepositoryInfo ctor: wrong uniqueNameInRepository");
        }
        this.uniqueNameInRepository = uniqueNameInRepository;
        ByteArrayInputStream f = FileRepository.getRepo().getFileByID(uniqueNameInRepository);
        file = CsvParserForDoubleData.parse(f);
    }

    public ByteArrayInputStream getFileData() throws Exception {
        return new ByteArrayInputStream(file.toPointJson().getBytes());
    }

    public Set<String> getKeys() {
        return file.getData().get(0).getKeys();
    }

    public String getToken() throws Exception {
        return uniqueNameInRepository;
    }


    public List<Double> getByField(final String fieldName) throws Exception {
        if (fieldName == null || fieldName.equals("")) {
            throw new IllegalArgumentException("CsvFileInRepositoryInfo getByField(): fieldName cannot be null or empty");
        }
        return file.getByKey(fieldName);
    }
}
