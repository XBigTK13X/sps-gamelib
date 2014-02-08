package sps.main;

import com.badlogic.gdx.Gdx;
import sps.console.DevConsole;
import sps.console.DevShortcuts;
import sps.preload.SpsEngineChainLink;
import sps.data.Options;
import sps.display.Window;
import sps.entity.Entities;
import sps.input.Input;
import sps.input.InputWrapper;
import sps.particle.ParticleWrapper;
import sps.prompts.ExitPrompt;
import sps.prompts.PausePrompt;
import sps.states.State;
import sps.states.Systems;
import sps.states.StateManager;
import sps.task.GameTasks;
import sps.text.TextPool;
import sps.thread.GameMonitor;
import sps.tutorial.Tutorials;

public class LoadedGame implements SpsEngineChainLink {
    private State _preUpdateState;
    private boolean _firstUpdateOptionsLoaded = false;

    private void handleWindowQuerks() {
        if (!_firstUpdateOptionsLoaded) {
            Options.load();
            Options.get().apply();
            _firstUpdateOptionsLoaded = true;
        }
    }

    private void handleUserInput() {
        Input.get().update();

        if (InputWrapper.isActive("Pause") && !DevConsole.get().isActive() && !Tutorials.get().isActive() && !ExitPrompt.get().isActive()) {
            PausePrompt.get().setActive(!PausePrompt.get().isActive());
        }

        if (InputWrapper.isActive("ToggleFullScreen") && !DevConsole.get().isActive()) {
            Options.get().FullScreen = !Gdx.graphics.isFullscreen();
            Options.get().apply();
            Options.get().save();
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
