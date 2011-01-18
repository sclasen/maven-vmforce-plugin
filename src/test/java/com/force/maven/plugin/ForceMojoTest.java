package com.force.maven.plugin;


import org.junit.Test;

import java.io.File;

public class ForceMojoTest {

    @Test
    public void testPlugin() throws Exception {
        ForceMojo mojo = new ForceMojo();
        mojo.forceScript = new File("target/test-classes/testForceScript.fs");
        mojo.execute();
    }

}
