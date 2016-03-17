package com.analyzeme.repository;

import java.io.IOException;

//toString in getters is used for copying

/**
 * Created by lagroffe on 22.02.2016 23:12
 */

public class UserInfo {
	private String login;
	private int id;
	private String email;
	private String password;
	private ProjectsRepository projects;

	UserInfo(final String login, final int id, final String email, final String password) throws IOException {
		if (login == null || login.equals("")) throw new IOException();
		this.setLogin(login);
		if (id == 0) throw new IOException();
		this.id = id;
		if (email == null || email.equals("")) throw new IOException();
		this.setEmail(email);
		if (password == null || password.equals("")) throw new IOException();
		this.setPassword(password);
		this.projects = new ProjectsRepository();
	}

	public String getLogin() {
		return login.toString();
	}

	public void setLogin(String login) throws IOException {
		if (login == null || login.equals("")) throw new IOException();
		this.login = login;
	}

	public String getEmail() {
		return email.toString();
	}

	public void setEmail(String email) throws IOException {
		if (email == null || email.equals("")) throw new IOException();
		this.email = email;
	}

	public String getPassword() {
		return password.toString();
	}

	public void setPassword(String password) throws IOException {
		if (password == null || password.equals("")) throw new IOException();
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public ProjectsRepository getProjects() {
		return projects;
	}
}
