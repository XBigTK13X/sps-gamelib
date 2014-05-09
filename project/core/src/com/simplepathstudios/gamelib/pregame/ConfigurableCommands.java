package com.simplepathstudios.gamelib.pregame;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConfigurableCommands {
    private static ConfigurableCommands __instance;
    public static ConfigurableCommands get(){
        if(__instance == null){
            __instance = new ConfigurableCommands();
        }
        return __instance;
    }

    private List<String> _commandIds;
    private Map<String,String> _mapping;
    private ConfigurableCommands(){
        _commandIds = new LinkedList<>();
        _mapping = new HashMap<>();
    }

    public void add(String command, String display){
        _commandIds.add(command);
        _mapping.put(command,display);
    }


    public List<String> getIds() {
        return _commandIds;
    }

    public String getDisplay(String commandId) {
        return _mapping.get(commandId);
    }
}
