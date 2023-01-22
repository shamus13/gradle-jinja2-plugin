package dk.grixie.jinja2.engine;

import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TemplateEngineTest {

    @Test
    public void testGenerateSingleLineTemplate() {
        TemplateEngine engine = new TemplateEngine();

        String content = engine.generate("hello {{ to }} from {{ from }}.", Map.of("to", "Alice", "from", "Bob"));

        assertThat(content).isEqualTo("hello Alice from Bob.");
    }

    @Test
    public void testGenerateTwoLineTemplate() {
        TemplateEngine engine = new TemplateEngine();

        String content = engine.generate("hello {{ to }} from {{ from }}.\n" +
                "Best regards to {{ regards }} as well.", Map.of("to", "Alice", "from", "Bob", "regards", "your friend"));

        assertThat(content).isEqualTo("hello Alice from Bob.\nBest regards to your friend as well.");
    }

    @Test
    public void testGenerateBlankLineTemplate() throws IOException {
        TemplateEngine engine = new TemplateEngine();

        String content = engine.generate("\nhello {{ to }} from {{ from }}.\n\n" +
                "Best regards to {{ regards }} as well.\n", Map.of("to", "Alice", "from", "Bob", "regards", "your friend"));

        assertThat(content).isEqualTo("\nhello Alice from Bob.\n\nBest regards to your friend as well.\n");
    }
}
