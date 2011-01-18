package com.force.maven.plugin;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.force.cliforce.CLIForce;
import com.force.cliforce.ForceEnv;
import com.sforce.ws.ConnectionException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import javax.servlet.ServletException;
import java.io.*;

/**
 * Execute a force script as part of a maven build.
 *
 * @goal force
 * @phase process-test-classes
 */
public class ForceMojo
        extends AbstractMojo {

    /**
     * Force environment name.
     *
     * @parameter expression="${force.env}"
     */
    String forceEnv;

    /**
     * Location of the file.
     *
     * @parameter expression="${force.script}"
     * @required
     */
    File forceScript;

    public void execute()
            throws MojoExecutionException {
        if (forceScript.exists()) {

            try {
                FileInputStream in = new FileInputStream(forceScript);
                ForceEnv env = new ForceEnv(forceEnv);
                final CLIForce cliForce = new CLIForce(env);
                cliForce.init(in, new PrintWriter(System.out, true));
                cliForce.run();
                Thread.sleep(1000);
                System.out.println();
            } catch (FileNotFoundException e) {
                getLog().error("forceScript file not found", e);
            } catch (ConnectionException e) {
                getLog().error("error initializing force connection", e);
            } catch (ServletException e) {
                getLog().error("error initializing force connection", e);
            } catch (IOException e) {
                getLog().error("error initializing force connection", e);
            } catch (InterruptedException e) {
                getLog().error(e);
            }

        } else {
            getLog().error(String.format("forceScript file: %s does not exist", forceScript.getAbsolutePath()));
        }
    }
}
