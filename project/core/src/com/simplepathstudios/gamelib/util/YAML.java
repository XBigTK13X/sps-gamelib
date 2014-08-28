package com.simplepathstudios.gamelib.util;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class YAML {
    public static Object getObject(String raw, Constructor constructor) {
        Yaml parser = new Yaml(constructor);
        return parser.load(raw);
    }

    public static String toString(Object yaml) {
        return new Yaml().dump(yaml);
    }
}
