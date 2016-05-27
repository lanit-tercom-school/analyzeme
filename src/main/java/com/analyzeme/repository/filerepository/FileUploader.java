package com.analyzeme.repository.filerepository;

import com.analyzeme.data.DataSet;
import com.analyzeme.data.resolvers.sourceinfo.CsvFileInRepositoryInfo;
import com.analyzeme.data.resolvers.sourceinfo.ISourceInfo;
import com.analyzeme.data.resolvers.sourceinfo.JsonPointFileInRepositoryInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lagroffe on 20.05.2016 21:27
 */
public class FileUploader {
    private static ISourceInfo createSourceInfo(final String nameInRepo, TypeOfFile typeOfFile) throws IOException {
        ISourceInfo source = null;
        switch (typeOfFile) {
            case SIMPLE_JSON: {
                source = new JsonPointFileInRepositoryInfo(nameInRepo);
                break;
            }
            case CSV: {
                source = new CsvFileInRepositoryInfo(nameInRepo);
                break;
            }
        }
        return source;
    }

    public static DataSet upload(MultipartFile file, String filename, String referenceName, TypeOfFile typeOfFile) throws Exception {
        if (filename == null || filename.equals("") || file == null) {
            throw new IllegalArgumentException("FileUploader upload(MultipartFile): empty argument");
        }
        if(typeOfFile == null) {
            throw new NullPointerException("null type of file");
        }
        String nameInRepo = FileRepository.getRepo().persist(file, filename);
        if (nameInRepo == null || nameInRepo.equals("")) {
            throw new NullPointerException("FileUploader upload(MultipartFile): impossible to add file in FileRepository");
        }
        ISourceInfo source = createSourceInfo(nameInRepo, typeOfFile);
        DataSet result = new DataSet(referenceName, source);
        result.addField("x");
        result.addField("y");
        //next line should be deprecated when real referenceName is ready
        result.setReferenceName(nameInRepo);
        return result;
    }

    public static DataSet upload(String file, String filename, String referenceName, TypeOfFile typeOfFile) throws Exception {
        if (filename == null || filename.equals("") || file == null || file.equals("")) {
            throw new IllegalArgumentException("FileUploader upload(String): empty argument");
        }
        if(typeOfFile == null) {
            throw new NullPointerException("null type of file");
        }
        String nameInRepo = FileRepository.getRepo().persist(file, filename);
        if (nameInRepo == null || nameInRepo.equals("")) {
            throw new IllegalArgumentException("FileUploader upload(String): impossible to add file in FileRepository");
        }
        ISourceInfo source = createSourceInfo(nameInRepo, typeOfFile);
        DataSet result = new DataSet(referenceName, source);
        result.addField("x");
        result.addField("y");
        //next line should be deprecated when real referenceName is ready
        result.setReferenceName(nameInRepo);
        return result;
    }

    public static DataSet upload(InputStream part, String filename, String referenceName, TypeOfFile typeOfFile) throws Exception {
        if (filename == null || filename.equals("") || part == null) {
            throw new IllegalArgumentException("FileUploader upload(ByteArrayInputStream): empty argument");
        }
        if(typeOfFile == null) {
            throw new NullPointerException("null type of file");
        }
        String nameInRepo = FileRepository.getRepo().persist(part, filename);
        if (nameInRepo == null || nameInRepo.equals("")) {
            throw new IllegalArgumentException("FileUploader upload(ByteArrayInputStream): impossible to add file in FileRepository");
        }
        ISourceInfo source = createSourceInfo(nameInRepo, typeOfFile);
        DataSet result = new DataSet(referenceName, source);
        result.addField("x");
        result.addField("y");
        //next line should be deprecated when real referenceName is ready
        result.setReferenceName(nameInRepo);
        return result;
    }
}
