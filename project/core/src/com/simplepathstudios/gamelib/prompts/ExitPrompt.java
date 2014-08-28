package com.simplepathstudios.gamelib.prompts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.simplepathstudios.gamelib.bridge.Commands;
import com.simplepathstudios.gamelib.bridge.DrawDepths;
import com.simplepathstudios.gamelib.color.Color;
import com.simplepathstudios.gamelib.core.SpsConfig;
import com.simplepathstudios.gamelib.display.Screen;
import com.simplepathstudios.gamelib.display.Window;
import com.simplepathstudios.gamelib.draw.SpriteMaker;
import com.simplepathstudios.gamelib.pregame.MainMenu;
import com.simplepathstudios.gamelib.pregame.PreloadMainMenu;
import com.simplepathstudios.gamelib.states.State;
import com.simplepathstudios.gamelib.states.StateManager;
import com.simplepathstudios.gamelib.states.Systems;
import com.simplepathstudios.gamelib.text.Text;
import com.simplepathstudios.gamelib.text.TextPool;
import com.simplepathstudios.gamelib.ui.ButtonStyle;
import com.simplepathstudios.gamelib.ui.Buttons;
import com.simplepathstudios.gamelib.ui.UIButton;

public class ExitPrompt {
    private Color _bg;
    private Sprite _background;
    private boolean _active = false;
    private UIButton _desktop;
    private UIButton _toggleFullScreen;
    private UIButton _mainMenu;
    private UIButton _cancel;
    private Text _display;


    private static ExitPrompt __instance;
    private static State _lastState;

    public static ExitPrompt get() {
        if (_lastState != StateManager.get().current()) {
            __instance = new ExitPrompt();
            _lastState = StateManager.get().current();
        }
        if (__instance == null) {
            __instance = new ExitPrompt();
        }
        return __instance;
    }

    private ExitPrompt() {
        if (_bg == null) {
            _bg = new Color(0, 0, 0, 1);
        }
        _background = SpriteMaker.pixel(_bg);
        _background.setSize(Screen.width(100), Screen.height(100));
        _desktop = new UIButton("Exit to Desktop") {
            @Override
            public void click() {
                Gdx.app.exit();
            }
        };

        _cancel = new UIButton("Continue Playing " + Commands.get("Exit")) {
            @Override
            public void click() {
                close();
            }
        };

        _toggleFullScreen = new UIButton("Toggle Fullscreen " + Commands.get("ToggleFullscreen")) {
            @Override
            public void click() {
                SpsConfig.get().fullScreen = !Gdx.graphics.isFullscreen();
                SpsConfig.getInstance().apply();
                SpsConfig.getInstance().save();
            }
        };

        _mainMenu = new UIButton("Quit to Main Menu") {
            @Override
            public void click() {
                PausePrompt.get().setActive(false);
                if (StateManager.get().current().getClass() == MainMenu.class) {
                    _cancel.click();
                }
                else {
                    try {
                        StateManager.get().rollBackTo(MainMenu.class);
                    }
                    catch (Exception e) {
                        StateManager.reset().push(new PreloadMainMenu());
                    }
                }
            }
        };

        _mainMenu.setDepth(DrawDepths.get("ExitText"));
        _desktop.setDepth(DrawDepths.get("ExitText"));
        _cancel.setDepth(DrawDepths.get("ExitText"));
        _toggleFullScreen.setDepth(DrawDepths.get("ExitText"));

        ButtonStyle style = new ButtonStyle(20, 20, 60, 10, 10);
        style.apply(_desktop, 0, 6);
        style.apply(_mainMenu, 0, 4);
        style.apply(_toggleFullScreen, 0, 2);
        style.apply(_cancel, 0, 0);

        _display = Systems.get(TextPool.class).write("", Screen.pos(25, 80));
        _display.setDepth(DrawDepths.get("ExitText"));

        _display.setMoveable(false);
        _desktop.setMoveable(false);
        _cancel.setMoveable(false);
        _toggleFullScreen.setMoveable(false);
        _mainMenu.setMoveable(false);
        setActive(false);
    }

    public boolean isActive() {
        return _active;
    }

    public void activate() {
        setActive(true);
    }

    private void setActive(boolean active) {
        if (active) {
            PausePrompt.get().setActive(true);
        }
        _active = active;
        _display.setVisible(active);
        _desktop.setVisible(active);
        _cancel.setVisible(active);
        _mainMenu.setVisible(active);
        _toggleFullScreen.setVisible(active);
    }

    public void updateAndDraw() {
        if (_active) {
            Systems.get(Buttons.class).update();
            Window.get(true).schedule(_background, DrawDepths.get("ExitBackground"));
            _display.draw();
            _desktop.draw();
            _toggleFullScreen.draw();
            _cancel.draw();
            _mainMenu.draw();
            _desktop.getMessage().draw();
            _toggleFullScreen.getMessage().draw();
            _cancel.getMessage().draw();
            _mainMenu.getMessage().draw();
        }
    }

    public void close() {
        setActive(false);
        PausePrompt.get().setActive(false);
    }
}
