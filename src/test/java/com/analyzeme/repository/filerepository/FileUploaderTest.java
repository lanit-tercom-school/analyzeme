package com.analyzeme.repository.filerepository;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by lagroffe on 21.05.2016 19:07
 */

//after real reference names are added, add tests for formed datasets
public class FileUploaderTest {

    @Test(expected = IllegalArgumentException.class)
    public void nullFileForUploadMultipart() throws Exception {
        FileUploader.upload((MultipartFile) null,
                "sth", "sth", TypeOfFile.SIMPLE_JSON);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullNameForUploadMultipart() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile("sth", new byte[]{'a', 'b'});
        FileUploader.upload(file, null,
                "sth", TypeOfFile.SIMPLE_JSON);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyNameForUploadMultipart() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile("sth", new byte[]{'a', 'b'});
        FileUploader.upload(file, "",
                "sth", TypeOfFile.SIMPLE_JSON);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullReferenceForUploadMultipart() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile("sth", new byte[]{'a', 'b'});
        FileUploader.upload(file, "sth",
                null, TypeOfFile.SIMPLE_JSON);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyReferenceForUploadMultipart() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile("sth", new byte[]{'a', 'b'});
        FileUploader.upload(file, "sth", "", TypeOfFile.SIMPLE_JSON);
    }

     @Test(expected = IllegalArgumentException.class)
    public void nullFileForUploadString() throws Exception {
         FileUploader.upload((String)null, "sth",
                 "sth", TypeOfFile.SIMPLE_JSON);
     }

    @Test(expected = IllegalArgumentException.class)
    public void emptyFileForUploadString() throws Exception {
        FileUploader.upload("", "sth", "sth",
                TypeOfFile.SIMPLE_JSON);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullNameForUploadString() throws Exception {
        FileUploader.upload("sth", null, "sth",
                TypeOfFile.SIMPLE_JSON);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyNameForUploadString() throws Exception {
        FileUploader.upload("sth", "", "sth",
                TypeOfFile.SIMPLE_JSON);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullReferenceForUploadString() throws Exception {
        FileUploader.upload("sth", "sth",
                null, TypeOfFile.SIMPLE_JSON);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyReferenceForUploadString() throws Exception {
        FileUploader.upload("sth", "sth",
                "", TypeOfFile.SIMPLE_JSON);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullFileForUploadStream() throws Exception {
        FileUploader.upload((ByteArrayInputStream)null,
                "sth", "sth", TypeOfFile.SIMPLE_JSON);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullNameForUploadStream() throws Exception {
        InputStream stream =
                new ByteArrayInputStream(new byte[]{'a', 'b'});
        FileUploader.upload(stream, null,
                "sth", TypeOfFile.SIMPLE_JSON);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyNameForUploadStream() throws Exception {
        InputStream stream =
                new ByteArrayInputStream(new byte[]{'a', 'b'});
        FileUploader.upload(stream, "",
                "sth", TypeOfFile.SIMPLE_JSON);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullReferenceForUploadStream() throws Exception {
        InputStream stream =
                new ByteArrayInputStream(new byte[]{'a', 'b'});
        FileUploader.upload(stream, "sth",
                null, TypeOfFile.SIMPLE_JSON);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyReferenceForUploadStream() throws Exception {
        InputStream stream =
                new ByteArrayInputStream(new byte[]{'a', 'b'});
        FileUploader.upload(stream, "sth", "", TypeOfFile.SIMPLE_JSON);
    }
}
