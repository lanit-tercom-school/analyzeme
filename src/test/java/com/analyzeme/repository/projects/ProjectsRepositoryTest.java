package com.analyzeme.repository.projects;

import com.analyzeme.data.DataSet;
import com.analyzeme.data.resolvers.sourceinfo.ISourceInfo;
import com.analyzeme.data.resolvers.sourceinfo.DataRepositoryInfo;
import com.analyzeme.repository.filerepository.TypeOfFile;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.*;

public class ProjectsRepositoryTest {

    @Test
    public void testCtor() {
        try {
            new ProjectsRepository();
        } catch (Exception e) {
            fail("ProjectsRepository ctor: exception");
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArgumentsForFindProjectById()
            throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        repo.findProjectById(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyArgumentsForFindProjectById()
            throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        repo.findProjectById("");
    }

    @Test
    public void testEmptyForFindProjectById()
            throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        ProjectInfo info = repo.findProjectById("sth");
        assertNull(
                "Empty project repository does not work correctly",
                info);
    }

    @Test
    public void testRecentlyAddedForFindProjectById()
            throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        String testProjectName = "new";
        String id = repo.createProject(testProjectName);
        ProjectInfo info = repo.findProjectById(id);
        assertEquals(
                "FindProjectById for recently added does not work correctly",
                id, info.getUniqueName());
        assertEquals(
                "FindProjectById for recently added does not work correctly",
                testProjectName, info.getProjectName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArgumentsForCreateProject() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        repo.createProject(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyArgumentsForCreateProject() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        repo.createProject("");
    }

    @Test
    public void uniqueNameFirsForCreateProject() {
        try {
            ProjectsRepository repo = new ProjectsRepository();
            String id = repo.createProject("new");
            assertEquals("Wrong id of first project", "project", id);
        } catch (Exception e) {
            fail("Something went wrong in createProject");
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArgumentsForDeactivateProjectById() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        repo.deactivateProjectById(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyArgumentsForDeactivateProjectById() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        repo.deactivateProjectById("");
    }

    @Test
    public void testEmptyForDeactivateProjectById() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        boolean deactivated = repo.deactivateProjectById("sth");
        assertFalse(
                "Empty repository works correctly for deactivation",
                deactivated);
    }

    @Test
    public void testRecentlyAddedForDeactivateProjectById() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        String id = repo.createProject("new");
        boolean deactivated =
                repo.deactivateProjectById(id);
        if (deactivated) {
            List<String> names =
                    repo.returnAllActiveProjectsIds();
            assertTrue(
                    "Deactivation is correct for recently added",
                    names.size() == 1);
            return;
        }
        fail("Deactivation is not correct for recently added");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullIdForPersist() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        ISourceInfo info =
                new DataRepositoryInfo("sth",
                        TypeOfFile.SIMPLE_JSON);
        DataSet set = new DataSet("sth", info);
        repo.persist(set, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyIdForPersist() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        ISourceInfo info =
                new DataRepositoryInfo("sth",
                        TypeOfFile.SIMPLE_JSON);
        DataSet set = new DataSet("sth", info);
        repo.persist(set, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullSetForPersist() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        repo.persist(null, "sth");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nonExistingProjectForPersist() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        ISourceInfo info = new DataRepositoryInfo(
                "sth", TypeOfFile.SIMPLE_JSON);
        DataSet set = new DataSet("sth", info);
        repo.persist(set, "sth");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullIdForGetByReferenceName() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        repo.getByReferenceName(null, "sth");
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyIdForGetByReferenceName() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        repo.getByReferenceName("", "sth");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullNameForGetByReferenceName() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        repo.getByReferenceName("sth", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyNameForGetByReferenceName() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        repo.getByReferenceName("sth", "");
    }

    @Test(expected = NullPointerException.class)
    public void testEmptyForGetByReferenceName() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        repo.getByReferenceName("sth", "sth");
    }

    @Test
    public void testRecentlyAddedForGetByReferenceName() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        ISourceInfo info = new DataRepositoryInfo(
                "sth", TypeOfFile.SIMPLE_JSON);
        DataSet set = new DataSet("sth", info);
        repo.persist(set, "demo");
        DataSet res = repo.getByReferenceName(
                "demo", "sth");
        assertNotNull(
                "getByReferenceName works correctly for recently added",
                res);
    }

    @Test
    public void testGetProjects() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        List<ProjectInfo> infos = repo.getProjects();
        assertTrue("getProjects for empty", infos.size() == 1 &&
                infos.get(0).getUniqueName().equals("demo"));
    }

    @Test
    public void testReturnAllProjectsIds() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        List<String> infos = repo.returnAllProjectsIds();
        assertTrue("getProjects for empty", infos.size() == 1 &&
                infos.get(0).equals("demo"));
    }

    @Test
    public void testReturnAllActiveProjectsIds() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        List<String> infos = repo.returnAllActiveProjectsIds();
        assertTrue("getProjects for empty", infos.size() == 1 &&
                infos.get(0).equals("demo"));
    }
}
