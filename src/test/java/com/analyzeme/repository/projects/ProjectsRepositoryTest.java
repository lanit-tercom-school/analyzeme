package com.analyzeme.repository.projects;

/**
 * Created by lagroffe on 21.05.2016 10:54
 */

import com.analyzeme.data.DataSet;
import com.analyzeme.data.resolvers.sourceinfo.ISourceInfo;
import com.analyzeme.data.resolvers.sourceinfo.JsonPointFileInRepositoryInfo;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

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
    public void nullArgumentsForFindProjectById() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        repo.findProjectById(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyArgumentsForFindProjectById() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        repo.findProjectById("");
    }

    @Test
    public void testEmptyForFindProjectById() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        ProjectInfo info = repo.findProjectById("sth");
        assertTrue("Empty project repository does not work correctly", info == null);
    }

    @Test
    public void testRecentlyAddedForFindProjectById() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        String testProjectName = "new";
        String id = repo.createProject(testProjectName);
        ProjectInfo info = repo.findProjectById(id);
        assertTrue("FindProjectById for recently added does not work correctly", info.getProjectName().equals(testProjectName) && info.getUniqueName().equals(id));
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
            assertTrue("Wrong id of first project", id.equals("project"));
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
        assertTrue("Empty repository works correctly for deactivation", !deactivated);
    }

    @Test
    public void testRecentlyAddedForDeactivateProjectById() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        String id = repo.createProject("new");
        boolean deactivated = repo.deactivateProjectById(id);
        if(deactivated) {
            List<String> names = repo.returnAllActiveProjectsIds();
            assertTrue("Deactivation is correct for recently added", names.size() == 1);
            return;
        }
        fail("Deactivation is not correct for recently added");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullIdForPersist() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        ISourceInfo info = new JsonPointFileInRepositoryInfo("sth");
        DataSet set = new DataSet("sth", info);
        repo.persist(set, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyIdForPersist() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        ISourceInfo info = new JsonPointFileInRepositoryInfo("sth");
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
        ISourceInfo info = new JsonPointFileInRepositoryInfo("sth");
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
        ISourceInfo info = new JsonPointFileInRepositoryInfo("sth");
        DataSet set = new DataSet("sth", info);
        repo.persist(set, "demo");
        DataSet res = repo.getByReferenceName("demo", "sth");
        assertTrue("getByReferenceName works correctly for recently added", res!=null);
    }

    @Test
    public void testGetProjects() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        List<ProjectInfo> infos = repo.getProjects();
        assertTrue("getProjects for empty", infos.size() == 1 && infos.get(0).getUniqueName().equals("demo"));
    }

    @Test
    public void testReturnAllProjectsIds() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        List<String> infos = repo.returnAllProjectsIds();
        assertTrue("getProjects for empty", infos.size() == 1 && infos.get(0).equals("demo"));
    }

    @Test
    public void testReturnAllActiveProjectsIds() throws Exception {
        ProjectsRepository repo = new ProjectsRepository();
        List<String> infos = repo.returnAllActiveProjectsIds();
        assertTrue("getProjects for empty", infos.size() == 1 && infos.get(0).equals("demo"));
    }
}
