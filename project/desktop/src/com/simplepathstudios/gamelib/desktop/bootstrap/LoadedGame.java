package com.simplepathstudios.gamelib.desktop.bootstrap;

import com.badlogic.gdx.Gdx;
import com.simplepathstudios.gamelib.console.DevConsole;
import com.simplepathstudios.gamelib.console.DevShortcuts;
import com.simplepathstudios.gamelib.core.SpsConfig;
import com.simplepathstudios.gamelib.display.Window;
import com.simplepathstudios.gamelib.input.Input;
import com.simplepathstudios.gamelib.input.InputWrapper;
import com.simplepathstudios.gamelib.preload.SpsEngineChainLink;
import com.simplepathstudios.gamelib.prompts.ExitPrompt;
import com.simplepathstudios.gamelib.prompts.PausePrompt;
import com.simplepathstudios.gamelib.states.State;
import com.simplepathstudios.gamelib.states.StateManager;
import com.simplepathstudios.gamelib.states.Systems;
import com.simplepathstudios.gamelib.thread.GameMonitor;
import com.simplepathstudios.gamelib.tutorial.Tutorials;

public class LoadedGame implements SpsEngineChainLink {
    private State _preUpdateState;
    private boolean _firstUpdateOptionsLoaded = false;

    private void handleWindowQuerks() {
        if (!_firstUpdateOptionsLoaded) {
            SpsConfig.getInstance().apply();
            _firstUpdateOptionsLoaded = true;
        }
    }

    private void handleUserInput() {
        Input.get().update();

        if (InputWrapper.isActive("Pause") && !DevConsole.get().isActive() && !Tutorials.get().isActive() && !ExitPrompt.get().isActive()) {
            PausePrompt.get().setActive(!PausePrompt.get().isActive());
        }

        if (InputWrapper.isActive("ToggleFullScreen") && !DevConsole.get().isActive()) {
            SpsConfig.get().fullScreen = !Gdx.graphics.isFullscreen();
            SpsConfig.getInstance().apply();
            SpsConfig.getInstance().save();
        }

        if (!DevConsole.get().isActive()) {
            if (InputWrapper.isActive("Help")) {
                Tutorials.get().show(true);
            }

            if (InputWrapper.isActive("Exit")) {
                if (ExitPrompt.get().isActive()) {
                    ExitPrompt.get().close();
                }
                else {
                    if (!Tutorials.get().close()) {
                        ExitPrompt.get().activate();
                    }
                }
            }
        }
    }

    private void nonGameUpdates() {
        GameMonitor.keepAlive();
        handleWindowQuerks();
        handleUserInput();
        PausePrompt.get().updateAndDraw();
        ExitPrompt.get().updateAndDraw();
        Tutorials.get().update();
        DevConsole.get().updateAndDraw();
        DevShortcuts.handle();
    }

    @Override
    public void update() {
        nonGameUpdates();

        if (!PausePrompt.get().isActive() && !ExitPrompt.get().isActive()) {
            _preUpdateState = StateManager.get().current();
            StateManager.get().asyncUpdate();
            StateManager.get().update();
            Systems.update();
        }
    }

    @Override
    public void draw() {
        if (_preUpdateState == StateManager.get().current()) {
            if (!PausePrompt.get().isActive() && !ExitPrompt.get().isActive()) {
                StateManager.get().draw();
                Systems.draw();
            }

            Window.processDrawCalls();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
