package dk.grixie.jinja2.io;

import dk.grixie.jinja2.gradle.exception.Jinja2Exceptions;
import org.gradle.api.file.RegularFileProperty;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class EngineFileUtilility {

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

    public OutputStreamWriter getOutputStreamWriter(final RegularFileProperty fileProperty) {
        try {
            return new FileWriter(fileProperty.getAsFile().get());
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
