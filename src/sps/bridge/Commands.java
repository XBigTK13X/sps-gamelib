package sps.bridge;

import sps.core.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Commands {
    private static Commands instance;

    public static Command get(String name) {
        if (instance == null) {
            instance = new Commands();
        }
        return instance.resolve(name);
    }

    public static void add(Command command) {
        if (instance == null) {
            instance = new Commands();
        }
        instance.put(command);
    }

    public static List<Command> values() {
        if (instance == null) {
            instance = new Commands();
        }
        return instance.all();
    }

    public static int size() {
        return instance.all().size();
    }


    private Map<String, Command> commands = new HashMap<String, Command>();

    private Commands() {

    }

    public void put(Command command) {
        commands.put(command.name(), command);
    }

    public Command resolve(String name) {
        return commands.get(name);
    }

    private List<Command> values;

    public List<Command> all() {
        if (values == null) {
            values = new ArrayList<Command>();
            for (String key : commands.keySet()) {
                values.add(commands.get(key));
            }
        }
        return values;
    }


}
