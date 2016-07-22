package com.analyzeme.filestorageconfiguration;

import org.json.simple.JSONObject;

/**
 * Created by Кирилл Зубов on 7/7/2016.
 */
public interface IFileStorageConf {

    /**
     * get field name
     *
     * @return name of FileStorageConfiguration
     */
    String getName();

    /**
     * set field name
     *
     * @param name what will set as field name
     */
    void setName(final String name);

    /**
     * set field activeFlag
     *
     * @param activeFlag what will set as field activeFlag
     */
    void setActiveFlag(final boolean activeFlag);

    /**
     * get field flag
     *
     * @return value of field flag
     */
    boolean isActive();

    /**
     * assignment value of param to this object
     * this object will become equal to FConf
     *
     * @param FConf will be assignmented to this IFileStorageConf
     */

    void assignment(final IFileStorageConf FConf);

    /**
     * @return this this object as JSONObject
     */
    JSONObject toJSONObject();

}
