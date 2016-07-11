package com.analyzeme.repository.filerepository;

import org.junit.Test;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FileInfoTest {

    @Test(expected = IllegalArgumentException.class)
    public void nullNameCtor() throws Exception {
        InputStream stream = new ByteArrayInputStream(
                new byte[]{'a', 'b'});
        new FileInfo(null, "sth", stream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyNameCtor() throws Exception {
        InputStream stream = new ByteArrayInputStream(
                new byte[]{'a', 'b'});
        new FileInfo("", "sth", stream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullIdCtor() throws Exception {
        InputStream stream = new ByteArrayInputStream(
                new byte[]{'a', 'b'});
        new FileInfo("sth", null, stream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyIdCtor() throws Exception {
        InputStream stream = new ByteArrayInputStream(
                new byte[]{'a', 'b'});
        new FileInfo("sth", "", stream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullStreamCtor() throws Exception {
        new FileInfo("sth", "sth", null);
    }

    @Test
    public void testGetNameForUser() throws Exception {
        InputStream stream = new ByteArrayInputStream(
                new byte[]{'a', 'b'});
        FileInfo info = new FileInfo("sth", "sth", stream);
        String name = info.getNameForUser();
        assertNotNull(name);
        assertEquals("sth", name);
    }

    @Test
    public void testGetUniqueName() throws Exception {
        InputStream stream = new ByteArrayInputStream(
                new byte[]{'a', 'b'});
        FileInfo info = new FileInfo("sth", "sth", stream);
        String name = info.getUniqueName();
        assertNotNull(name);
        assertEquals("sth", name);
    }

    @Test
    public void testGetUploadingDate() throws Exception {
        InputStream stream = new ByteArrayInputStream(
                new byte[]{'a', 'b'});
        FileInfo info = new FileInfo("sth", "sth", stream);
        Date date = info.getUploadingDate();
        assertNotNull(date);
    }

    @Test
    public void testGetData() throws Exception {
        InputStream stream = new ByteArrayInputStream(
                new byte[]{'a', 'b'});
        FileInfo info = new FileInfo("sth", "sth", stream);
        ByteArrayInputStream data = info.getData();
        assertNotNull(data);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetData() throws Exception {
        InputStream stream = new ByteArrayInputStream(
                new byte[]{'a', 'b'});
        FileInfo info = new FileInfo("sth", "sth", stream);
        info.setData(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullSetNameForUser() throws Exception {
        InputStream stream = new ByteArrayInputStream(
                new byte[]{'a', 'b'});
        FileInfo info = new FileInfo("sth", "sth", stream);
        info.setNameForUser(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptySetNameForUser() throws Exception {
        InputStream stream = new ByteArrayInputStream(
                new byte[]{'a', 'b'});
        FileInfo info = new FileInfo("sth", "sth", stream);
        info.setNameForUser("");
    }
}
