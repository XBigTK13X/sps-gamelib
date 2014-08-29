package com.simplepathstudios.gamelib.input.gamepad;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.simplepathstudios.gamelib.core.Loader;
import com.simplepathstudios.gamelib.core.Logger;
import com.simplepathstudios.gamelib.util.YAML;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreconfiguredGamepadInputs {
    private static Map<String, Map<String, GamepadInput>> __inputs;
    private static Map<String, List<String>> __hardwareNames;

    public static Map getAll() {
        if (__inputs == null) {
            init();
        }
        return __inputs;
    }

    private static void init() {
        __inputs = new HashMap<>();
        __hardwareNames = new HashMap<>();
        try {
            for (File config : Loader.get().data("gamepad").listFiles()) {
                Logger.info("Reading gamepad config: " + config.getAbsolutePath());
                if (config.getName().contains(".yaml") && !config.getName().contains("~") && !config.getName().contains(".DS_Store")) {
                    String yaml = FileUtils.readFileToString(config);
                    PreconfiguredGamepadData gamepadData = (PreconfiguredGamepadData) YAML.getObject(yaml, PreconfiguredGamepadData.getYamlConstructor());

                    __hardwareNames.put(gamepadData.title,gamepadData.hardwareNames);

                    __inputs.put(gamepadData.title, new HashMap<>());
                    for (Map<String,Object> binding : gamepadData.bindings) {
                        __inputs.get(gamepadData.title).put(binding.get("id").toString(), GamepadInput.fromSerializable(binding, gamepadData.deadZone));
                    }
                }
            }
        }
        catch (Exception e) {
            Logger.exception(e);
        }
    }

    public static String getTypeFromName(int controllerIndex) {
        if (__hardwareNames == null) {
            init();
        }
        Controller gamepad = Controllers.getControllers().get(controllerIndex);
        for (String key : __hardwareNames.keySet()) {
            for (String name : __hardwareNames.get(key)) {
                if (gamepad.getName().toLowerCase().contains(name)) {
                    //TODO Verify that bluetooth ps3 connected mapping is correct
                    if(key.equalsIgnoreCase("ps3") && SystemUtils.IS_OS_LINUX){
                        return "xbox360";
                    }
                    return key;
                }
            }
        }
        return "sps-autoconf";
    }

    public static GamepadInput get(String type, String controlName) {
        if (__inputs == null) {
            init();
        }
        if (__inputs.containsKey(type)) {
            for (String controlKey : __inputs.get(type).keySet()) {
                if (controlKey.equalsIgnoreCase(controlName)) {
                    return __inputs.get(type).get(controlKey);
                }
            }
        }
        return null;
    }
}
