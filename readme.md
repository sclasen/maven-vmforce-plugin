#maven-vmforce-plugin

This is a maven plugin that lets you execute cliforce scripts as part of your maven build.

It can be used to set up before and tear down after functional tests run by maven, or for any other purpose for which you
can use a force script.

##configuration

Here is an example configuration that could be added to your pom.xml to cause a force script to be executed
before tests are run.

            <plugin>
                <groupId>com.force.maven.plugin</groupId>
                <artifactId>maven-vmforce-plugin</artifactId>
                <configuration>
                    <forceScript>target/test-classes/cleanAndSetupTestEnv.fs</forceScript>
                </configuration>
                <executions>
                    <execution>
                        <phase>process-test-classes</phase>
                        <goals>
                            <goal>force</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>

##using maven resource filtering on scripts

If you configure your maven pom.xml correctly you can use maven resource filtering on script files.

For instance if you would like to have your webapp deployed every time you do a build, you could place the following line
in src/test/resources/deploy.fs

    push -n ${project.artifactId} -p target/${project.artifactId}-${project.version}.${project.packaging}

then configure the plugin to use that script, and then configure your pom.xml to do filtering on that directory.

     <build>
     ...
     <testResources>
            <testResource>
                <directory>src/test/resources</directory>
                <filtering>true</filtering>
            </testResource>
     </testResources>
     ...
     </build>

In this way you wont have to update the script anytime your project version changes.

###plugin group

To make this plugin easy to use via the command line you should add com.force.maven.plugin as a plugin group
to your maven settings.xml

    <pluginGroups>
    ...
        <pluginGroup>com.force.maven.plugin</pluginGroup>
    ...
    </pluginGroups>

This will shorten the command you have to type from

    mvn com.force.maven.plugin:maven-vmforce-plugin:force -Dforce.script=path/to/script

to

    mvn vmforce:force -Dforce.script=path/to/script