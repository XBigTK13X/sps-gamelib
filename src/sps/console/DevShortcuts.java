package sps.console;

import sps.data.DevConfig;
import sps.data.Options;
import sps.input.InputWrapper;
import sps.pregame.PreloadMainMenu;
import sps.states.GlobalStateResolver;
import sps.states.StateManager;

public class DevShortcuts {
    public static void handle() {
        if (DevConfig.ShortcutsEnabled && !DevConsole.get().isActive()) {
            if (InputWrapper.isActive("MoveDown") && InputWrapper.isActive("MoveUp") && InputWrapper.isActive("Debug1")) {
                StateManager.reset().push(new PreloadMainMenu());
            }
            if (InputWrapper.isActive("MoveDown") && InputWrapper.isActive("MoveUp") && InputWrapper.isActive("Debug2")) {
                Options.resetToDefaults();
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

