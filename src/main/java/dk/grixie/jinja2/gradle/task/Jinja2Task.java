package dk.grixie.jinja2.gradle.task;

import dk.grixie.jinja2.engine.TemplateEngine;
import dk.grixie.jinja2.io.EngineFileUtilility;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.TaskAction;

import java.util.Map;

public class Jinja2Task extends DefaultTask {

    RegularFileProperty template;

    RegularFileProperty output;

    Map<String, Object> dictionary;

    private final EngineFileUtilility engineFileUtilility;
    private final TemplateEngine templateEngine;

    public Jinja2Task() {
        this.engineFileUtilility = new EngineFileUtilility();
        this.templateEngine = new TemplateEngine();
    }

    @TaskAction
    public void generate() {
        engineFileUtilility.makeParents(output);

        templateEngine.generate(engineFileUtilility.getInputStreamReader(template),
                dictionary, engineFileUtilility.getOutputStreamWriter(output));
    }
}
