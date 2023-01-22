package dk.grixie.jinja2.engine;

import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.loader.FileLocator;
import dk.grixie.jinja2.gradle.exception.Jinja2Exceptions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TemplateEngine {

    public String generate(final String template, final Map<String, Object> dictionary) {
        Jinjava jinjava = new Jinjava();

        jinjava.setResourceLocator(new FileLocator());

        return jinjava.render(template, dictionary);
    }

    public void generate(final InputStreamReader templateFile,
                         final Map<String, Object> dictionary,
                         final OutputStreamWriter outputFile) {
        try {
            String template = new BufferedReader(templateFile).lines().collect(Collectors.joining("\n"));

            String content = generate(template, new HashMap<>(dictionary));

            BufferedWriter writer = new BufferedWriter(outputFile);

            writer.write(content);

            writer.close();
        } catch (IOException e) {
            throw new Jinja2Exceptions(e);
        }
    }
}
