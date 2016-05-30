package com.analyzeme.repository.filerepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * FileInfo object contains binary file data and
 * all information about file
 */

@JsonAutoDetect
public class FileInfo {
    @JsonProperty("nameForUser")
    private String nameForUser;
    @JsonProperty("uniqueName")
    private String uniqueName;
    @JsonProperty("uploadingDate")
    private final Date uploadingDate;
    ByteArrayInputStream data;
    @JsonProperty("isActive")
    private boolean isActive = true;

    /**
     * function to convert input stream to bytes array
     *
     * @throws IOException
     */
    private synchronized static byte[] getBytesFromInputStream(InputStream is) throws IOException {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buffer = new byte[0xFFFF];

            for (int len; (len = is.read(buffer)) != -1; )
                os.write(buffer, 0, len);

            os.flush();

            return os.toByteArray();
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * @param nameForUser - name given by user
     * @param uniqueName  - name generated for repository
     * @param data        - input stream with byte info (to extract from Part use method getInputStream)
     */
    FileInfo(final String nameForUser, final String uniqueName, final InputStream data) throws IOException {
        if (nameForUser == null || nameForUser.equals("")) {
            throw new IllegalArgumentException("FileInfo ctor: wrong nameForUser");
        }
        this.setNameForUser(nameForUser);
        if (uniqueName == null || uniqueName.equals("")) {
            throw new IllegalArgumentException("FileInfo ctor: wrong uniqueName");
        }
        this.uniqueName = uniqueName;
        if(data == null) {
            throw new IllegalArgumentException("FileInfo ctor: data is null");
        }
        this.setData(new ByteArrayInputStream(getBytesFromInputStream(data)));
        //default ctor fills Date with current info (number of milliseconds since the Unix epoch (first moment of 1970) in the UTC time zone)
        uploadingDate = new Date();
    }

    public String getNameForUser() {
        return nameForUser;
    }

    public void setNameForUser(String nameForUser) throws IOException {
        if (nameForUser == null || nameForUser.equals("")) {
            throw new IllegalArgumentException("FileInfo: impossible to set this name for user");
        }
        this.nameForUser = nameForUser;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public Date getUploadingDate() {
        return uploadingDate;
    }

    public ByteArrayInputStream getData() throws IOException {
        data.reset();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int len;
        while ((len = data.read(buffer)) > -1) {
            baos.write(buffer, 0, len);
        }
        baos.flush();

        data = new ByteArrayInputStream(baos.toByteArray());
        return new ByteArrayInputStream(baos.toByteArray());
    }

    public void setData(ByteArrayInputStream data) throws IOException {
        if (data == null) {
            throw new IllegalArgumentException("FileInfo: you should not change data to null");
        }
        this.data = data;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
