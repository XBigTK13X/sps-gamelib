package com.simplepathstudios.gamelib.util;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class YAML {
    public static Object getObject(String raw, Constructor constructor) {
        Yaml parser = new Yaml(constructor);
        return parser.load(raw);
    }

    public static String toString(Object yaml) {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        return new Yaml(options).dump(yaml);
    }
}
