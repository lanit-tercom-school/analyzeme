package com.analyzeme.repository.filerepository;


import org.junit.Test;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.assertTrue;
/**
 * Created by lagroffe on 21.05.2016 18:44
 */
public class FileInfoTest {

    @Test(expected = IllegalArgumentException.class)
    public void nullNameCtor() throws Exception {
        InputStream stream = new ByteArrayInputStream(new byte[]{'a', 'b'});
        new FileInfo(null, "sth", stream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyNameCtor() throws Exception {
        InputStream stream = new ByteArrayInputStream(new byte[]{'a', 'b'});
        new FileInfo("", "sth", stream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullIdCtor() throws Exception {
        InputStream stream = new ByteArrayInputStream(new byte[]{'a', 'b'});
        new FileInfo("sth", null, stream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyIdCtor() throws Exception {
        InputStream stream = new ByteArrayInputStream(new byte[]{'a', 'b'});
        new FileInfo("sth", "", stream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullStreamCtor() throws Exception {
        new FileInfo("sth", "sth", null);
    }

    @Test
    public void testGetNameForUser() throws Exception {
        InputStream stream = new ByteArrayInputStream(new byte[]{'a', 'b'});
        FileInfo info = new FileInfo("sth", "sth", stream);
        String name = info.getNameForUser();
        assertTrue(name != null && name.equals("sth"));
    }

    @Test
    public void testGetUniqueName() throws Exception {
        InputStream stream = new ByteArrayInputStream(new byte[]{'a', 'b'});
        FileInfo info = new FileInfo("sth", "sth", stream);
        String name = info.getUniqueName();
        assertTrue(name != null && name.equals("sth"));
    }

    @Test
    public void testGetUploadingDate() throws Exception {
        InputStream stream = new ByteArrayInputStream(new byte[]{'a', 'b'});
        FileInfo info = new FileInfo("sth", "sth", stream);
        Date date = info.getUploadingDate();
        assertTrue(date != null);
    }

    @Test
    public void testGetData() throws Exception {
        InputStream stream = new ByteArrayInputStream(new byte[]{'a', 'b'});
        FileInfo info = new FileInfo("sth", "sth", stream);
        ByteArrayInputStream data = info.getData();
        assertTrue(data != null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetData() throws Exception {
        InputStream stream = new ByteArrayInputStream(new byte[]{'a', 'b'});
        FileInfo info = new FileInfo("sth", "sth", stream);
        info.setData(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullSetNameForUser() throws Exception {
        InputStream stream = new ByteArrayInputStream(new byte[]{'a', 'b'});
        FileInfo info = new FileInfo("sth", "sth", stream);
        info.setNameForUser(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptySetNameForUser() throws Exception {
        InputStream stream = new ByteArrayInputStream(new byte[]{'a', 'b'});
        FileInfo info = new FileInfo("sth", "sth", stream);
        info.setNameForUser("");
    }
}
