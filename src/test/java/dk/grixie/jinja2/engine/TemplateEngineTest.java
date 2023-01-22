package dk.grixie.jinja2.engine;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TemplateEngineTest {

    @Test
    public void testGenerateSingleLineTemplate() throws IOException {
        TemplateEngine engine = new TemplateEngine();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        engine.generate(new InputStreamReader(TemplateEngineTest.class.getResourceAsStream("/single-line-template.j2")),
                Map.of("to", "Alice", "from", "Bob"),
                new OutputStreamWriter(byteArrayOutputStream)
        );

        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())));

        assertThat(reader.readLine()).isEqualTo("hello Alice from Bob.");
        assertThat(reader.readLine()).isNull();
    }

    @Test
    public void testGenerateTwoLineTemplate() throws IOException {
        TemplateEngine engine = new TemplateEngine();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        engine.generate(new InputStreamReader(TemplateEngineTest.class.getResourceAsStream("/double-line-template.j2")),
                Map.of("to", "Alice", "from", "Bob", "regards", "your friend"),
                new OutputStreamWriter(byteArrayOutputStream)
        );

        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())));

        assertThat(reader.readLine()).isEqualTo("hello Alice from Bob.");
        assertThat(reader.readLine()).isEqualTo("Best regards to your friend as well.");
        assertThat(reader.readLine()).isNull();
    }

    @Test
    public void testGenerateBlankLineTemplate() throws IOException {
        TemplateEngine engine = new TemplateEngine();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        engine.generate(new InputStreamReader(TemplateEngineTest.class.getResourceAsStream("/blank-lines-template.j2")),
                Map.of("to", "Alice", "from", "Bob", "regards", "your friend"),
                new OutputStreamWriter(byteArrayOutputStream)
        );

        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())));

        assertThat(reader.readLine()).isEqualTo("");
        assertThat(reader.readLine()).isEqualTo("hello Alice from Bob.");
        assertThat(reader.readLine()).isEqualTo("");
        assertThat(reader.readLine()).isEqualTo("Best regards to your friend as well.");
        assertThat(reader.readLine()).isEqualTo("");
        assertThat(reader.readLine()).isNull();
    }
}
