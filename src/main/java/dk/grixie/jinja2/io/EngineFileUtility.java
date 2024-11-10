package dk.grixie.jinja2.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

import dk.grixie.jinja2.gradle.exception.Jinja2Exceptions;

public class EngineFileUtility {
    private final Logger logger = Logging.getLogger(getClass());

    public String readTemplateFile(final InputStreamReader inputStreamReader) {

        try {
            StringBuilder stringBuilder = new StringBuilder();

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            char[] buf = new char[3];
            int count;

            while ((count = bufferedReader.read(buf)) != -1) {
                stringBuilder.append(buf, 0, count);
            }

            return stringBuilder.toString();
        } catch (IOException e) {
            throw new Jinja2Exceptions(e);
        }
    }

    public void makeParents(final RegularFileProperty fileProperty) {
        File file = fileProperty.getAsFile().get();

        File parent = file.getParentFile();
        if (parent == null) {
            logger.warn("Failed to read parent directory of {}", file.getPath());
            return;
        }

        if (parent.exists()) {
            logger.debug("Directory {} already exists", parent.getPath());
            return;
        }

        if (parent.mkdirs()) {
            logger.debug("Successfully created directory", parent.getPath());
            return;
        }

        throw new IllegalArgumentException("Filed to create directory " + parent.getPath());
    }

    public void writeContent(final RegularFileProperty fileProperty, final String content) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileProperty.getAsFile().get()));
            bufferedWriter.write(content);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new Jinja2Exceptions(e);
        }
    }

    public InputStreamReader getInputStreamReader(final RegularFileProperty fileProperty) {
        try {
            return new FileReader(fileProperty.getAsFile().get());
        } catch (IOException e) {
            throw new Jinja2Exceptions(e);
        }

    }
}
