package dk.grixie.jinja2.gradle.plugin;

import dk.grixie.jinja2.engine.TemplateEngine;
import dk.grixie.jinja2.io.EngineFileUtilility;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.util.Optional;

public class Jinja2Plugin implements Plugin<Project> {

    private final EngineFileUtilility engineFileUtilility;
    private final TemplateEngine templateEngine;

    public Jinja2Plugin() {
        this.engineFileUtilility = new EngineFileUtilility();
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

                            engineFileUtilility.makeParents(data.getOutput());

                            templateEngine.generate(engineFileUtilility.getInputStreamReader(data.getTemplate()),
                                    data.getDictionary(), engineFileUtilility.getOutputStreamWriter(data.getOutput()));
                        }
                );
    }
}