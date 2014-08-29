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
import java.util.*;

public class InputBindings {
    private InputBindings() {

    }

    private static boolean __areLoaded;

    public static boolean areLoaded() {
        return __areLoaded;
    }

    public static void updateConfig() {
        List<Map<String, Object>> commandBindings = new LinkedList<>();
        for (Command command : Commands.values()) {
            String commandName = command.name();
            List<String> keyboardChord = new LinkedList<>();
            for (int ii = 0; ii < command.keys().size(); ii++) {
                keyboardChord.add(command.keys().get(ii).name());
            }

            Map<String, Object> mapping = new HashMap<>();
            mapping.put("command", commandName);
            List<Map<String, Object>> bindings = new LinkedList<>();
            if (keyboardChord.size() > 0) {
                Map<String, Object> chordMap = new HashMap<>();
                chordMap.put("type", "keyboard");
                chordMap.put("chord", keyboardChord);
                bindings.add(chordMap);
            }

            for (String gamepad : command.supportedGamepads()) {
                List<GamepadInput> gamepadInputs = command.controllerInput(gamepad);
                List<Object> gamepadChord = new LinkedList<>();
                for (GamepadInput gamepadInput : gamepadInputs) {
                    if (gamepad.equalsIgnoreCase("sps-autoconf")) {
                        gamepadChord.add(gamepadInput.getSerializable());
                    }
                    else {
                        gamepadChord.add(gamepadInput.getName());
                    }
                }
                Map<String, Object> chordMap = new HashMap<>();
                chordMap.put("type", gamepad);
                chordMap.put("chord", gamepadChord);
                bindings.add(chordMap);
            }
            mapping.put("bindings", bindings);
            commandBindings.add(mapping);
        }
        SpsConfig.get().commandBindings = commandBindings;
        SpsConfig.getInstance().apply();
        SpsConfig.getInstance().save();
    }

    public static void bindAll() {
        for (Map<String, Object> commandBindings : SpsConfig.get().commandBindings) {
            String command = commandBindings.get("command").toString();
            List<Map<String, Object>> castedBindings = (List<Map<String, Object>>) commandBindings.get("bindings");
            for (Map<String, Object> binding : castedBindings) {
                String chordType = binding.get("type").toString();
                List<Object> chord = (List<Object>) binding.get("chord");
                InputBindings.bind(command, chordType, chord);
            }
        }
        __areLoaded = true;
    }

    private static void bind(String commandName, String chordType, List<Object> rawChord) {
        if (Commands.get(commandName) == null) {
            if (SpsConfig.get().inputLoadLogging) {
                Logger.info("No overrides found for " + commandName + " , setting context to default of All");
            }
            Commands.add(new Command(commandName, Contexts.get("All")));
        }
        List<Keys> chord = new ArrayList<>();
        List<GamepadInput> gamepadInputs = new ArrayList<>();

        for (Object rawControl : rawChord) {
            String controlName = rawControl.toString();
            if (chordType.equalsIgnoreCase("keyboard")) {
                Keys key = Keys.fromName(controlName);
                if (key == null && controlName.length() == 1) {
                    key = Keys.fromChar(controlName.charAt(0));
                }
                if (key == null) {
                    Logger.exception(new RuntimeException("Unable to handle input binding for : '" + commandName + "'. " + "[" + rawControl + "] was not listed as any KeyID."));
                }
                chord.add(key);
            }
            else {
                GamepadInput gamepadInput = PreconfiguredGamepadInputs.get(chordType, controlName);
                if (gamepadInput == null) {
                    gamepadInput = GamepadInput.fromSerializable(rawControl, null);
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