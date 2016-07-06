package com.analyzeme.data.resolvers.sourceinfo;

import com.analyzeme.data.DataArray;
import com.analyzeme.parsers.ExcelParser;
import com.analyzeme.repository.filerepository.FileRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by ilya on 7/5/16.
 */
public class ExcelFileInRepositoryInfo implements ISourceInfo {
    private final String uniqueNameInRepository;

    public ExcelFileInRepositoryInfo(final String uniqueNameInRepository) throws IllegalArgumentException, IOException,
            InvalidFormatException {
        if (uniqueNameInRepository == null || uniqueNameInRepository.equals("")) {
            throw new IllegalArgumentException("ExcelFileInRepositoryInfo error: wrong uniqueNameInRepository");
        }
        this.uniqueNameInRepository = uniqueNameInRepository;
    }

    public ByteArrayInputStream getFileData() throws Exception {
        ByteArrayInputStream f = FileRepository.getRepo().getFileByID(uniqueNameInRepository);
        DataArray<Double> dataArray = (new ExcelParser()).parse(f);
        return new ByteArrayInputStream(dataArray.toPointJson().getBytes());
    }

    public Set<String> getKeys() throws Exception {
        ByteArrayInputStream f = FileRepository.getRepo().getFileByID(uniqueNameInRepository);
        DataArray<Double> dataArray = (new ExcelParser()).parse(f);
        return dataArray.getData().get(0).getKeys();
    }

    public String getToken() throws Exception {
        return uniqueNameInRepository;
    }

    public List<Double> getByField(final String fieldName) throws Exception {
        if (fieldName == null || fieldName.equals("")) {
            throw new IllegalArgumentException("ExcelFileInRepositoryInfo getByField(): fieldName cannot be null or empty");
        }
        ByteArrayInputStream f = FileRepository.getRepo().getFileByID(uniqueNameInRepository);
        DataArray<Double> dataArray = (new ExcelParser()).parse(f);
        return dataArray.getByKey(fieldName);
    }
}
