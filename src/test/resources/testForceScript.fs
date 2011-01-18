plugin -a cliplugin -v 1.0
cliplugin:hello
unplug cliplugin
help ${plugin}
help ${project.artifactId}-${project.version}.${project.packaging}
list
apps
exit