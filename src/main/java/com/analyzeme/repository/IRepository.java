package com.analyzeme.repository;

import java.util.List;

/**
 * Created by lagroffe on 22.02.2016 22:27
 */

//TODO: change to generic
public interface IRepository {

    /**
     * ctor should be private - call only from checkInitializationAndCreate()
     */

    /**
     * checks if the object of class exists
     * if yes, return it
     * if no, creates and return it
     *
     * @return unique object of repository
     */
    IRepository checkInitializationAndCreate();


    /**
     * checks if the object of class exists
     * if yes, return it
     * if no, return null
     *
     * @return existing unique object of repository or null
     */
    IRepository checkInitialization();


    /**
     * add new item into repository
     * (item from repository name, e.g. for UsersRepository use this function to add new User)
     * specification for parameters in data should be specified in javadoc for specific repositories
     *
     * @param data contains information about an object to create, e.g. name, id, sth else
     * @return identificator in repository or throws Exception
     * @throws Exception
     */
    String newItem(final String[] data) throws Exception;

    /**
     * return all ids of items in repository
     * (item from repository name, e.g. for UsersRepository use this function to get ids of users)
     *
     * @return array of names or null if repository is empty
     */
    List<String> getAllIds();
}
