package com.analyzeme.data.resolvers.sourceinfo;

import com.analyzeme.parsers.JsonParser;
import com.analyzeme.repository.filerepository.FileRepository;

import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lagroffe on 30.03.2016 13:33
 */
public class JsonPointFileInRepositoryInfo implements ISourceInfo {
    private String uniqueNameInRepository;

    public JsonPointFileInRepositoryInfo(final String uniqueNameInRepository) throws IllegalArgumentException {
        if (uniqueNameInRepository == null || uniqueNameInRepository.equals(""))
            throw new IllegalArgumentException("JsonPointFileInRepositoryInfo ctor: wrong uniqueNameInRepository");
        this.uniqueNameInRepository = uniqueNameInRepository;
    }

    public ByteArrayInputStream getFileData() throws Exception {
        return FileRepository.getRepo().getFileByID(uniqueNameInRepository);
    }

    public String getToken() throws Exception {
        return uniqueNameInRepository;
    }

    public Set<String> getKeys() throws Exception {
        ByteArrayInputStream stream = FileRepository.getRepo().getFileByID(uniqueNameInRepository);
        JsonParser parser = new JsonParser();
        Map<String, List<Double>> data = parser.parse(stream);
        return data.keySet();
    }


    public List<Double> getByField(final String fieldName) throws Exception {
        ByteArrayInputStream stream = FileRepository.getRepo().getFileByID(uniqueNameInRepository);
        //Set<String> names = new HashSet<String>();
        //names.add("x");
        //names.add("y");
        //JsonParser parser = new JsonParser();
        //Map<String, List<Double>> data = parser.getPointsFromJsonWithNames(stream, names);
        JsonParser parser = new JsonParser();
        Map<String, List<Double>> data = parser.parse(stream);
        return data.get(fieldName);
    }
}
