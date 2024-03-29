package dk.grixie.jinja2.gradle.plugin;

import dk.grixie.jinja2.engine.TemplateEngine;
import dk.grixie.jinja2.io.EngineFileUtility;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.util.Optional;

public class Jinja2Plugin implements Plugin<Project> {

    private final EngineFileUtility engineFileUtility;
    private final TemplateEngine templateEngine;

    public Jinja2Plugin() {
        this.engineFileUtility = new EngineFileUtility();
        this.templateEngine = new TemplateEngine();
    }

    @Override
    public void apply(Project project) {
        project.getExtensions().create("jinja2", Jinja2PluginExtension.class);

        project.task("jinja2")
                .doLast(task -> {
                            task.setGroup("Jinja2");
                            task.setDescription("Generates files based on a Jinja2 template and dictionary file. Configured through the jinja2 extension.");

                            Jinja2PluginExtension data = Optional.ofNullable(
                                            (Jinja2PluginExtension) task.getProject().getExtensions().findByName("jinja2"))
                                    .orElse(new Jinja2PluginExtension());

                            String template = engineFileUtility.readTemplateFile(engineFileUtility.getInputStreamReader(data.getTemplate()));
                            String content = templateEngine.generate(template, data.getDictionary());

                            engineFileUtility.makeParents(data.getOutput());

                            engineFileUtility.writeContent(data.getOutput(), content);
                        }
                );
    }
}