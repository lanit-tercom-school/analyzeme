package com.analyzeme.repository;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertTrue;

// TODO: rewrite to avoid using *-test functions
// TODO: tests for multithreading should be added (?)

/**
 * Created by lagroffe on 05.03.2016 10:42
 */

public class ProjectsRepositoryTest {
	ProjectsRepository repo;

	@Before
	public void Before() throws Exception {
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
		assertTrue("Project wasn't returned correctly", newProjectName.equals(info.getProjectName()));
	}

	@Test
	public void testDeleteRecentlyAddedDeactivationName() throws Exception {
		String newProjectName = "myProject";
		String id = repo.createProject(newProjectName);
		boolean result = repo.deleteProject(newProjectName);
		assertTrue("Impossible to delete existing project", result);
		ProjectInfo info = repo.findProjectById(id);
		assertTrue("Inaccurate deleting of the project", !info.isActive());
	}

	@Test
	public void testDeleteRecentlyAddedDeactivationId() throws Exception {
		String newProjectName = "myProject";
		String id = repo.createProject(newProjectName);
		boolean result = repo.deleteProjectById(id);
		assertTrue("Impossible to delete existing project", result);
		ProjectInfo info = repo.findProjectById(id);
		assertTrue("Inaccurate deleting of the project", !info.isActive());
	}

	@Test
	public void testDeleteRecentlyAddedCompletelyName() throws Exception {
		String newProjectName = "myProject";
		String id = repo.createProject(newProjectName);
		boolean result = repo.deleteProjectCompletely(newProjectName);
		assertTrue("Impossible to delete existing project", result);
		ProjectInfo info = repo.findProjectById(id);
		assertTrue("Inaccurate deleting of the project", info == null);
	}

	@Test
	public void testDeleteRecentlyAddedCompletelyId() throws Exception {
		String newProjectName = "myProject";
		String id = repo.createProject(newProjectName);
		boolean result = repo.deleteProjectCompletelyById(id);
		assertTrue("Impossible to delete existing project", result);
		ProjectInfo info = repo.findProjectById(id);
		assertTrue("Inaccurate deleting of the project", info == null);
	}

	@Test
	public void testEmptyRepository() throws Exception {
		try {
			ProjectInfo info = repo.findProjectById("sth");
			assertTrue("Empty repository doesn't work correctly (find)", info == null);
		} catch (Exception e) {
			assertTrue("Empty repository doesn't work correctly (find)", false);
		}
	}

	@Test
	public void testEmptyForDeleting() throws Exception {
		try {
			Boolean a = repo.deleteProject("sth");
			assertTrue("Empty repository doesn't work correctly (delete)", !a);
		} catch (Exception e) {
			assertTrue("Empty repository doesn't work correctly (delete)", false);
		}
	}

	@Test
	public void testPersistByName() throws Exception {
		try {
			String projectId = repo.createProject("default");
			char[] buffer = {'a', 'b', 'c', 'd'};
			byte[] b = new byte[4];
			for (int i = 0; i < b.length; i++) {

				b[i] = (byte) buffer[i];

			}
			ByteArrayInputStream file = new ByteArrayInputStream(b);
			String name = repo.persist(file, "name.txt", "default");
			assertTrue("Persist does not work correctly", !name.equals("") && name != null);
			boolean a = FileRepository.getRepo().deleteFileById(name);
		} catch (Exception e) {
			assertTrue("Persist does not work correctly", false);
		}
	}

	@Test
	public void testPersistById() throws Exception {
		try {
			String projectId = repo.createProject("default_project");
			char[] buffer = {'a', 'b', 'c', 'd'};
			byte[] b = new byte[4];
			for (int i = 0; i < b.length; i++) {

				b[i] = (byte) buffer[i];

			}
			ByteArrayInputStream file = new ByteArrayInputStream(b);
			String name = repo.persistById(file, "name.txt", projectId);
			assertTrue("Persist does not work correctly", (!(name.equals("")) && (name != null)));
			boolean a = FileRepository.getRepo().deleteFileById(name);
		} catch (Exception e) {
			assertTrue("Persist does not work correctly", false);
		}
	}

	@Test
	public void testGetFilesFromProjectForEmptyByName() throws Exception {
		String id = repo.createProject("project");
		List<ByteArrayInputStream> result = repo.getFilesFromProject("project");
		assertTrue("Get files by name from empty repository does not work correctly", result == null);
	}

	@Test
	public void testGetFilesFromProjectForEmptyById() throws Exception {
		String id = repo.createProject("project");
		List<ByteArrayInputStream> result = repo.getFilesFromProjectById(id);
		assertTrue("Get files by id from empty repository does not work correctly", result == null);
	}

	@Test
	public void testGetFilesFromProjectForOneFileByName() throws Exception {
		try {
			String projectId = repo.createProject("default");
			char[] buffer = {'a', 'b', 'c', 'd'};
			byte[] b = new byte[4];
			for (int i = 0; i < b.length; i++) {

				b[i] = (byte) buffer[i];

			}
			ByteArrayInputStream file = new ByteArrayInputStream(b);
			String name = repo.persistById(file, "name.txt", projectId);
			List<ByteArrayInputStream> result = repo.getFilesFromProject("default");
			byte[] res = new byte[4];
			result.get(0).read(res);
			assertTrue("GetFiles by name does not work correctly", Arrays.equals(res, b));
			boolean a = FileRepository.getRepo().deleteFileById(name);
		} catch (Exception e) {
			assertTrue("GetFiles by name does not work correctly", false);
		}
	}

	@Test
	public void testGetFilesFromProjectForOneFileById() throws Exception {
		try {
			String projectId = repo.createProject("default");
			char[] buffer = {'a', 'b', 'c', 'd'};
			byte[] b = new byte[4];
			for (int i = 0; i < b.length; i++) {

				b[i] = (byte) buffer[i];

			}
			ByteArrayInputStream file = new ByteArrayInputStream(b);
			String name = repo.persistById(file, "name.txt", projectId);
			List<ByteArrayInputStream> result = repo.getFilesFromProjectById(projectId);
			byte[] res = new byte[4];
			result.get(0).read(res);
			assertTrue("GetFiles by id does not work correctly", Arrays.equals(res, b));
			boolean a = FileRepository.getRepo().deleteFileById(name);
		} catch (Exception e) {
			assertTrue("GetFiles by id does not work correctly", false);
		}
	}

	@Test
	public void testGetFileFromProjectForEmptyByName() throws Exception {
		String id = repo.createProject("project");
		ByteArrayInputStream result = repo.getFileFromProject("sth", "project");
		assertTrue("Get file by name from empty repository does not work correctly", result == null);
	}

	@Test
	public void testGetFileFromProjectForEmptyById() throws Exception {
		String id = repo.createProject("project");
		ByteArrayInputStream result = repo.getFileFromProjectById("sth", id);
		assertTrue("Get file by id from empty repository does not work correctly", result == null);
	}

	@Test
	public void testGetFileFromProjectForOneFileByName() throws Exception {
		try {
			String projectId = repo.createProject("default");
			char[] buffer = {'a', 'b', 'c', 'd'};
			byte[] b = new byte[4];
			for (int i = 0; i < b.length; i++) {

				b[i] = (byte) buffer[i];

			}
			ByteArrayInputStream file = new ByteArrayInputStream(b);
			String name = repo.persistById(file, "name.txt", projectId);
			ByteArrayInputStream result = repo.getFileFromProject(name, "default");
			byte[] res = new byte[4];
			result.read(res);
			assertTrue("GetFile by name does not work correctly", Arrays.equals(res, b));
			boolean a = FileRepository.getRepo().deleteFileById(name);
		} catch (Exception e) {
			assertTrue("GetFile by name does not work correctly", false);
		}
	}

	@Test
	public void testGetFileFromProjectForOneFileById() throws Exception {
		try {
			String projectId = repo.createProject("default_project");
			char[] buffer = {'a', 'b', 'c', 'd'};
			byte[] b = new byte[4];
			for (int i = 0; i < b.length; i++) {

				b[i] = (byte) buffer[i];

			}
			ByteArrayInputStream file = new ByteArrayInputStream(b);
			String name = repo.persistById(file, "new_name.txt", projectId);
			ByteArrayInputStream result = repo.getFileFromProjectById(name, projectId);
			byte[] res = new byte[4];
			result.read(res);
			assertTrue("GetFile by id does not work correctly", Arrays.equals(res, b));
			boolean a = FileRepository.getRepo().deleteFileById(name);
		} catch (Exception e) {
			assertTrue("GetFile by id does not work correctly", false);
		}
	}
}
