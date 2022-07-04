package dk.grixie.jinja2.engine;

import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Provider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TemplateEngineTest {

    @Mock
    Provider<File> templateFileProvider;

    @Mock
    RegularFileProperty template;

    @Mock
    Provider<File> outputFileProvider;

    @Mock
    RegularFileProperty output;

    @Test
    public void testSimpleInlineTemplate() {
        TemplateEngine engine = new TemplateEngine();

        String message = engine.generate("I am {{ me }}, and you are {{ you }}.", Map.of("me", "Alice", "you", "Bob"));

        assertThat(message).isEqualTo("I am Alice, and you are Bob.");
    }

    @Test
    public void testCreateParentDirectories() {
        TemplateEngine engine = new TemplateEngine();

        when(template.getAsFile()).thenReturn(templateFileProvider);
        when(templateFileProvider.get()).thenReturn(new File(TemplateEngineTest.class.getResource("/template.j2").getFile()));

        when(output.getAsFile()).thenReturn(outputFileProvider);

        File outputFile = new File("build/generated/template/testCreateParentDirectoriesOutput");
        when(outputFileProvider.get()).thenReturn(outputFile);

        engine.generate(template,
                Map.of("to", "Alice", "from", "Bob"),
                output
        );

        assertThat(outputFile).exists();
    }
}
