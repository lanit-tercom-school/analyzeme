package com.analyzeme.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertTrue;

/**
 * Created by lagroffe on 05.03.2016 13:39
 */

public class UsersRepositoryTest {
	private static UsersRepository repo;

	@BeforeClass
	public static void Before() {
		UsersRepository.repo.checkInitializationAndCreate();
		repo = UsersRepository.repo;
	}

	@Test
	public void testNewUserName() throws Exception {
		try {
			String[] param = {"guest", "guest@sth.sth", "1234"};
			String id = repo.newItem(param);
			int id2 = repo.findUser("guest").id;
			assertTrue("User wasn't created correctly", Integer.parseInt(id) == id2);
		} catch (Exception e) {
			assertTrue("User wasn't created correctly", false);
		}
	}

	@Test
	public void testRecentlyAddedById() throws Exception {
		try {
			String[] param = {"another_guest", "guest@sth.sth", "1234"};
			String id = repo.newItem(param);
			UserInfo info = repo.findUser(Integer.parseInt(id));
			if (info != null) {
				assertTrue("User wasn't returned correctly", ("another_guest".equals(info.login)) && ("guest@sth.sth".equals(info.email)) && ("1234".equals(info.password)));
			} else {
				assertTrue("User wasn't returned correctly", false);
			}
		} catch (Exception e) {
			assertTrue("User wasn't returned correctly", false);
		}
	}

	@Test
	public void testRecentlyAddedByName() throws Exception {
		try {
			String[] param = {"another_guest1", "guest@sth.sth", "1234"};
			String id = repo.newItem(param);
			UserInfo info = repo.findUser("another_guest1");
			if (info != null) {
				assertTrue("User wasn't returned correctly", ("another_guest1".equals(info.login)) && ("guest@sth.sth".equals(info.email)) && ("1234".equals(info.password)));
			} else {
				assertTrue("User wasn't returned correctly", false);
			}
		} catch (Exception e) {
			assertTrue("User wasn't returned correctly", false);
		}
	}

	@Test
	public void testFindForNotExistingUserByName() throws Exception {
		try {
			UserInfo info = repo.findUser("third_user");
			assertTrue("FindUser does not work correctly for non-existing user (by name)", info == null);
		} catch (ArrayIndexOutOfBoundsException e) {
			assertTrue("FindUser does not work correctly for non-existing user (by name)", true);
		}
	}

	@Test
	public void testFindForNotExistingUserById() throws Exception {
		try {
			UserInfo info = repo.findUser(150);
			assertTrue("FindUser does not work correctly for non-existing user (by id)", info == null);
		} catch (ArrayIndexOutOfBoundsException e) {
			assertTrue("FindUser does not work correctly for non-existing user (by id)", true);
		}
	}

	@Ignore
	@Test
	public void testPersist() throws Exception {
		//write when *-test functions are avoided in other repositories
	}

	@Ignore
	@Test
	public void testPersistByProjectId() throws Exception {
		//write when *-test functions are avoided in other repositories
	}

	@Ignore
	@Test
	public void testPersistByIds() throws Exception {
		//write when *-test functions are avoided in other repositories
	}

	@Test
	public void testGetAllNames() throws Exception {
		ArrayList<String> names = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			names.add("another_guest_" + i);
			String[] param = {"another_guest_" + i, "guest@sth.sth", "1234"};
			String id = repo.newItem(param);
		}
		ArrayList<String> names2 = repo.getAllNames();
		for (int i = 0; i < 5; i++) {
			assertTrue("Names does not returned correctly", names2.get(i).equals(names.get(i)));
		}
	}

	@Test
	public void testGetItem() throws Exception {
		String[] param = {"one_more_guest", "guest@sth.sth", "1234"};
		String id = repo.newItem(param);
		String json = repo.getItem(id);
		UserInfo info = repo.findUser(Integer.parseInt(id));
		ObjectMapper obj = new ObjectMapper();
		String json2 = obj.writeValueAsString(info);
		assertTrue("GetItem does not work correctly", json.equals(json2));
	}

	@Ignore
	@Test
	public void testGetFile() throws Exception {
		//write when *-test functions are avoided in other repositories
	}
}
