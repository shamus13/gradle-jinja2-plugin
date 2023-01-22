package dk.grixie.jinja2.io;

import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Provider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EngineFileUtilityTest {

    @Mock
    Provider<File> outputFileProvider;

    @Mock
    RegularFileProperty output;

    @Test
    public void testMakeExistingOutputDirectory() {
        File outputFile = new File("build");
        when(outputFileProvider.get()).thenReturn(outputFile);
        when(output.getAsFile()).thenReturn(outputFileProvider);

        EngineFileUtilility engineFileUtilility = new EngineFileUtilility();
        engineFileUtilility.makeParents(output);

        assertThat(outputFile).exists();
    }

    @Test
    public void testMakeNotNestedOutputDirectory() {
        File outputFile = new File("build/testCreateDeeplyNestedOutputDirectory");
        when(outputFileProvider.get()).thenReturn(outputFile);
        when(output.getAsFile()).thenReturn(outputFileProvider);

        EngineFileUtilility engineFileUtilility = new EngineFileUtilility();
        engineFileUtilility.makeParents(output);

        assertThat(outputFile).exists();
    }

    @Test
    public void testMakeDeeplyNestedOutputDirectory() {
        File outputFile = new File("build/a/b/c/d/e/f/testCreateDeeplyNestedOutputDirectory");
        when(outputFileProvider.get()).thenReturn(outputFile);
        when(output.getAsFile()).thenReturn(outputFileProvider);

        EngineFileUtilility engineFileUtilility = new EngineFileUtilility();
        engineFileUtilility.makeParents(output);

        assertThat(outputFile).exists();
    }
}
