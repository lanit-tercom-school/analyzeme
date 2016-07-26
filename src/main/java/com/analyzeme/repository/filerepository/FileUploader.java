package com.analyzeme.repository.filerepository;

import com.analyzeme.data.dataset.DataSet;
import com.analyzeme.data.resolvers.sourceinfo.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * Created by lagroffe on 20.05.2016 21:27
 */
public class FileUploader {

    private static ISourceInfo createSourceInfo(
            final String nameInRepo, TypeOfFile typeOfFile) throws Exception {
        ISourceInfo source;
        switch (typeOfFile) {
            case SIMPLE_JSON:
            case CSV:
            case EXCEL:
                source = new DataRepositoryInfo(nameInRepo, typeOfFile);
                break;
            case SCRIPT:
                source = new ScriptInRepositoryInfo(nameInRepo);
                break;
            default:
                throw new IllegalArgumentException(
                        "FileUploader createSourceInfo(): wrong TypeOfFile");

        }
        return source;
    }

    public static DataSet upload(MultipartFile file,
                                 String filename, String referenceName,
                                 TypeOfFile typeOfFile) throws Exception {
        if (filename == null || filename.equals("") ||
                referenceName == null || referenceName.equals("") ||
                file == null) {
            throw new IllegalArgumentException(
                    "FileUploader upload(MultipartFile): empty argument");
        }
        if (typeOfFile == null) {
            throw new IllegalArgumentException("null type of file");
        }
        String nameInRepo = FileRepository.getRepo().persist(file, filename);
        if (nameInRepo == null || nameInRepo.equals("")) {
            throw new IllegalArgumentException(
                    "FileUploader upload(MultipartFile): impossible to add file in FileRepository");
        }
        ISourceInfo source = createSourceInfo(nameInRepo, typeOfFile);
        DataSet result = new DataSet(referenceName, source);
        for (String key : source.getKeys()) {
            result.addField(key);
        }
        //next line should be deprecated when real referenceName is ready
        result.setReferenceName(nameInRepo);
        return result;
    }

    public static DataSet upload(String file,
                                 String filename, String referenceName,
                                 TypeOfFile typeOfFile) throws Exception {
        if (filename == null || filename.equals("") ||
                referenceName == null || referenceName.equals("") ||
                file == null || file.equals("")) {
            throw new IllegalArgumentException(
                    "FileUploader upload(String): empty argument");
        }
        if (typeOfFile == null) {
            throw new IllegalArgumentException("null type of file");
        }
        String nameInRepo = FileRepository.getRepo().persist(file, filename);
        if (nameInRepo == null || nameInRepo.equals("")) {
            throw new IllegalArgumentException(
                    "FileUploader upload(String): impossible to add file in FileRepository");
        }
        ISourceInfo source = createSourceInfo(nameInRepo, typeOfFile);
        DataSet result = new DataSet(referenceName, source);
        for (String key : source.getKeys()) {
            result.addField(key);
        }
        //next line should be deprecated when real referenceName is ready
        result.setReferenceName(nameInRepo);
        return result;
    }

    public static DataSet upload(InputStream part,
                                 String filename, String referenceName,
                                 TypeOfFile typeOfFile) throws Exception {
        if (filename == null || filename.equals("") ||
                referenceName == null || referenceName.equals("") || part == null) {
            throw new IllegalArgumentException(
                    "FileUploader upload(ByteArrayInputStream): empty argument");
        }
        if (typeOfFile == null) {
            throw new IllegalArgumentException("null type of file");
        }
        String nameInRepo = FileRepository.getRepo().persist(part, filename);
        if (nameInRepo == null || nameInRepo.equals("")) {
            throw new IllegalArgumentException(
                    "FileUploader upload(ByteArrayInputStream): impossible to add file in FileRepository");
        }
        ISourceInfo source = createSourceInfo(nameInRepo, typeOfFile);
        DataSet result = new DataSet(referenceName, source);
        for (String key : source.getKeys()) {
            result.addField(key);
        }
        //next line should be deprecated when real referenceName is ready
        result.setReferenceName(nameInRepo);
        return result;
    }
}
