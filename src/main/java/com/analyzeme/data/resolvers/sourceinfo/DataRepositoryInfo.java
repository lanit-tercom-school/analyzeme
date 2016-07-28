package com.analyzeme.data.resolvers.sourceinfo;

import com.analyzeme.data.dataset.DataArray;
import com.analyzeme.parsers.ParserFactory;
import com.analyzeme.repository.filerepository.FileRepository;
import com.analyzeme.repository.filerepository.TypeOfFile;

import java.io.ByteArrayInputStream;

public class DataRepositoryInfo implements ISourceInfo {
    private final String uniqueNameInRepository;
    private final TypeOfFile fileType;

    public DataRepositoryInfo(final String uniqueNameInRepository,
                              TypeOfFile fileType) throws IllegalArgumentException {
        this.fileType = fileType;
        this.uniqueNameInRepository = uniqueNameInRepository;
        if (uniqueNameInRepository == null
                || uniqueNameInRepository.equals("")) {
            throw new IllegalArgumentException(
                    "DataRepositoryInfo error: wrong uniqueNameInRepository");
        }
    }

    public ByteArrayInputStream getFileData() throws Exception {
        DataArray dataArray = getDataArray();
        return new ByteArrayInputStream(
                dataArray.toPointJson().getBytes());
    }

    public String getToken() throws Exception {
        return uniqueNameInRepository;
    }

    public DataArray getDataArray() throws Exception {
        ByteArrayInputStream file = FileRepository
                .getRepo().getFileByID(uniqueNameInRepository);
        return ParserFactory
                .createParser(fileType).parse(file);
    }
}
