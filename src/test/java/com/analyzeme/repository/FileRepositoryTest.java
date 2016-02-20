package com.analyzeme.repository;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

import static junit.framework.Assert.assertTrue;

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
			if (correct[i] == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * test method for addNewFile - check for exceptions and return value
	 */
	@Test
	public void testAddNewFile() throws Exception {
		FileRepositoryTest.repo = new FileRepository();
		int a = 0;
		String nameToWrite = "";
		try {
			for (int j = 0; j < 10; j++) {
				nameToWrite = FileRepositoryTest.repo.addNewFileForTests(file, "filename.txt", "guest");
			}
		} catch (Exception e) {
			assertTrue("Exceptions in adding new file", false);
			a++;
		}
		if (nameToWrite == null) {
			assertTrue("Wrong return value in adding new file", false);
			a++;
		}
		if (FileRepositoryTest.repo.countFiles() != 10) {
			assertTrue("Wrong counter of added files", false);
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
		FileRepositoryTest.repo = new FileRepository();
		ByteArrayInputStream get = FileRepositoryTest.repo.getFileByID("anything");
		assertTrue("Empty repository does not work properly", get == null);
	}

	/**
	 * Test method of addNewFile and getFileById
	 * checks if the recently added file will be returned correctly
	 */
	@Test
	public void testRecentlyAdded() throws Exception {
		FileRepositoryTest.repo = new FileRepository();
		String nameToWrite = FileRepositoryTest.repo.addNewFileForTests(file, "filename.txt", "guest");
		for (int i = 0; i < 10; i++) {
			ByteArrayInputStream file2 = FileRepositoryTest.repo.getFileByID(nameToWrite);
			byte[] b2 = new byte[4];
			file2.read(b2);
			char[] buffer2 = new char[4];
			for (int j = 0; j < b2.length; j++) {
				buffer2[j] = (char) b2[j];
			}
			assertTrue("File is not returned correctly", Arrays.equals(buffer, buffer2));
		}
	}

	/**
	 * Test method of getFileById
	 * checks if it is possible to correctly get file from different threads
	 */
	@Test
	public void testThreadsForReading() throws Exception {
		cleanCorrect();
		FileRepositoryTest.repo = new FileRepository();
		String nameToWrite = FileRepositoryTest.repo.addNewFileForTests(file, "filename.txt", "guest");

		for (int i = 0; i < 200; i++) {
			testReaderThread testReader = new testReaderThread(nameToWrite, i);
			testReader.start();
		}
		Thread.sleep(5000);

		Boolean check = checkCorrect();
		assertTrue("Filereading from other thread is incorrect", check);
	}


	private class testReaderThread extends Thread {
		String nameToWrite;
		int number;

		testReaderThread(String name, int number) {
			nameToWrite = name;
			this.number = number;
		}

		@Override
		public void run() {
			try {
				ByteArrayInputStream file2 = FileRepositoryTest.repo.getFileByID(nameToWrite);
				byte[] b2 = new byte[4];
				file2.read(b2);
				char[] buffer2 = new char[4];
				for (int i = 0; i < b2.length; i++) {
					buffer2[i] = (char) b2[i];
				}

				if (Arrays.equals(buffer, buffer2)) {
					FileRepositoryTest.correct[number] = true;
				} else {
					System.out.println("Incorrect file");
					FileRepositoryTest.correct[number] = false;
				}
			} catch (Exception e) {
				System.out.println("Incorrect file exception");
				FileRepositoryTest.correct[number] = false;
			}
		}
	}
}
