package dk.grixie.jinja2.engine;

import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.loader.FileLocator;
import dk.grixie.jinja2.gradle.exception.Jinja2Exceptions;
import org.gradle.api.file.RegularFileProperty;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TemplateEngine {

    public String generate(final String template, final Map<String, Object> dictionary) {
        Jinjava jinjava = new Jinjava();

        jinjava.setResourceLocator(new FileLocator());

        return jinjava.render(template, dictionary);
    }

    public void generate(final RegularFileProperty templateFile,
                         final Map<String, Object> dictionary,
                         final RegularFileProperty outputFile) {
        try {
            String template = new BufferedReader(new FileReader(templateFile.getAsFile().get())).lines().collect(Collectors.joining());

            String content = generate(template, new HashMap<>(dictionary));

            File file = outputFile.getAsFile().get();

            makeParents(file.getParentFile());

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            writer.write(content);

            writer.close();
        } catch (IOException e) {
            throw new Jinja2Exceptions(e);
        }
    }

    private void makeParents(File current) {
        List<File> directories = new ArrayList<>();

        while (current != null && !current.exists() && !directories.contains(current)) {
            directories.add(0, current);

            current = current.getParentFile();
        }

        for (File file : directories) {
            file.mkdir();
        }
    }
}
