package dk.grixie.jinja2.io;

import dk.grixie.jinja2.engine.TemplateEngineTest;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Provider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EngineFileUtilityTest {

    @Mock
    Provider<File> fileProvider;

    @Mock
    RegularFileProperty fileProperty;

    @Test
    public void testReadTemplateFileContainingBlankLines() {
        EngineFileUtility engineFileUtility = new EngineFileUtility();
        String template = engineFileUtility.
                readTemplateFile(new InputStreamReader(TemplateEngineTest.class.getResourceAsStream("/blank-lines-template.j2")));

        assertThat(template).isEqualTo("\n" +
                "hello {{ to }} from {{ from }}.\n" +
                "\n" +
                "Best regards to {{ regards }} as well.\n" +
                "\n");
    }

    @Test
    public void testReadTemplateFileContainingTwoLines() {
        EngineFileUtility engineFileUtility = new EngineFileUtility();
        String template = engineFileUtility.
                readTemplateFile(new InputStreamReader(TemplateEngineTest.class.getResourceAsStream("/double-line-template.j2")));

        assertThat(template).isEqualTo("hello {{ to }} from {{ from }}.\n" +
                "Best regards to {{ regards }} as well.");
    }

    @Test
    public void testReadTemplateFileContainingSingleLine() {
        EngineFileUtility engineFileUtility = new EngineFileUtility();
        String template = engineFileUtility.
                readTemplateFile(new InputStreamReader(TemplateEngineTest.class.getResourceAsStream("/single-line-template.j2")));

        assertThat(template).isEqualTo("hello {{ to }} from {{ from }}.");
    }

    @Test
    public void testMakeExistingOutputDirectory() {
        File outputFile = new File("build");
        when(fileProvider.get()).thenReturn(outputFile);
        when(fileProperty.getAsFile()).thenReturn(fileProvider);

        EngineFileUtility engineFileUtility = new EngineFileUtility();
        engineFileUtility.makeParents(fileProperty);

        assertThat(outputFile).exists();
    }

    @Test
    public void testMakeNotNestedOutputDirectory() {
        File outputFile = new File("build/testCreateDeeplyNestedOutputDirectory");
        File outputDirectory = outputFile.getParentFile();

        when(fileProvider.get()).thenReturn(outputFile);
        when(fileProperty.getAsFile()).thenReturn(fileProvider);

        EngineFileUtility engineFileUtility = new EngineFileUtility();
        engineFileUtility.makeParents(fileProperty);

        assertThat(outputDirectory).exists();
    }

    @Test
    public void testMakeDeeplyNestedOutputDirectory() {
        File outputFile = new File("build/a/b/c/d/e/f/testCreateDeeplyNestedOutputDirectory");
        File outputDirectory = outputFile.getParentFile();

        when(fileProvider.get()).thenReturn(outputFile);
        when(fileProperty.getAsFile()).thenReturn(fileProvider);

        EngineFileUtility engineFileUtility = new EngineFileUtility();
        engineFileUtility.makeParents(fileProperty);

        assertThat(outputDirectory).exists();
    }
}
