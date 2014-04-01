package sps.input;

import com.badlogic.gdx.Gdx;
import sps.bridge.*;
import sps.display.Screen;
import sps.display.Window;
import sps.input.provider.DefaultStateProvider;
import sps.input.provider.FalseInput;
import sps.input.provider.InputProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Input implements InputProvider {

    private static InputProvider instance = new Input();
    private static InputProvider falseInstance = new FalseInput();
    private static boolean disabled = false;
    // Lists what commands are locked for a given player
    private final List<CommandLock> __locks = new ArrayList<CommandLock>();
    private StateProvider provider;
    // $$$ FIXME (Integer -> PlayerId) Maps a playerId to a context
    private HashMap<PlayerIndex, Context> __contexts;
    private boolean __isInputActive = false;
    private int mouseX;
    private int mouseY;
    private boolean _mouseLocked = false;

    private Command[] _validCommands;

    public static InputProvider get() {
        return isDisabled() ? falseInstance : instance;
    }

    public static void enable() {
        disabled = false;
    }

    public static void disable() {
        disabled = true;
    }

    public static boolean isDisabled() {
        return disabled;
    }

    @Override
    public void setup(StateProvider stateProvider) {
        __contexts = new HashMap<>();
        for (PlayerIndex index : Players.getAll()) {
            __contexts.put(index, Contexts.get(Sps.Contexts.Free));
        }

        if (stateProvider == null) {
            provider = new DefaultStateProvider();
        }
        else {
            provider = stateProvider;
        }
    }

    @Override
    public float getVector(Command command, PlayerIndex playerIndex) {
        if (playerIndex.GamepadIndex != null) {
            if (command.controllerInput(playerIndex.GamepadType) != null) {
                float vector = 0f;
                float vectorCount = 0;
                for (int ii = 0; ii < command.controllerInput(playerIndex.GamepadType).size(); ii++) {
                    if (command.controllerInput(playerIndex.GamepadType).get(ii).isAxis()) {
                        vector += command.controllerInput(playerIndex.GamepadType).get(ii).getVector(playerIndex);
                        vectorCount++;
                    }
                }
                if(vectorCount == 0){
                    return 0f;
                }
                return vector / vectorCount;
            }
        }
        return 0f;
    }

    @Override
    public boolean detectState(Command command, PlayerIndex playerIndex) {
        if (_validCommands != null) {
            boolean found = false;
            //TODO Force system commands (Exit, Pause, FullScreen, etc)
            for (Command vC : _validCommands) {
                if (vC == command) {
                    found = true;
                }
            }
            if (!found) {
                return false;
            }
        }

        boolean gamepadActive = false;
        if (playerIndex.GamepadIndex != null) {
            if (command.controllerInput(playerIndex.GamepadType) != null) {
                boolean chordActive = true;
                for (int ii = 0; ii < command.controllerInput(playerIndex.GamepadType).size(); ii++) {
                    chordActive = chordActive && command.controllerInput(playerIndex.GamepadType).get(ii).isActive(playerIndex);
                }
                if (chordActive) {
                    for (Command otherCommand : Commands.values()) {
                        if (otherCommand != command && otherCommand.controllerInput(playerIndex.GamepadType) != null) {
                            boolean anyCommonKeys = false;
                            for (int ii = 0; ii < command.controllerInput(playerIndex.GamepadType).size(); ii++) {
                                for (int jj = 0; jj < otherCommand.controllerInput(playerIndex.GamepadType).size(); jj++) {
                                    if (command.controllerInput(playerIndex.GamepadType).get(ii) == otherCommand.controllerInput(playerIndex.GamepadType).get(jj)) {
                                        anyCommonKeys = true;
                                    }
                                }
                            }
                            if (anyCommonKeys && otherCommand.controllerInput(playerIndex.GamepadType).size() > command.controllerInput(playerIndex.GamepadType).size()) {
                                boolean secondChordActive = true;
                                for (int ii = 0; ii < otherCommand.controllerInput(playerIndex.GamepadType).size(); ii++) {
                                    secondChordActive = secondChordActive && otherCommand.controllerInput(playerIndex.GamepadType).get(ii).isActive(playerIndex);
                                }
                                if (secondChordActive) {
                                    chordActive = false;
                                }
                            }
                        }
                    }
                }
                gamepadActive = chordActive;
            }
        }

        boolean keyboardActive = false;
        if (playerIndex.KeyboardIndex != null) {
            boolean chordActive = true;
            for (int ii = 0; ii < command.keys().size(); ii++) {
                chordActive = chordActive && Gdx.input.isKeyPressed(command.keys().get(ii).getKeyCode());
            }
            if (chordActive) {
                for (Command otherCommand : Commands.values()) {
                    if (otherCommand != command) {
                        boolean anyCommonKeys = false;
                        for (int ii = 0; ii < command.keys().size(); ii++) {
                            for (int jj = 0; jj < otherCommand.keys().size(); jj++) {
                                if (command.keys().get(ii) == otherCommand.keys().get(jj)) {
                                    anyCommonKeys = true;
                                }
                            }
                        }
                        if (anyCommonKeys && otherCommand.keys().size() > command.keys().size()) {
                            boolean secondChordActive = true;
                            for (int ii = 0; ii < otherCommand.keys().size(); ii++) {
                                secondChordActive = secondChordActive && Gdx.input.isKeyPressed(otherCommand.keys().get(ii).getKeyCode());
                            }
                            if (secondChordActive) {
                                chordActive = false;
                            }
                        }
                    }
                }
            }
            keyboardActive = chordActive;
        }

        return gamepadActive || keyboardActive;
    }

    private boolean isDown(Command command, PlayerIndex playerIndex) {
        return provider.isActive(command, playerIndex);
    }

    public boolean isActive(Command command, PlayerIndex playerIndex) {
        return isActive(command, playerIndex, true);
    }

    @Override
    public boolean isActive(Command command, PlayerIndex playerIndex, boolean failIfLocked) {
        __isInputActive = isDown(command, playerIndex);
        if (!__isInputActive && shouldLock(command, playerIndex)) {
            unlock(command, playerIndex);
        }

        if (isLocked(command, playerIndex) && failIfLocked) {
            return false;
        }

        if (__isInputActive && shouldLock(command, playerIndex)) {
            lock(command, playerIndex);
        }

        return __isInputActive;
    }

    // If the key is marked to be locked on press and its lock context is
    // currently inactive
    private boolean shouldLock(Command command, PlayerIndex playerIndex) {
        return command.Context == __contexts.get(playerIndex) || (command.Context == Contexts.get(Sps.Contexts.Non_Free) && __contexts.get(playerIndex) != Contexts.get(Sps.Contexts.Free) || command.Context == Contexts.get(Sps.Contexts.All));
    }

    @Override
    public void setContext(Context context, PlayerIndex playerIndex) {
        __contexts.put(playerIndex, context);
    }

    @Override
    public boolean isContext(Context context, PlayerIndex playerIndex) {
        return __contexts.get(playerIndex) == context;
    }

    @Override
    public boolean isLocked(Command command, PlayerIndex playerIndex) {
        for (CommandLock lock : __locks) {
            if (lock.Command == command && lock.Index == playerIndex) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void lock(Command command, PlayerIndex playerIndex) {
        __locks.add(new CommandLock(command, playerIndex));
    }

    @Override
    public void unlock(Command command, PlayerIndex playerIndex) {
        for (int ii = 0; ii < __locks.size(); ii++) {
            if (__locks.get(ii).Command == command && __locks.get(ii).Index == playerIndex) {
                __locks.remove(__locks.get(ii));
                ii--;
            }
        }
        setMouseLock(false);
    }

    @Override
    public void update() {
        // Remove command locks if the associated key/button isn't being pressed
        for (int ii = 0; ii < __locks.size(); ii++) {
            if (!isDown(__locks.get(ii).Command, __locks.get(ii).Index)) {
                __locks.remove(__locks.get(ii));
                ii--;
            }
        }

        provider.pollLocalState();
        translateMouseCoords();
    }

    private void translateMouseCoords() {
        float percentX = ((float) Gdx.input.getX() - Window.get().screenEngine().getBuffer().x) / (Gdx.graphics.getWidth() - Window.get().screenEngine().getBuffer().x * 2);
        mouseX = (int) (percentX * Screen.get().VirtualWidth);

        float percentY = ((float) Gdx.input.getY() - Window.get().screenEngine().getBuffer().y) / (Gdx.graphics.getHeight() - Window.get().screenEngine().getBuffer().y * 2);
        mouseY = Screen.get().VirtualHeight - (int) (percentY * Screen.get().VirtualHeight);
    }

    @Override
    public int x() {
        return mouseX;
    }

    @Override
    public int y() {
        return mouseY;
    }

    @Override
    public boolean isMouseDown() {
        return isMouseDown(true);
    }

    @Override
    public boolean isMouseDown(boolean failIfLocked) {
        if (_mouseLocked && failIfLocked) {
            return false;
        }
        return Gdx.input.isButtonPressed(com.badlogic.gdx.Input.Buttons.LEFT);
    }

    @Override
    public void setValidCommands(Command... commands) {
        _validCommands = commands;
    }

    @Override
    public void removeValidCommands() {
        _validCommands = null;
    }

    @Override
    public void setMouseLock(boolean locked) {
        _mouseLocked = locked;
    }
}
