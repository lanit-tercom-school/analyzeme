package com.analyzeme.repository;

import com.analyzeme.repository.projects.ProjectsRepository;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

//toString in getters is used for copying

/**
 * Created by lagroffe on 22.02.2016 23:12
 */

@JsonAutoDetect
public class UserInfo {
    @JsonProperty("username")
    private String login;
    @JsonProperty("userId")
    private final int id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
    private final ProjectsRepository projects;

    UserInfo(final String login, final int id,
             final String email, final String password)
            throws Exception {
        if (login == null || login.equals("")) {
            throw new IllegalArgumentException("UserInfo ctor: empty login");
        }
        this.login = login;
        if (id <= 0) {
            throw new IllegalArgumentException("UserInfo ctor: wrong id");
        }
        this.id = id;
        if (email == null || email.equals("")) {
            throw new IllegalArgumentException("UserInfo ctor: empty email");
        }
        this.email = email;
        if (password == null || password.equals("")) {
            throw new IllegalArgumentException("UserInfo ctor: empty password");
        }
        this.password = password;
        this.projects = new ProjectsRepository();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) throws IllegalArgumentException {
        if (login == null || login.equals("")) {
            throw new IllegalArgumentException(
                    "UserInfo setLogin(): empty login");
        }
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws IllegalArgumentException {
        if (email == null || email.equals("")) {
            throw new IllegalArgumentException(
                    "UserInfo setEmail(): empty email");
        }
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws IllegalArgumentException {
        if (password == null || password.equals("")) {
            throw new IllegalArgumentException(
                    "UserInfo setPassword(): empty password");
        }
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public ProjectsRepository getProjects() {
        return projects;
    }
}
