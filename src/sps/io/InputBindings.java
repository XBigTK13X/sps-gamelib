package sps.io;

import org.apache.commons.io.FileUtils;
import sps.bridge.Command;
import sps.bridge.Commands;
import sps.bridge.Contexts;
import sps.bridge.Sps;
import sps.core.Loader;
import sps.core.Logger;

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
            if (command.keys().length == 1) {
                chord = command.keys()[0].name();
            }
            if (command.keys().length > 1) {
                for (int ii = 0; ii < command.keys().length; ii++) {
                    chord += command.keys()[ii];
                    if (ii < command.keys().length - 1) {
                        chord += "+";
                    }
                }
            }
            String controller = command.controllerInput() == null ? "NULL" : command.controllerInput().serialize();
            result.add(command.name() + "," + chord + "-" + controller);
        }
        return result;
    }

    private static void fromConfig(List<String> config) {
        try {
            for (String line : config) {
                if (!line.contains("##") && line.length() > 1) {
                    String key = line.split(",")[0];
                    String value = line.split(",")[1];
                    String[] rawChord = value.split("-")[0].split("\\+");

                    Keys[] chord = new Keys[rawChord.length];
                    String keyNotFound = "";
                    for (int ii = 0; ii < rawChord.length; ii++) {
                        chord[ii] = Keys.get(rawChord[ii]);
                        if (chord[ii] == null) {
                            keyNotFound = "[" + rawChord[ii] + "] was not listed as any KeyID.";
                        }
                    }

                    ControllerInput controllerInput = ControllerInput.parse(value.split("-")[1]);
                    if (controllerInput == null && !keyNotFound.isEmpty()) {
                        Logger.exception(new RuntimeException("Unable to parse input config: '" + line + "'. " + keyNotFound));
                    }

                    //Unless otherwise defined in bridge.cfg already,
                    // init a new binding to always lock after 1 pressF
                    if (Commands.get(key) == null) {
                        Commands.add(new Command(key, Contexts.get(Sps.Contexts.All)));
                    }
                    Commands.get(key).bind(controllerInput, chord);

                }
            }
        }
        catch (Exception e) {
            Logger.exception(e);
        }
    }
}