package com.simplepathstudios.gamelib.input;

import com.simplepathstudios.gamelib.bridge.Command;
import com.simplepathstudios.gamelib.bridge.Commands;
import com.simplepathstudios.gamelib.bridge.Contexts;
import com.simplepathstudios.gamelib.bridge.Sps;
import com.simplepathstudios.gamelib.core.Loader;
import com.simplepathstudios.gamelib.core.Logger;
import com.simplepathstudios.gamelib.data.SpsConfig;
import com.simplepathstudios.gamelib.input.gamepad.GamepadInput;
import com.simplepathstudios.gamelib.input.gamepad.PreconfiguredGamepadInputs;
import com.simplepathstudios.gamelib.util.JSON;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InputBindings {
    private InputBindings() {

    }

    private static boolean __areLoaded;

    public static boolean areLoaded() {
        return __areLoaded;
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

    public static void bindAll() {
        for (Map<String, Object> commandBindings : SpsConfig.get().inputBindings) {
            String command = commandBindings.get("command").toString();
            List<Map<String, Object>> castedBindings = (List<Map<String, Object>>) commandBindings.get("bindings");
            for (Map<String, Object> binding : castedBindings) {
                String chordType = binding.get("type").toString();
                List<String> chord = (List<String>) binding.get("chord");
                InputBindings.bind(command, chordType, chord);
            }
        }
        __areLoaded = true;
    }

    private static void bind(String commandName, String chordType, List<String> rawChord) {
        if (Commands.get(commandName) == null) {
            if (SpsConfig.get().inputLoadLogging) {
                Logger.info("No overrides found for " + commandName + " , setting context to default of All");
            }
            Commands.add(new Command(commandName, Contexts.get("All")));
        }
        List<Keys> chord = new ArrayList<>();
        List<GamepadInput> gamepadInputs = new ArrayList<>();

        for (String rawControl : rawChord) {
            if (chordType.equalsIgnoreCase("keyboard")) {
                Keys key = Keys.fromName(rawControl);
                if (key == null && rawControl.length() == 1) {
                    key = Keys.fromChar(rawControl.charAt(0));
                }
                if (key == null) {
                    Logger.exception(new RuntimeException("Unable to handle input binding for : '" + commandName + "'. " + "[" + rawControl + "] was not listed as any KeyID."));
                }
                chord.add(key);
            }
            else {
                GamepadInput gamepadInput = PreconfiguredGamepadInputs.get(chordType, rawControl);
                if (gamepadInput == null) {
                    gamepadInput = GamepadInput.parse(rawControl, null);
                }
                if (gamepadInput != null) {
                    gamepadInputs.add(gamepadInput);
                }
            }
        }

        if (chord.size() > 0) {
            if (SpsConfig.get().inputLoadLogging) {
                Logger.info("Binding " + commandName + " to keyboard " + chord);
            }
            Commands.get(commandName).bind(chord);
        }
        if (gamepadInputs.size() > 0) {
            if (SpsConfig.get().inputLoadLogging) {
                Logger.info("Binding " + commandName + " to gamepad " + gamepadInputs + " of type " + chordType);
            }
            Commands.get(commandName).bind(chordType, gamepadInputs);
        }
    }
}