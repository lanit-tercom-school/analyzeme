package com.analyzeme.data;

import com.analyzeme.analyze.Point;
import com.analyzeme.data.resolvers.sourceinfo.ISourceInfo;
import com.analyzeme.parsers.JsonParser;
import com.analyzeme.streamreader.StreamToString;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lagroffe on 30.03.2016 13:17
 */

public class DataSet {
    //unique id in the project
    private BigInteger idInProject;
    //name that user gave this data in the process of uploading
    private String referenceName;
    //first String is field alias (use this in script), second - field name: example <"t", "time">
    private final Map<String, String> fields;
    //specific structure that gives a possibility to get binary data from any source
    private final ISourceInfo file;

    /**
     * ctor
     *
     * @param referenceName - name that user gave this data in the process of uploading
     * @param file          - specific structure that gives a possibility to get binary data from any source
     * @throws Exception
     */
    public DataSet(final String referenceName, final ISourceInfo file) throws Exception {
        if (referenceName == null || referenceName.equals("") || file == null) {
            throw new IllegalArgumentException("DataSet ctor: empty argument cannot be used");
        }
        this.idInProject = BigInteger.ZERO;
        this.referenceName = referenceName;
        this.file = file;
        fields = new HashMap<String, String>();
    }

    /**
     * @return binary data as if source was a json file
     * @throws Exception
     */
    public ByteArrayInputStream getData() throws Exception {
        return file.getFileData();
    }


    //TODO: implement for Point from analyzers

    /**
     * @return array of Point (analyzeme.analyze.Point) from this source
     * @throws Exception
     */
    public Point[] getPoints() throws Exception {
        ByteArrayInputStream data = getData();
        String DataString = StreamToString.convertStream(data);

        InputStream is = new ByteArrayInputStream(DataString.getBytes());

        JsonParser jsonParser;
        jsonParser = new JsonParser();

        Point[] Data = jsonParser.getPointsFromPointJson(is);
        return Data;
    }

    /**
     * @return name that user gave this data in the process of uploading
     */
    public String getReferenceName() {
        return referenceName;
    }

    /**
     * @param referenceName - referenceName
     * @throws Exception
     */
    public void setReferenceName(final String referenceName) throws Exception {
        if (referenceName == null || referenceName.equals("")) {
            throw new IllegalArgumentException("DataSet setReferenceName(): empty argument cannot be used");
        }
        this.referenceName = referenceName;
    }

    /**
     * @param field - field alias (use in script) (if you call this it will be used as a field name too)
     * @throws Exception
     */
    public void addField(final String field) throws Exception {
        if (field == null || field.equals("")) {
            throw new IllegalArgumentException("DataSet addField(): empty argument cannot be used");
        }
        fields.put(field, field);
    }

    /**
     * @param field     - field alias (use in script)
     * @param fieldName - field name (sth like)
     * @throws Exception
     */
    public void addField(final String field, final String fieldName) throws Exception {
        if (field == null || field.equals("") || fieldName == null || fieldName.equals("")) {
            throw new IllegalArgumentException("DataSet addField(): empty argument cannot be used");
        }
        fields.put(field, fieldName);
    }


    /**
     * @return Map of fields: first String is field alias (use this in script), second - field name: example <"t", "time">
     */
    public Map<String, String> getFieldsWithNames() {
        return fields;
    }

    /**
     * @return set of field alias (use in script)
     */
    public Set<String> getFields() {
        return fields.keySet();
    }

    public List<Double>  getByField(final String field) throws Exception {
        return file.getByField(field);
    }

    /**
     * @return - unique id in project
     */
    public BigInteger getIdInProject() {
        return idInProject;
    }

    /**
     * @param id - unique id in project
     */
    public void setIdInProject(BigInteger id) throws Exception {
        if (id == null) {
            throw new IllegalArgumentException("DataSet setIdInProject: empty id");
        }
        idInProject = id;
    }

    /**
     * @return specific structure that gives a possibility to get binary data from any source
     */
    public ISourceInfo getFile() {
        return file;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof DataSet)) return false;
        try {
            DataSet o = (DataSet) other;
            if ((o.getReferenceName().equals(referenceName)) && (o.getFile().getClass().equals(file.getClass())) && (o.getFile().getToken().equals(file.getToken())))
                return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
