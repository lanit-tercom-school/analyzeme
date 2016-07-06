package com.analyzeme.data.resolvers.sourceinfo;

import com.analyzeme.data.DataArray;
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
    private final String uniqueNameInRepository;

    public CsvFileInRepositoryInfo(final String uniqueNameInRepository) throws IllegalArgumentException, IOException {
        if (uniqueNameInRepository == null || uniqueNameInRepository.equals("")) {
            throw new IllegalArgumentException("CsvFileInRepositoryInfo ctor: wrong uniqueNameInRepository");
        }
        this.uniqueNameInRepository = uniqueNameInRepository;

    }

    public ByteArrayInputStream getFileData() throws Exception {
        ByteArrayInputStream f = FileRepository.getRepo().getFileByID(uniqueNameInRepository);
        DataArray<Double> file = CsvParserForDoubleData.parse(f);
        return new ByteArrayInputStream(file.toPointJson().getBytes());
    }

    public Set<String> getKeys() throws Exception {
        ByteArrayInputStream f = FileRepository.getRepo().getFileByID(uniqueNameInRepository);
        DataArray<Double> file = CsvParserForDoubleData.parse(f);
        return file.getData().get(0).getKeys();
    }

    public String getToken() throws Exception {
        return uniqueNameInRepository;
    }


    public List<Double> getByField(final String fieldName) throws Exception {
        if (fieldName == null || fieldName.equals("")) {
            throw new IllegalArgumentException("CsvFileInRepositoryInfo getByField(): fieldName cannot be null or empty");
        }
        ByteArrayInputStream f = FileRepository.getRepo().getFileByID(uniqueNameInRepository);
        DataArray<Double> file = CsvParserForDoubleData.parse(f);
        return file.getByKey(fieldName);
    }
}
