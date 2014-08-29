package com.simplepathstudios.gamelib.input.gamepad;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.constructor.Constructor;

import java.util.List;
import java.util.Map;

public class PreconfiguredGamepadData {
    private static Constructor __yamlConstructor;

    public static Constructor getYamlConstructor() {
        if (__yamlConstructor == null) {
            __yamlConstructor = new Constructor(PreconfiguredGamepadData.class);
            TypeDescription gamepadData = new TypeDescription(PreconfiguredGamepadData.class);
            gamepadData.putListPropertyType("hardwareNames", String.class);
            gamepadData.putMapPropertyType("bindings", String.class, Object.class);
            __yamlConstructor.addTypeDescription(gamepadData);
        }
        return __yamlConstructor;
    }

    public PreconfiguredGamepadData(){

    }

    public String title;
    public Float deadZone;
    public List<String> hardwareNames;
    public List<Map<String,Object>> bindings;
}
