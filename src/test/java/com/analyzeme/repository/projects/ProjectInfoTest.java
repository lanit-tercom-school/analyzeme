package com.analyzeme.repository.projects;

import com.analyzeme.data.dataset.DataSet;
import com.analyzeme.data.resolvers.sourceinfo.ISourceInfo;
import com.analyzeme.data.resolvers.sourceinfo.DataRepositoryInfo;
import com.analyzeme.repository.filerepository.FileUploader;
import com.analyzeme.repository.filerepository.TypeOfFile;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ProjectInfoTest {

    @Test(expected = IllegalArgumentException.class)
    public void nullNameCtor() throws Exception {
        new ProjectInfo(null, "sth");
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyNameCtor() throws Exception {
        new ProjectInfo("", "sth");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullIdCtor() throws Exception {
        new ProjectInfo("sth", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyIdCtor() throws Exception {
        new ProjectInfo("sth", "");
    }

    @Test
    public void testEmptyForReturnActiveFilesForList()
            throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        String list = info.returnActiveFilesForList();
        assertEquals(
                "returnActiveFilesForList incorrect for empty project",
                "[]", list);
    }

    @Test
    public void testRecentlyAddedForReturnActiveFilesForList()
            throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        DataSet set = FileUploader.upload(
                "{\"Data\":[{\"x\":\"1\", \"y\":\"1\"}]}",
                "sth", "sth", TypeOfFile.SIMPLE_JSON);
        info.persist(set);
        String list = info.returnActiveFilesForList();
        assertNotNull(
                "returnActiveFilesForList incorrect for recently added",
                list);
        assertNotEquals(
                "returnActiveFilesForList incorrect for recently added",
                "[]", list);
    }

    @Test
    public void testRecentlyDeletedForReturnActiveFilesForList()
            throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        DataSet set = FileUploader.upload(
                "{\"Data\":[{\"x\":\"1\", \"y\":\"1\"}]}",
                "sth", "sth", TypeOfFile.SIMPLE_JSON);
        info.persist(set);
        info.deactivateFiles();
        String list = info.returnActiveFilesForList();
        assertEquals(
                "returnActiveFilesForList incorrect for recently deleted",
                "[]", list);
    }

    @Test
    public void testEmptyForReturnAllNamesOfActiveFiles()
            throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        List<String> list = info.returnAllNamesOfActiveFiles();
        assertTrue(
                "returnAllNamesOfActiveFiles incorrect for empty project",
                list == null || list.isEmpty());
    }

    @Test
    public void testRecentlyAddedForReturnAllNamesOfActiveFiles()
            throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        DataSet set = FileUploader.upload(
                "{\"Data\":[{\"x\":\"1\", \"y\":\"1\"}]}",
                "sth", "sth", TypeOfFile.SIMPLE_JSON);
        info.persist(set);
        List<String> list = info.returnAllNamesOfActiveFiles();
        assertNotNull(
                "returnAllNamesOfActiveFiles incorrect for recently added",
                list);
        assertFalse(
                "returnAllNamesOfActiveFiles incorrect for recently added",
                list.isEmpty());
    }

    @Test
    public void testRecentlyDeletedForReturnAllNamesOfActiveFiles()
            throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        DataSet set = FileUploader.upload(
                "{\"Data\":[{\"x\":\"1\", \"y\":\"1\"}]}",
                "sth", "sth", TypeOfFile.SIMPLE_JSON);
        info.persist(set);
        info.deactivateFiles();
        List<String> list = info.returnAllNamesOfActiveFiles();
        assertTrue(
                "returnAllNamesOfActiveFiles incorrect for recently deleted",
                list == null || list.isEmpty());
    }

    @Test
    public void testEmptyForGetReferenceNames() throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        List<String> list = info.getReferenceNames();
        assertTrue(
                "returnAllNamesOfActiveFiles incorrect for empty project",
                list == null || list.isEmpty());
    }

    @Test
    public void testRecentlyAddedForGetReferenceNames()
            throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        DataSet set = FileUploader.upload(
                "{\"Data\":[{\"x\":\"1\", \"y\":\"1\"}]}",
                "sth", "sth", TypeOfFile.SIMPLE_JSON);
        info.persist(set);
        List<String> list = info.getReferenceNames();
        assertNotNull(
                "returnAllNamesOfActiveFiles incorrect for recently added",
                list);
        assertFalse(
                "returnAllNamesOfActiveFiles incorrect for recently added",
                list.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArgumentGetDataSetByReferenceName()
            throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        info.getDataSetByReferenceName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyArgumentGetDataSetByReferenceName()
            throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        info.getDataSetByReferenceName("");
    }

    @Test
    public void testEmptyForGetDataSetByReferenceName()
            throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        DataSet set = info.getDataSetByReferenceName("sth");
        assertNull(set);
    }

    @Test
    public void testNonExistingForGetDataSetByReferenceName()
            throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        DataSet set = FileUploader.upload(
                "{\"Data\":[{\"x\":\"1\", \"y\":\"1\"}]}",
                "sth", "sth", TypeOfFile.SIMPLE_JSON);
        info.persist(set);
        DataSet set2 = info.getDataSetByReferenceName("sth2");
        assertNull(set2);
    }

    @Test
    public void testRecentlyAddedForGetDataSetByReferenceName()
            throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        DataSet set = FileUploader.upload(
                "{\"Data\":[{\"x\":\"1\", \"y\":\"1\"}]}",
                "sth", "sth", TypeOfFile.SIMPLE_JSON);
        info.persist(set);
        DataSet set2 = info.getDataSetByReferenceName(
                set.getReferenceName());
        assertNotNull(set2);
        assertEquals(set.getReferenceName(),
                set2.getReferenceName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArgumentPersist() throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        info.persist(null);
    }

    @Test
    public void testPersist() throws Exception {
        ProjectInfo project = new ProjectInfo("sth", "sth");
        ISourceInfo info = new DataRepositoryInfo(
                "sth", TypeOfFile.SIMPLE_JSON);
        DataSet set = new DataSet("sth", info);
        project.persist(set);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArgumentSetProjectName() throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        info.setProjectName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyArgumentSetProjectName() throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        info.setProjectName("");
    }

    @Test
    public void testGetProjectName() throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        String name = info.getProjectName();
        assertNotNull("getProjectName incorrect", name);
        assertNotEquals("getProjectName incorrect", "", name);
    }

    @Test
    public void testGetUniqueName() throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        String id = info.getUniqueName();
        assertNotNull("getUniqueName incorrect", id);
        assertNotEquals("getUniqueName incorrect", "", id);
    }

    @Test
    public void testLastChangeDate() throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        Date date = info.getLastChangeDate();
        assertNotNull("getLastChangeDate incorrect", date);
    }

    @Test
    public void testCreationDate() throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        Date date = info.getCreationDate();
        assertNotNull("getCreationDate incorrect", date);
    }

    @Test
    public void testGetDatasets() throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        List<DataSet> sets = info.getDatasets();
        assertNotNull("getDatasets incorrect", sets);
        assertTrue("getDatasets incorrect", sets.isEmpty());
    }
}
