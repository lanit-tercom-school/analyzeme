package com.analyzeme.repository;

//TODO: synchronized - ?

import com.analyzeme.data.DataSet;
import com.analyzeme.repository.filerepository.FileInfo;
import com.analyzeme.repository.filerepository.FileRepository;
import com.analyzeme.repository.projects.ProjectInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lagroffe on 22.02.2016 23:03
 */

public class UsersRepository {
    private static List<UserInfo> users;
    //unique id of a new user - users.size()+1 (complete user deletion is not planned)

    static synchronized void deleteForTests() {
        users = null;
    }
    /**
     * ctor should be private - call only from checkInitializationAndCreate()
     */
    private UsersRepository() {
        users = new ArrayList<UserInfo>();
    }

    /**
     * checks if the object of class exists
     * if yes, return this
     * if no, creates and return it
     *
     * @return unique object of repository
     */
    public static synchronized void checkInitializationAndCreate() {
        if (users == null) {
            new UsersRepository();
        }
    }

    /**
     * checks if the object of class exists
     * if yes, return this
     * if no, return null
     *
     * @return existing unique object of repository or null
     */
    public static synchronized void checkInitialization() throws IllegalStateException {
        if (users == null) {
            throw new IllegalStateException("UsersRepository does not exist");
        }
    }


    /**
     * add new User into repository
     *
     * @param data contains login, email, password  (IN THIS ORDER)
     * @return id in repository or throws Exception
     * @throws Exception
     */
    public static synchronized String newItem(final String[] data) throws Exception {
        if(users == null) {
            throw new IllegalStateException("UsersRepository does not exist");
        }
        if(data.length != 3) {
            throw new IllegalArgumentException("UsersRepository newItem(): wrong parameters");
        }
        for (String str : data) {
            if (str == null || str.equals("")) {
                throw new IllegalArgumentException("UsersRepository newItem(): empty argument");
            }
        }
        int id = users.size() + 1;
        UserInfo newUser = new UserInfo(data[0], id, data[1], data[2]);
        users.add(newUser);
        return Integer.toString(id);
    }

    /**
     * returns UserInfo if the unique id is given
     *
     * @param id - user id in repository
     * @throws Exception
     */
    public static synchronized UserInfo findUser(final int id) throws Exception {
        if(users == null) {
            throw new IllegalStateException("UsersRepository does not exist");
        }
        if (id <= 0 || id > users.size()) {
            throw new ArrayIndexOutOfBoundsException("UsersRepository findUser(int): incorrect userId");
        }
        return users.get(id - 1);
    }

    /**
     * return UserInfo if the login is given
     *
     * @param username
     * @throws Exception
     */
    public static synchronized UserInfo findUser(final String username) throws Exception {
        if(users == null) {
            throw new IllegalStateException("UsersRepository does not exist");
        }
        if (username == null || username.equals("")) {
            throw new IllegalArgumentException("UsersRepository findUser(String): empty userId");
        }
        for (UserInfo info : users) {
            if (info.getLogin().equals(username)) {
                return info;
            }
        }
        throw new IllegalArgumentException("UsersRepository findUser(String): user does not exist");
    }

    /**
     * creates new project for a user
     *
     * @param username
     * @param projectName
     * @return project id
     * @throws Exception
     */
    public static synchronized String newProject(final String username, final String projectName) throws Exception {
        if(users == null) {
            throw new IllegalStateException("UsersRepository does not exist");
        }
        if (username == null || username.equals("") || projectName == null || projectName.equals("")) {
            throw new IllegalArgumentException("UsersRepository newProject(String): incorrect username or projectName");
        }
        return findUser(username).getProjects().createProject(projectName);
    }

    /**
     * creates new project for a user
     *
     * @param userId
     * @param projectName
     * @return project id
     * @throws Exception
     */
    public static synchronized String newProject(final int userId, final String projectName) throws Exception {
        if(users == null) {
            throw new IllegalStateException("UsersRepository does not exist");
        }
        if (userId <= 0 || projectName == null || projectName.equals("")) {
            throw new IllegalArgumentException("UsersRepository newProject(int): incorrect userId or projectName");
        }
        return findUser(userId).getProjects().createProject(projectName);
    }

    /**
     * return all names of users in repository
     *
     * @return list of names or null if repository is empty
     */
    public static synchronized List<String> getAllIds() {
        if(users == null) {
            throw new IllegalStateException("UsersRepository does not exist");
        }
        ArrayList<String> names = new ArrayList<String>();
        for (UserInfo info : users) {
            names.add(Integer.toString(info.getId()));
        }
        return names;
    }

    /**
     * checks if the requirements given in params are met
     * if so, returns fileinfo from filerepository
     *
     * @param referenceName - fileId in repository
     * @param params        - userId, projectId
     * @return
     */
    public static synchronized FileInfo findByReferenceName(final String referenceName, final String[] params) throws Exception {
        if(users == null) {
            throw new IllegalStateException("UsersRepository does not exist");
        }
        DataSet set = getDataSetByReferenceName(referenceName, params);
        if (set == null) {
            throw new IllegalArgumentException("UsersRepository findByReferenceName(): impossible to find dataset");
        }
        return FileRepository.getRepo().findFileById(set.getFile().getToken());
    }

    /**
     * checks if the requirements given in params are met
     * if so, returns dataset
     *
     * @param referenceName - fileId in repository
     * @param params        - userId, projectId
     * @return
     */
    public static synchronized DataSet getDataSetByReferenceName(final String referenceName, final String[] params) throws Exception {
        if(users == null) {
            throw new IllegalStateException("UsersRepository does not exist");
        }
        if (referenceName == null || referenceName.equals("")) {
            throw new IllegalArgumentException("UsersRepository findByReferenceName(): empty referenceName");
        }
        if(params.length != 2) {
            throw new IllegalArgumentException("UsersRepository findByReferenceName(): wrong params");
        }
        for (String str : params) {
            if (str == null || str.equals("")) {
                throw new IllegalArgumentException("UsersRepository findByReferenceName(): empty projectId or userId");
            }
        }
        int userId = Integer.parseInt(params[0]);
        UserInfo user = findUser(userId);
        String projectId = params[1];
        ProjectInfo project = user.getProjects().findProjectById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("UsersRepository findByReferenceName(): project does not exist");
        }
        return project.getDataSetByReferenceName(referenceName);
    }

}
