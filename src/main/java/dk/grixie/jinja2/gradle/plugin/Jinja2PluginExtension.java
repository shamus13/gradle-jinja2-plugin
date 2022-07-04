package dk.grixie.jinja2.gradle.plugin;

import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputFile;

import java.util.Map;

public class Jinja2PluginExtension {

    @InputFile
    private RegularFileProperty template;

    @OutputFile
    private RegularFileProperty output;

    @Input
    private Map<String, Object> dictionary;

    public Jinja2PluginExtension() {
    }

    public RegularFileProperty getTemplate() {
        return template;
    }

    public void setTemplate(RegularFileProperty template) {
        this.template = template;
    }

    public RegularFileProperty getOutput() {
        return output;
    }

    public void setOutput(RegularFileProperty output) {
        this.output = output;
    }

    public Map<String, Object> getDictionary() {
        return dictionary;
    }

    public void setDictionary(Map<String, Object> dictionary) {
        this.dictionary = dictionary;
    }
}
