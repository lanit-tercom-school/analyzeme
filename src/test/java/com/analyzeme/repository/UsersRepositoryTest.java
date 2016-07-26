package com.analyzeme.repository;

import com.analyzeme.data.dataset.DataSet;
import com.analyzeme.data.resolvers.sourceinfo.ISourceInfo;
import com.analyzeme.data.resolvers.sourceinfo.DataRepositoryInfo;
import com.analyzeme.repository.filerepository.FileInfo;
import com.analyzeme.repository.filerepository.TypeOfFile;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNull;

public class UsersRepositoryTest {

    @Before
    public void before() {
        UsersRepository.deleteForTests();
        UsersRepository.checkInitializationAndCreate();
    }

    @Test(expected = IllegalStateException.class)
    public void testEmptyForCheckInitialization() {
        UsersRepository.deleteForTests();
        UsersRepository.checkInitialization();
    }

    @Test
    public void testForCheckInitialization() throws Exception {
        UsersRepository.checkInitialization();
    }

    @Test(expected = IllegalArgumentException.class)
    public void lengthOfArgumentsForNewItem() throws Exception {
        UsersRepository.newItem(
                new String[]{"sth", "sth"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArgumentsForNewItem0() throws Exception {
        UsersRepository.newItem(
                new String[]{null, "sth", "sth"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArgumentsForNewItem1() throws Exception {
        UsersRepository.newItem(
                new String[]{"sth", null, "sth"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArgumentsForNewItem2() throws Exception {
        UsersRepository.newItem(
                new String[]{"sth", "sth", null});
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyArgumentsForNewItem0() throws Exception {
        UsersRepository.newItem(
                new String[]{"", "sth", "sth"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyArgumentsForNewItem1() throws Exception {
        UsersRepository.newItem(
                new String[]{"sth", "", "sth"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyArgumentsForNewItem2() throws Exception {
        UsersRepository.newItem(
                new String[]{"sth", "sth", ""});
    }

    @Test
    public void testNewItem() throws Exception {
        String id = UsersRepository.newItem(
                new String[]{"sth", "sth", "sth"});
        assertEquals(
                "newItem not correct for empty repository",
                "1", id);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void wrongArgumentFindUserId() throws Exception {
        UsersRepository.newItem(
                new String[]{"sth", "sth", "sth"});
        UsersRepository.findUser(-50);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testEmptyForFindUserId() throws Exception {
        UsersRepository.findUser(1);
    }

    @Test
    public void testRecentlyAddedForFindUserId() throws Exception {
        String id = UsersRepository.newItem(
                new String[]{"sth", "sth", "sth"});
        UserInfo info = UsersRepository
                .findUser(Integer.parseInt(id));
        assertEquals("findUser(int) does not work correctly",
                "sth", info.getLogin());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArgumentFindUserName() throws Exception {
        UsersRepository.newItem(new String[]{"sth", "sth", "sth"});
        UsersRepository.findUser(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyArgumentFindUserName() throws Exception {
        UsersRepository.newItem(new String[]{"sth", "sth", "sth"});
        UsersRepository.findUser("");
    }


    @Test(expected = IllegalArgumentException.class)
    public void testEmptyForFindUserName() throws Exception {
        UsersRepository.findUser("non-exist");
    }

    @Test
    public void testRecentlyAddedForFindUserName() throws Exception {
        UsersRepository.newItem(
                new String[]{"sth", "sth", "sth"});
        UserInfo info = UsersRepository.findUser("sth");
        assertEquals(
                "findUser(int) does not work correctly",
                "sth", info.getLogin());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullLoginForNewProject() throws Exception {
        UsersRepository.newProject(null, "sth");
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyLoginForNewProject() throws Exception {
        UsersRepository.newProject("", "sth");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullNameForNewProject() throws Exception {
        UsersRepository.newProject("sth", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyNameForNewProject() throws Exception {
        UsersRepository.newProject("sth", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyForNewProject() throws Exception {
        UsersRepository.newProject("sth", "sth");
    }

    @Test
    public void testForNewProject() throws Exception {
        UsersRepository.newItem(new String[]{"sth", "sth", "sth"});
        String id = UsersRepository.newProject("sth", "sth");
        assertEquals(
                "new project is not created correctly",
                "project", id);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullLoginForNewProjectId() throws Exception {
        UsersRepository.newProject(0, "sth");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullNameForNewProjectId() throws Exception {
        UsersRepository.newProject("sth", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyNameForNewProjectId() throws Exception {
        UsersRepository.newProject("sth", "");
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testEmptyForNewProjectId() throws Exception {
        UsersRepository.newProject(1, "sth");
    }

    @Test
    public void testForNewProjectId() throws Exception {
        String user = UsersRepository.newItem(
                new String[]{"sth", "sth", "sth"});
        String id = UsersRepository.newProject(
                Integer.parseInt(user), "sth");
        assertEquals(
                "new project is not created correctly",
                "project", id);
    }

    @Test
    public void testGetAllIds() throws Exception {
        List<String> ids = UsersRepository.getAllIds();
        assertTrue(
                "getAllIds does not work correctly",
                ids.isEmpty());
    }

    @Test
    public void testFindByReferenceName() throws Exception {
        String user = UsersRepository.newItem(
                new String[]{"sth", "sth", "sth"});
        String id = UsersRepository.newProject(
                Integer.parseInt(user), "sth");
        ISourceInfo info = new DataRepositoryInfo(
                "sth", TypeOfFile.SIMPLE_JSON);
        DataSet set = new DataSet("sth", info);
        UsersRepository.findUser("sth").
                getProjects().findProjectById(id).persist(set);
        FileInfo res = UsersRepository.findByReferenceName(
                "sth", new String[]{user, id});
        assertNull(
                "findByReferenceName does not work correctly",
                res);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullNameForGetDataSetByReferenceName() throws Exception {
        UsersRepository.getDataSetByReferenceName(
                null, new String[]{"sth", "sth"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyNameForGetDataSetByReferenceName() throws Exception {
        UsersRepository.getDataSetByReferenceName("",
                new String[]{"sth", "sth"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void lengthForGetDataSetByReferenceName() throws Exception {
        UsersRepository.getDataSetByReferenceName("sth",
                new String[]{"sth"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyArgumentForGetDataSetByReferenceName0() throws Exception {
        UsersRepository.getDataSetByReferenceName("sth",
                new String[]{"", "sth"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArgumentForGetDataSetByReferenceName0() throws Exception {
        UsersRepository.getDataSetByReferenceName("sth",
                new String[]{null, "sth"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyArgumentForGetDataSetByReferenceName1() throws Exception {
        UsersRepository.getDataSetByReferenceName("sth",
                new String[]{"sth", ""});
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArgumentForGetDataSetByReferenceName1() throws Exception {
        UsersRepository.getDataSetByReferenceName("sth",
                new String[]{"sth", null});
    }

    @Test(expected = IllegalArgumentException.class)
    public void nonExistingProjectForGetDataSetByReferenceName() throws Exception {
        String user = UsersRepository.newItem(
                new String[]{"sth", "sth", "sth"});
        UsersRepository.getDataSetByReferenceName("sth",
                new String[]{user, "sth"});
    }

    @Test
    public void emptyProjectForGetDataSetByReferenceName() throws Exception {
        String user = UsersRepository.newItem(
                new String[]{"sth", "sth", "sth"});
        String id = UsersRepository.newProject(
                Integer.parseInt(user), "sth");
        DataSet set = UsersRepository.getDataSetByReferenceName(
                "sth", new String[]{user, id});
        assertNull(
                "getDataSetByReferenceName does not work correctly for empty project",
                set);
    }

    @Test
    public void testGetDataSetByReferenceName() throws Exception {
        String user = UsersRepository.newItem(
                new String[]{"sth", "sth", "sth"});
        String id = UsersRepository.newProject(
                Integer.parseInt(user), "sth");
        ISourceInfo info =
                new DataRepositoryInfo("sth", TypeOfFile.SIMPLE_JSON);
        DataSet set = new DataSet("sth", info);
        UsersRepository.findUser("sth").
                getProjects().findProjectById(id).persist(set);

        DataSet res = UsersRepository.
                getDataSetByReferenceName("sth", new String[]{user, id});
        assertEquals(
                "getDataSetByReferenceName does not work correctly for recently added",
                set, res);
    }
}
