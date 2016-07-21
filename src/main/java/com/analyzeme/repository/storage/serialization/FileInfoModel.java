package com.analyzeme.repository.storage.serialization;


/**
 * Class for storing infoabout file
 */
public class FileInfoModel {
    private String realName;
    private String token;

    public FileInfoModel() {

    }

    public FileInfoModel(String realName, String token) {
        this.realName = realName;
        this.token = token;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return realName.concat(": ").concat(token);
    }
}
