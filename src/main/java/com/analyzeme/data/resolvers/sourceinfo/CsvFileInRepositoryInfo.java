package com.analyzeme.data.resolvers.sourceinfo;

import com.analyzeme.parsers.CsvParserForDoubleData;
import com.analyzeme.repository.filerepository.FileRepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by lagroffe on 27.05.2016 20:00
 */
public class CsvFileInRepositoryInfo implements ISourceInfo {
    private String uniqueNameInRepository;
    private String file;

    public CsvFileInRepositoryInfo(final String uniqueNameInRepository) throws IllegalArgumentException, IOException {
        if (uniqueNameInRepository == null || uniqueNameInRepository.equals(""))
            throw new IllegalArgumentException("JsonPointFileInRepositoryInfo ctor: wrong uniqueNameInRepository");
        this.uniqueNameInRepository = uniqueNameInRepository;
        ByteArrayInputStream f = FileRepository.getRepo().getFileByID(uniqueNameInRepository);
        file = CsvParserForDoubleData.parse(f).toPointJson();
    }

    public ByteArrayInputStream getFileData() throws Exception {
        return new ByteArrayInputStream(file.getBytes());
    }

    public String getToken() throws Exception {
        return uniqueNameInRepository;
    }
}
