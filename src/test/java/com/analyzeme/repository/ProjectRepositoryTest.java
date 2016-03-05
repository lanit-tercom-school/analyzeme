package com.analyzeme.repository;


import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Created by lagroffe on 05.03.2016 10:42
 */

public class ProjectRepositoryTest {
	ProjectsRepository repo;

	@Before
	public void Before() {
		repo = new ProjectsRepository();
	}

	@Test
	public void testNewProjectName() throws Exception {
		String newProjectName = "myProject";
		String id = repo.createProject(newProjectName);
		assertTrue("ProjectId wasn't created correctly", id.equals("project"));
	}

	@Test
	public void testRecentlyAdded() throws Exception {
		String newProjectName = "myProject";
		String id = repo.createProject(newProjectName);
		ProjectInfo info = repo.findProjectById(id);
		assertTrue("Project wasn't returned correctly", newProjectName.equals(info.projectName));
	}

	@Test
	public void testEmptyRepository() throws Exception {
		try {
			ProjectInfo info = repo.findProjectById("sth");
			assertTrue("Empty repository doesn't work correctly", info==null);
		}
		catch(Exception e) {
			assertTrue("Empty repository doesn't work correctly", true);
		}
	}
}
