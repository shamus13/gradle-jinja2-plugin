package dk.grixie.jinja2.gradle.task;

import dk.grixie.jinja2.engine.TemplateEngine;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.TaskAction;

import java.util.Map;

public class Jinja2Task extends DefaultTask {

    RegularFileProperty template;

    RegularFileProperty output;

    Map<String, Object> dictionary;

    private final TemplateEngine templateEngine;

    public Jinja2Task() {
        this.templateEngine = new TemplateEngine();
    }

    @TaskAction
    public void generate() {
        templateEngine.generate(template, dictionary, output);
    }
}
