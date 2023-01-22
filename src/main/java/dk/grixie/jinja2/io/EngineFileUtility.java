package dk.grixie.jinja2.io;

import dk.grixie.jinja2.gradle.exception.Jinja2Exceptions;
import org.gradle.api.file.RegularFileProperty;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class EngineFileUtility {

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
        List<File> directories = new ArrayList<>();

        File current = fileProperty.getAsFile().get();

        while (current != null && !current.exists() && !directories.contains(current)) {
            directories.add(0, current);

            current = current.getParentFile();
        }

        for (File file : directories) {
            file.mkdir();
        }
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
