package sps.bridge;

import java.util.*;

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

    public static void clear() {
        instance = new Commands();
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
        commands.put(command.name().toLowerCase(), command);
    }

    public Command resolve(String name) {
        return commands.get(name.toLowerCase());
    }

    private List<Command> values;

    public List<Command> all() {
        if (values == null || values.size() != commands.entrySet().size()) {
            values = new ArrayList<>();
            for (String key : commands.keySet()) {
                values.add(commands.get(key));
            }
            Collections.sort(values);
        }
        return values;
    }

}
