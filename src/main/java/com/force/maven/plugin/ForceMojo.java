package com.force.maven.plugin;


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
            String msg = String.format("forceScript file: %s does not exist", forceScript.getAbsolutePath());
            getLog().error(msg);
            throw new MojoExecutionException(msg);
        }
    }
}
