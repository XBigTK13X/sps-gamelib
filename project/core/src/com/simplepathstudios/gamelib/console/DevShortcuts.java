package com.simplepathstudios.gamelib.console;

import com.simplepathstudios.gamelib.data.DevConfig;
import com.simplepathstudios.gamelib.core.SpsConfig;
import com.simplepathstudios.gamelib.input.InputWrapper;
import com.simplepathstudios.gamelib.pregame.PreloadMainMenu;
import com.simplepathstudios.gamelib.states.GlobalStateResolver;
import com.simplepathstudios.gamelib.states.StateManager;

public class DevShortcuts {
    public static void handle() {
        if (DevConfig.ShortcutsEnabled && !DevConsole.get().isActive()) {
            if (InputWrapper.isActive("MoveDown") && InputWrapper.isActive("MoveUp") && InputWrapper.isActive("Debug1")) {
                StateManager.reset().push(new PreloadMainMenu());
            }
            if (InputWrapper.isActive("MoveDown") && InputWrapper.isActive("MoveUp") && InputWrapper.isActive("Debug2")) {
                SpsConfig.getInstance().resetToDefaults();
            }
            if (InputWrapper.isActive("MoveRight") && InputWrapper.isActive("MoveLeft") && InputWrapper.isActive("Debug2")) {
                StateManager.reset().push(GlobalStateResolver.get().createInitial());
            }
            if (InputWrapper.isActive("ToggleDevConsole")) {
                DevConsole.get().toggle();
            }
        }
    }
}

