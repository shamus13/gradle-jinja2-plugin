package dk.grixie.jinja2.gradle.plugin;

import org.gradle.api.Project;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class Jinja2PluginTest {

    @Mock
    RegularFileProperty templateFileProperty;

    @Mock
    RegularFileProperty outputFileProperty;

    public static final String PLUGIN_ID = "dk.grixie.jinja2";

    @Test
    public void greetingTest() {
        Project project = ProjectBuilder.builder().build();
        project.getPluginManager().apply(PLUGIN_ID);

        Jinja2PluginExtension extension = (Jinja2PluginExtension) project.getExtensions().getByName("jinja2");
        extension.setTemplate(templateFileProperty);
        extension.setDictionary(Map.of("from", "me", "to", "you"));
        extension.setOutput(outputFileProperty);

        assertThat(project.getPluginManager()).isNotNull();
        assertThat(project.getPluginManager().hasPlugin(PLUGIN_ID)).isTrue();

        assertThat(project.getTasks()).isNotNull();
        assertThat(project.getTasks().getByName("jinja2")).isNotNull();
    }
}
