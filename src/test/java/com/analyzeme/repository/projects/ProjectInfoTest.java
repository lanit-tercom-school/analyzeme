package com.analyzeme.repository.projects;

import com.analyzeme.data.DataSet;
import com.analyzeme.data.resolvers.sourceinfo.ISourceInfo;
import com.analyzeme.data.resolvers.sourceinfo.DataRepositoryInfo;
import com.analyzeme.repository.filerepository.FileUploader;
import com.analyzeme.repository.filerepository.TypeOfFile;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by lagroffe on 21.05.2016 17:59
 */
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
    public void testEmptyForReturnActiveFilesForList() throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        String list = info.returnActiveFilesForList();
        assertTrue(
                "returnActiveFilesForList incorrect for empty project",
                list.equals("[]"));
    }

    @Test
    public void testRecentlyAddedForReturnActiveFilesForList() throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        DataSet set = FileUploader.upload(
                "{\"Data\":[{\"x\":\"1\", \"y\":\"1\"}]}",
                "sth", "sth", TypeOfFile.SIMPLE_JSON);
        info.persist(set);
        String list = info.returnActiveFilesForList();
        assertTrue(
                "returnActiveFilesForList incorrect for recently added",
                list != null && !list.equals("[]"));
    }

    @Test
    public void testRecentlyDeletedForReturnActiveFilesForList() throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        DataSet set = FileUploader.upload(
                "{\"Data\":[{\"x\":\"1\", \"y\":\"1\"}]}",
                "sth", "sth", TypeOfFile.SIMPLE_JSON);
        info.persist(set);
        info.deactivateFiles();
        String list = info.returnActiveFilesForList();
        assertTrue(
                "returnActiveFilesForList incorrect for recently deleted",
                list.equals("[]"));
    }

    @Test
    public void testEmptyForReturnAllNamesOfActiveFiles() throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        List<String> list = info.returnAllNamesOfActiveFiles();
        assertTrue(
                "returnAllNamesOfActiveFiles incorrect for empty project",
                list == null || list.isEmpty());
    }

    @Test
    public void testRecentlyAddedForReturnAllNamesOfActiveFiles() throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        DataSet set = FileUploader.upload(
                "{\"Data\":[{\"x\":\"1\", \"y\":\"1\"}]}",
                "sth", "sth", TypeOfFile.SIMPLE_JSON);
        info.persist(set);
        List<String> list = info.returnAllNamesOfActiveFiles();
        assertTrue(
                "returnAllNamesOfActiveFiles incorrect for recently added",
                list != null && !list.isEmpty());
    }

    @Test
    public void testRecentlyDeletedForReturnAllNamesOfActiveFiles() throws Exception {
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
    public void testRecentlyAddedForGetReferenceNames() throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        DataSet set = FileUploader.upload(
                "{\"Data\":[{\"x\":\"1\", \"y\":\"1\"}]}",
                "sth", "sth", TypeOfFile.SIMPLE_JSON);
        info.persist(set);
        List<String> list = info.getReferenceNames();
        assertTrue(
                "returnAllNamesOfActiveFiles incorrect for recently added",
                list != null && !list.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArgumentGetDataSetByReferenceName() throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        info.getDataSetByReferenceName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyArgumentGetDataSetByReferenceName() throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        info.getDataSetByReferenceName("");
    }

    @Test
    public void testEmptyForGetDataSetByReferenceName() throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        DataSet set = info.getDataSetByReferenceName("sth");
        assertTrue(set == null);
    }

    @Test
    public void testNonExistingForGetDataSetByReferenceName() throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        DataSet set = FileUploader.upload(
                "{\"Data\":[{\"x\":\"1\", \"y\":\"1\"}]}",
                "sth", "sth", TypeOfFile.SIMPLE_JSON);
        info.persist(set);
        DataSet set2 = info.getDataSetByReferenceName("sth2");
        assertTrue(set2 == null);
    }

    @Test
    public void testRecentlyAddedForGetDataSetByReferenceName() throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        DataSet set = FileUploader.upload(
                "{\"Data\":[{\"x\":\"1\", \"y\":\"1\"}]}",
                "sth", "sth", TypeOfFile.SIMPLE_JSON);
        info.persist(set);
        DataSet set2 = info.getDataSetByReferenceName(set.getReferenceName());
        assertTrue(set2 != null && set2.getReferenceName().equals(set.getReferenceName()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArgumentPersist() throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        info.persist(null);
    }

    @Test
    public void testPersist() throws Exception {
        ProjectInfo project = new ProjectInfo("sth", "sth");
        ISourceInfo info = new DataRepositoryInfo("sth", TypeOfFile.SIMPLE_JSON);
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
        assertTrue("getProjectName incorrect", name != null && !name.equals(""));
    }

    @Test
    public void testGetUniqueName() throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        String id = info.getUniqueName();
        assertTrue("getUniqueName incorrect", id != null && !id.equals(""));
    }

    @Test
    public void testLastChangeDate() throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        Date date = info.getLastChangeDate();
        assertTrue("getLastChangeDate incorrect", date != null);
    }

    @Test
    public void testCreationDate() throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        Date date = info.getCreationDate();
        assertTrue("getCreationDate incorrect", date != null);
    }

    @Test
    public void testGetDatasets() throws Exception {
        ProjectInfo info = new ProjectInfo("sth", "sth");
        List<DataSet> sets = info.getDatasets();
        assertTrue("getDatasets incorrect", sets != null && sets.isEmpty());
    }
}
