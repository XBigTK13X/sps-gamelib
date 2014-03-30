package sps.input;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.io.FileUtils;
import sps.bridge.Command;
import sps.bridge.Commands;
import sps.bridge.Contexts;
import sps.bridge.Sps;
import sps.core.Loader;
import sps.core.Logger;
import sps.input.gamepad.GamepadInput;
import sps.input.gamepad.PreconfiguredGamepadInputs;
import sps.util.JSON;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InputBindings {
    public static void reload(final File config) {
        reload(config, true);
    }

    private static void reload(final File config, boolean fallback) {
        if (config.exists()) {
            try {
                Logger.info("Reading input config: " + config.getAbsolutePath());
                fromConfig(FileUtils.readLines(config));
            }
            catch (Exception e) {
                Logger.exception(e, false);
            }
        }
        else {
            if (fallback) {
                Logger.info("Unable to find " + config.getAbsolutePath() + ". Attempting to load " + Loader.get().data("input.cfg").getAbsolutePath());
                reload(Loader.get().data("input.cfg"), false);
            }
        }
    }


    private InputBindings() {

    }

    public static List<String> toConfig() {
        List<String> result = new ArrayList<>();
        for (Command command : Commands.values()) {
            String chord = "";
            for (int ii = 0; ii < command.keys().size(); ii++) {
                chord += "\"" + command.keys().get(ii) + "\"";
                if (ii < command.keys().size() - 1) {
                    chord += ",";
                }
            }
            String entry = "{";
            entry += JSON.pad("command", command.name()) + ",";
            entry += "bindings:[";
            entry += "{type:\"keyboard\",chord:[" + chord + "]}";
            for (String gamepad : command.supportedGamepads()) {
                String gamepadChord = "";

                List<GamepadInput> gamepadInputs = command.controllerInput(gamepad);
                for (int ii = 0; ii < gamepadInputs.size(); ii++) {
                    if (gamepad.equalsIgnoreCase("sps-autoconf")) {
                        gamepadChord = gamepadInputs.get(0).serialize();
                    }
                    else {
                        gamepadChord = "\"" + gamepadInputs.get(0).getName() + "\"";
                    }
                    if (ii < gamepadInputs.size() - 1) {
                        gamepadChord += ",";
                    }
                }
                entry += ",{type:\"" + gamepad + "\",chord:[" + gamepadChord + "]}";
            }
            entry += "]";
            entry += "]}";
            result.add(entry);
        }
        return result;
    }

    private static void fromConfig(List<String> rawConfig) {
        int lineCount = 0;
        try {
            for (String line : rawConfig) {
                lineCount++;
                if (!line.contains("##") && line.length() > 1) {
                    JsonObject config = config = JSON.getObject(line);

                    String commandName = config.get("command").getAsString();
                    //Unless otherwise defined in bridge.cfg already,
                    // init a new binding to always lock after 1 press
                    if (Commands.get(commandName) == null) {
                        Commands.add(new Command(commandName, Contexts.get(Sps.Contexts.All)));
                    }

                    JsonArray bindings = config.get("bindings").getAsJsonArray();
                    for (JsonElement rawBinding : bindings) {
                        JsonObject binding = rawBinding.getAsJsonObject();
                        String type = binding.get("type").getAsString();
                        String[] rawChord = JSON.getGson().fromJson(binding.get("chord"), String[].class);
                        List<Keys> chord = new ArrayList<>();
                        List<GamepadInput> gamepadInputs = new ArrayList<>();

                        for (String rawControl : rawChord) {
                            if (type.equalsIgnoreCase("keyboard")) {
                                String keyNotFound = "";
                                Keys key = Keys.fromName(rawControl);
                                if (key == null && rawControl.length() == 1) {
                                    key = Keys.fromChar(rawControl.charAt(0));
                                }
                                if (key == null) {
                                    keyNotFound = "[" + rawControl + "] was not listed as any KeyID.";
                                }
                                if (!keyNotFound.isEmpty()) {
                                    Logger.exception(new RuntimeException("Unable to parse input config: '" + line + "'. " + keyNotFound));
                                }
                                if (key != null) {
                                    chord.add(key);
                                }
                            }
                            else {
                                GamepadInput gamepadInput = PreconfiguredGamepadInputs.get(type, rawControl);
                                if (gamepadInput == null) {
                                    gamepadInput = GamepadInput.parse(rawControl, null);
                                }
                                if (gamepadInput != null) {
                                    gamepadInputs.add(gamepadInput);
                                }
                            }
                        }

                        if (chord.size() > 0) {
                            //Logger.info("Binding " + commandName + " to keyboard " + chord);
                            Commands.get(commandName).bind(chord);
                        }
                        if (gamepadInputs.size() > 0) {
                            //Logger.info("Binding " + commandName + " to gamepad " + gamepadInputs);
                            Commands.get(commandName).bind(type, gamepadInputs);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            Logger.info("Failed to parse input config on line: " + lineCount);
            Logger.exception(e);
        }
    }
}