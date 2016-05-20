package com.analyzeme.repository.filerepository;

import com.analyzeme.data.DataSet;
import com.analyzeme.data.resolvers.sourceinfo.ISourceInfo;
import com.analyzeme.data.resolvers.sourceinfo.JsonPointFileInRepositoryInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

/**
 * Created by lagroffe on 20.05.2016 21:27
 */
public class FileUploader {
    public static DataSet upload(MultipartFile file, String filename, String referenceName) throws Exception {
        if (filename == null || filename.equals("") || file == null) {
            throw new IllegalArgumentException("FileUploader upload(MultipartFile): empty argument");
        }
        String nameInRepo = FileRepository.getRepo().persist(file, filename);
        if (nameInRepo == null || nameInRepo.equals("")) {
            throw new NullPointerException("FileUploader upload(MultipartFile): impossible to add file in FileRepository");
        }
        ISourceInfo source = new JsonPointFileInRepositoryInfo(nameInRepo);
        DataSet result = new DataSet(referenceName, source);
        //next line should be deprecated when real referenceName is ready
        result.setReferenceName(nameInRepo);
        return result;
    }

    public static DataSet upload(String file, String filename, String referenceName) throws Exception {
        if (filename == null || filename.equals("") || file == null) {
            throw new IllegalArgumentException("FileUploader upload(String): empty argument");
        }
        String nameInRepo = FileRepository.getRepo().persist(file, filename);
        if (nameInRepo == null || nameInRepo.equals("")) {
            throw new IllegalArgumentException("FileUploader upload(String): impossible to add file in FileRepository");
        }
        ISourceInfo source = new JsonPointFileInRepositoryInfo(nameInRepo);
        DataSet result = new DataSet(referenceName, source);
        //next line should be deprecated when real referenceName is ready
        result.setReferenceName(nameInRepo);
        return result;
    }

    public static DataSet upload(ByteArrayInputStream part, String filename, String referenceName) throws Exception {
        if (filename == null || filename.equals("") || part == null) {
            throw new IllegalArgumentException("FileUploader upload(ByteArrayInputStream): empty argument");
        }
        String nameInRepo = FileRepository.getRepo().persist(part, filename);
        if (nameInRepo == null || nameInRepo.equals("")) {
            throw new IllegalArgumentException("FileUploader upload(ByteArrayInputStream): impossible to add file in FileRepository");
        }
        ISourceInfo source = new JsonPointFileInRepositoryInfo(nameInRepo);
        DataSet result = new DataSet(referenceName, source);
        //next line should be deprecated when real referenceName is ready
        result.setReferenceName(nameInRepo);
        return result;
    }
}
