package com.analyzeme.filestorageconfiguration;

import org.json.simple.JSONObject;

/**
 * Created by Кирилл Зубов on 7/7/2016.
 */
public interface IFileStorageConf {

    String getName();

    void setName(final String name);

    void setActiveFlag(final boolean activeFlag);

    boolean isActive();

    void assignment(final IFileStorageConf FirebaseConfiguration);

    JSONObject toJSONObject();

}
