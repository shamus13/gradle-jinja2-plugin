package dk.grixie.jinja2.gradle.task;

import dk.grixie.jinja2.engine.TemplateEngine;
import dk.grixie.jinja2.io.EngineFileUtility;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.TaskAction;

import java.util.Map;

public class Jinja2Task extends DefaultTask {

    RegularFileProperty template;

    RegularFileProperty output;

    Map<String, Object> dictionary;

    private final EngineFileUtility engineFileUtility;
    private final TemplateEngine templateEngine;

    public Jinja2Task() {
        this.engineFileUtility = new EngineFileUtility();
        this.templateEngine = new TemplateEngine();
    }

    @TaskAction
    public void generate() {
        String t = engineFileUtility.readTemplateFile(engineFileUtility.getInputStreamReader(template));

        engineFileUtility.makeParents(output);

        String content = templateEngine.generate(t, dictionary);

        engineFileUtility.writeContent(output, content);
    }
}
