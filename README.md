# gradle-jinja2-plugin
Gradle plugin for generating files based on Jinja2 templates.

Jinja2 templates are widely used by Ansible scripts to generate configuration
files for applications, logging and much more.

This plugin makes it possible to use Jinja2 template files as part of a build.
As such Jinja templates created for Ansible deployment can be reused to for example
generate sample configurations to be included in a releases, test configurations etc.


Basic Jinja2 template file:
````
hello {{ to }} from {{ from }}.
````

Using the Jinja2 default task:
````
def dict = [ 'from': 'Alice', 'to': 'Bob']
def templateFile = file("$projectDir/src/main/resources/template.j2")
def outputFile = file("$projectDir/build/generated/dummy.txt")

jinja2 {
    template = objects.fileProperty().fileValue(templateFile)
    dictionary = dict
    output = objects.fileProperty().fileValue(outputFile)
}

compileJava.dependsOn tasks.jinja2
````

Defining a custom Jinja2 task:
````
def dict = [ 'from': 'Alice', 'to': 'Bob']
def templateFile = file("$projectDir/src/main/resources/template.j2")
def outputFile = file("$projectDir/build/generated/dummy.txt")

tasks.register("myJinja2Task", Jinja2Task) {
    template = objects.fileProperty().fileValue(templateFile)
    dictionary = dict
    output = objects.fileProperty().fileValue(outputFile)
}

compileJava.dependsOn tasks.myJinja2Task
````
