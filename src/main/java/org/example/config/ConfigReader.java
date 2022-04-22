package org.example.config;

import org.example.helpers.Serializer;
import org.example.models.Config;

import java.io.File;

public class ConfigReader {

    String environmentInUse = System.getProperty("env", "dev");
//
    public Config getConfig() {
        Serializer serializer = new Serializer();
        File yaml = new File("src/test/resources/org/example/environments/"+environmentInUse+".yaml");
        Config o = serializer.deserializeYaml(yaml, Config.class);

        return o;
    }
}
