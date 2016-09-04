package com.analyzeme.repository.filerepository;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;

public class FileRepositoryTest {
    private static ByteArrayInputStream file;
    private static FileRepository repo;
    private static char[] buffer = {'a', 'b', 'c', 'd'};
    public static Boolean[] correct = new Boolean[200];

    /**
     * creates ByteArrayInputStream object before tests
     */
    @Before
    public void createFile() {
        byte[] b = new byte[4];
        for (int i = 0; i < b.length; i++) {

            b[i] = (byte) buffer[i];

        }
        file = new ByteArrayInputStream(b);
    }

    private void cleanCorrect() {
        for (int i = 0; i < 200; i++) {
            correct[i] = false;
        }
    }

    private Boolean checkCorrect() {
        for (int i = 0; i < 200; i++) {
            if (!correct[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * test method for persist - check for exceptions and return value
     */
    @Test
    public void testAddNewFile() throws Exception {
        FileRepositoryTest.repo = new FileRepository();
        String nameToWrite = "";
        try {
            for (int j = 0; j < 10; j++) {
                nameToWrite = FileRepositoryTest
                        .repo.persist(file, "filename.txt");
            }
        } catch (Exception e) {
            fail("Exceptions in adding new file");
        }
        if (nameToWrite == null) {
            fail("Wrong return value in adding new file");
        }
        if (FileRepositoryTest.repo.countFiles() != 10) {
            fail("Wrong counter of added files");
        }
    }

    /**
     * Test method of getByReferenceName
     * should fail for empty repository
     */
    @Test
    public void testEmptyRepository() throws Exception {
        FileRepositoryTest.repo = new FileRepository();
        ByteArrayInputStream get =
                FileRepositoryTest.repo.getFileByID("anything");
        assertNull(
                "Empty repository does not work properly",
                get);
    }

    /**
     * Test method of persist and getFileById
     * checks if the recently added file will be returned correctly
     */
    @Test
    public void testRecentlyAdded() throws Exception {
        FileRepositoryTest.repo = new FileRepository();
        String nameToWrite =
                FileRepositoryTest.repo
                        .persist(file, "filename.txt");
        for (int i = 0; i < 10; i++) {
            ByteArrayInputStream file2 =
                    FileRepositoryTest.repo
                            .getFileByID(nameToWrite);
            byte[] b2 = new byte[4];
            file2.read(b2);
            char[] buffer2 = new char[4];
            for (int j = 0; j < b2.length; j++) {
                buffer2[j] = (char) b2[j];
            }
            assertTrue(
                    "File is not returned correctly",
                    Arrays.equals(buffer, buffer2));
        }
    }

    @Test
    public void testDeactivateNonExisting() throws Exception {
        FileRepositoryTest.repo = new FileRepository();
        boolean result = FileRepository.getRepo()
                .deleteFileById("sth");
        assertFalse(
                "Empty repository does not work properly",
                result);
    }

    @Test
    public void testDeleteNonExisting() throws Exception {
        FileRepositoryTest.repo = new FileRepository();
        boolean result =
                FileRepository.getRepo()
                        .deleteFileByIdCompletely("sth");
        assertFalse(
                "Empty repository does not work properly",
                result);
    }

    /**
     * Test method of getFileById
     * checks if it is possible to correctly get file from different threads
     */
    @Test
    public void testThreadsForReading() throws Exception {
        cleanCorrect();
        FileRepositoryTest.repo = new FileRepository();
        String nameToWrite = FileRepositoryTest
                .repo.persist(file, "filename.txt");

        for (int i = 0; i < 200; i++) {
            testReaderThread testReader =
                    new testReaderThread(nameToWrite, i);
            testReader.start();
        }
        Thread.sleep(5000);

        Boolean check = checkCorrect();
        assertTrue(
                "Filereading from other thread is incorrect",
                check);
    }


    private class testReaderThread extends Thread {
        private final String nameToWrite;
        private final int number;

        testReaderThread(final String name,
                         final int number) {
            nameToWrite = name;
            this.number = number;
        }

        @Override
        public void run() {
            try {
                ByteArrayInputStream file2 =
                        FileRepositoryTest.repo
                                .getFileByID(nameToWrite);
                byte[] b2 = new byte[4];
                file2.read(b2);
                char[] buffer2 = new char[4];
                for (int i = 0; i < b2.length; i++) {
                    buffer2[i] = (char) b2[i];
                }

                if (Arrays.equals(buffer, buffer2)) {
                    FileRepositoryTest.correct[number] = true;
                } else {
                    FileRepositoryTest.correct[number] = false;
                }
            } catch (Exception e) {
                FileRepositoryTest.correct[number] = false;
            }
        }
    }
}
