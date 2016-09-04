package com.analyzeme.repository;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

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
        assertNotNull("getLogin is not correct",
                info.getLogin());
        assertNotEquals("getLogin is not correct", "",
                info.getLogin());
    }

    @Test
    public void testGetEmail() throws Exception {
        UserInfo info = new UserInfo("sth", 1, "sth", "sth");
        assertNotNull("getEmail is not correct",
                info.getEmail());
        assertNotEquals("getEmail is not correct", "",
                info.getEmail());
    }

    @Test
    public void testGetPassword() throws Exception {
        UserInfo info = new UserInfo("sth", 1, "sth", "sth");
        assertNotNull("getPassword is not correct",
                info.getPassword());
        assertNotEquals("getPassword is not correct", "",
                info.getPassword());
    }

    @Test
    public void testGetId() throws Exception {
        UserInfo info = new UserInfo("sth", 1, "sth", "sth");
        assertTrue("getId is not correct", info.getId() > 0);
    }

    @Test
    public void testGetProjects() throws Exception {
        UserInfo info = new UserInfo("sth", 1, "sth", "sth");
        assertNotNull("getProjects is not correct",
                info.getProjects());
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
