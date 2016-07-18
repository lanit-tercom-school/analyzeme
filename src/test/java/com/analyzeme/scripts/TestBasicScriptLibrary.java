package com.analyzeme.scripts;

import org.junit.Test;

public class TestBasicScriptLibrary {

    @Test
    public void test() throws Exception {
        BasicScriptLibrary library = new BasicScriptLibrary();
        System.out.println(library.getAllScriptsNames());
    }
}
