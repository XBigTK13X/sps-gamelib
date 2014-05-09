package com.simplepathstudios.gamelib.bridge;

import com.simplepathstudios.gamelib.core.Logger;
import com.simplepathstudios.gamelib.input.InputBindings;

import java.util.*;

public class Commands {
    private static Map<String, Command> commands;
    private static List<Command> values;
    private static String __current = "";

    private static void init(){
        if (commands == null) {
            commands = new HashMap<>();
            values = new LinkedList<>();
        }
    }

    public static void clear() {
        init();
    }

    public static Command get(String name) {
        init();
        __current = name.toLowerCase();
        if(!commands.containsKey(__current) && InputBindings.areLoaded()){
            Logger.exception(new RuntimeException("Input binding not set for command -> "+name));
        }
        return commands.get(__current);
    }

    public static void add(Command command) {
        init();
        commands.put(command.name().toLowerCase(), command);
    }

    public static List<Command> values() {
        init();
        if (values == null || values.size() != commands.entrySet().size()) {
            values = new LinkedList<>();
            for (String key : commands.keySet()) {
                values.add(commands.get(key));
            }
            Collections.sort(values);
        }
        return values;
    }

    public static int size() {
        return commands.size();
    }

    private Commands() {

    }
}
