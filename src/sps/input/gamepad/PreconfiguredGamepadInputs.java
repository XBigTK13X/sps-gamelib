package sps.input.gamepad;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.io.FileUtils;
import sps.core.Loader;
import sps.core.Logger;
import sps.util.JSON;

import java.io.File;
import java.util.*;

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
                if (!config.getName().contains("~") && !config.getName().contains(".DS_Store")) {
                    JsonObject json = JSON.getObject(FileUtils.readFileToString(config));
                    JsonObject rawVariables = json.get("vars").getAsJsonObject();

                    String title = json.get("title").getAsString();
                    __hardwareNames.put(title, new ArrayList<String>());
                    for (JsonElement el : rawVariables.get("hardwareNames").getAsJsonArray()) {
                        __hardwareNames.get(title).add(el.getAsString());
                    }
                    __inputs.put(title, new HashMap<String, GamepadInput>());
                    JsonArray bindings = json.getAsJsonArray("bindings");
                    for (JsonElement binding : bindings) {
                        String inputName = binding.getAsJsonObject().get("id").getAsString();
                        __inputs.get(title).put(inputName, GamepadInput.parse(binding.toString(), rawVariables.get("deadZone").getAsFloat()));
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