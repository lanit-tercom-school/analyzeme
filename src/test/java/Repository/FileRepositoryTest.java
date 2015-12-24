package Repository;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

import static junit.framework.Assert.assertTrue;

public class FileRepositoryTest {

	/**
	 * test method for addNewFile - check for exceptions and return value
	 */
	@Test
	public void testAddNewFile() throws Exception {
		int a = 0;
		String nameToWrite = "";
		try {
			IFileRepository repo = new FileRepository();
			char[] buffer = {'a', 'b', 'c', 'd'};
			byte[] b = new byte[4];
			for (int i = 0; i < b.length; i++) {

				b[i] = (byte) buffer[i];

			}
			ByteArrayInputStream file = new ByteArrayInputStream(b);
			nameToWrite = repo.addNewFileForTests(file, "filename", "guest");
		} catch (Exception e) {
			assertTrue("Exceptions in adding new file", false);
			a++;
		}
		if (nameToWrite == null) {
			assertTrue("Wrong return value in adding new file", false);
			a++;
		}
		if (a == 0) assertTrue("Exceptions in adding new file", true);
	}

	/**
	 * Test method of getFileByID
	 * should fail for empty repository
	 */
	@Test
	public void testEmptyRepository() throws Exception {
		IFileRepository empty = new FileRepository();
		ByteArrayInputStream get = empty.getFileByID("anything");
		assertTrue("Empty repository does not work properly", get == null);
	}

	/**
	 * Test method of addNewFile and getFileById
	 * checks if the recently added file will be returned correctly
	 */
	@Test
	public void testRecentlyAdded() throws Exception {
		IFileRepository repo = new FileRepository();
		char[] buffer = {'a', 'b', 'c', 'd'};
		byte[] b = new byte[4];
		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) buffer[i];
		}
		ByteArrayInputStream file = new ByteArrayInputStream(b);
		String nameToWrite = repo.addNewFileForTests(file, "filename", "guest");
		ByteArrayInputStream file2 = repo.getFileByID(nameToWrite);

		byte[] b2 = new byte[4];
		file2.read(b2);
		char[] buffer2 = new char[4];
		for (int i = 0; i < b.length; i++) {
			buffer2[i] = (char) b2[i];
		}
		assertTrue("File is not returned correctly", Arrays.equals(buffer, buffer2));
	}
}
