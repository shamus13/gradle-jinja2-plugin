package dk.grixie.jinja2.engine;

import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.loader.FileLocator;
import java.util.Map;

public class TemplateEngine {

    public String generate(final String template, final Map<String, Object> dictionary) {
        Jinjava jinjava = new Jinjava();

        jinjava.setResourceLocator(new FileLocator());

        return jinjava.render(template, dictionary);
    }
}
