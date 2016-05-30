package com.analyzeme.repository;


import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Created by lagroffe on 21.05.2016 17:47
 */
public class UserInfoTest {

    @Test(expected = IllegalArgumentException.class)
    public void nullLoginCtor() throws Exception {
        new UserInfo(null, 1, "sth", "sth");
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyLoginCtor() throws Exception {
        new UserInfo("", 1, "sth", "sth");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullEmailCtor() throws Exception {
        new UserInfo("sth", 1, null, "sth");
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyEmailCtor() throws Exception {
        new UserInfo("sth", 1, "", "sth");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullPasswordCtor() throws Exception {
        new UserInfo("sth", 1, "sth", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyPasswordCtor() throws Exception {
        new UserInfo("sth", 1, "sth", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullIdCtor() throws Exception {
        new UserInfo("sth", -4, "sth", "sth");
    }

    @Test
    public void testGetLogin() throws Exception {
        UserInfo info = new UserInfo("sth", 1, "sth", "sth");
        assertTrue("getLogin is not correct",
                info.getLogin() != null &&
                        !info.getLogin().equals(""));
    }

    @Test
    public void testGetEmail() throws Exception {
        UserInfo info = new UserInfo("sth", 1, "sth", "sth");
        assertTrue("getLogin is not correct",
                info.getEmail() != null &&
                        !info.getEmail().equals(""));
    }

    @Test
    public void testGetPassword() throws Exception {
        UserInfo info = new UserInfo("sth", 1, "sth", "sth");
        assertTrue("getLogin is not correct",
                info.getPassword() != null &&
                        !info.getPassword().equals(""));
    }

    @Test
    public void testGetId() throws Exception {
        UserInfo info = new UserInfo("sth", 1, "sth", "sth");
        assertTrue("getLogin is not correct", info.getId() > 0);
    }

    @Test
    public void testGetProjects() throws Exception {
        UserInfo info = new UserInfo("sth", 1, "sth", "sth");
        assertTrue("getLogin is not correct",
                info.getProjects() != null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArgumentSetLogin() throws Exception {
        UserInfo info = new UserInfo("sth", 1, "sth", "sth");
        info.setLogin(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyArgumentSetLogin() throws Exception {
        UserInfo info = new UserInfo("sth", 1, "sth", "sth");
        info.setLogin("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArgumentSetEmail() throws Exception {
        UserInfo info = new UserInfo("sth", 1, "sth", "sth");
        info.setEmail(null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void emptyArgumentSetEmail() throws Exception {
        UserInfo info = new UserInfo("sth", 1, "sth", "sth");
        info.setEmail("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArgumentSetPassword() throws Exception {
        UserInfo info = new UserInfo("sth", 1, "sth", "sth");
        info.setPassword(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyArgumentSetPassword() throws Exception {
        UserInfo info = new UserInfo("sth", 1, "sth", "sth");
        info.setPassword("");
    }
}
