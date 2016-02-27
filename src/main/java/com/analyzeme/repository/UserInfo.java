package com.analyzeme.repository;

import java.io.IOException;

/**
 * Created by lagroffe on 22.02.2016 23:12
 */
public class UserInfo {
	public String login;
	public int id;
	public String email;
	public String password;
	public ProjectsRepository projects;

	UserInfo(final String login, final int id, final String email, final String password) throws IOException {
		if (login == null || login.equals("")) throw new IOException();
		this.login = login;
		if (id == 0) throw new IOException();
		this.id = id;
		if (email == null || email.equals("")) throw new IOException();
		this.email = email;
		if (password == null || password.equals("")) throw new IOException();
		this.password = password;
		this.projects = new ProjectsRepository();
	}
}
