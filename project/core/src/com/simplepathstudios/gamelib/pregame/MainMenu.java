package com.simplepathstudios.gamelib.pregame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.simplepathstudios.gamelib.bridge.Commands;
import com.simplepathstudios.gamelib.bridge.DrawDepths;
import com.simplepathstudios.gamelib.data.DevConfig;
import com.simplepathstudios.gamelib.data.Persistence;
import com.simplepathstudios.gamelib.display.Screen;
import com.simplepathstudios.gamelib.display.Window;
import com.simplepathstudios.gamelib.states.GlobalStateResolver;
import com.simplepathstudios.gamelib.states.State;
import com.simplepathstudios.gamelib.states.StateManager;
import com.simplepathstudios.gamelib.states.Systems;
import com.simplepathstudios.gamelib.text.Text;
import com.simplepathstudios.gamelib.text.TextPool;
import com.simplepathstudios.gamelib.tutorial.Tutorials;
import com.simplepathstudios.gamelib.ui.ButtonStyle;
import com.simplepathstudios.gamelib.ui.UIButton;

public class MainMenu implements State {
    private Sprite _background;
    private Sprite _logo;
    private Text _corruptSave;
    private UIButton _load;
    private String _version;
    private boolean _saveFilePresent;

    public MainMenu(MainMenuPayload payload) {
        _background = payload.Background;
        _logo = payload.Logo;
        _version = payload.Version;
        _saveFilePresent = payload.SaveFilePresent;
    }


    @Override
    public void create() {
        _logo.setPosition(Screen.centerWidth((int) _logo.getWidth()), Screen.height(80));

        UIButton _start = new UIButton("New Game", Commands.get("Menu2")) {
            @Override
            public void click() {
                start();
            }
        };

        UIButton _options = new UIButton("Options", Commands.get("Menu3")) {
            @Override
            public void click() {
                StateManager.get().push(new OptionsMenu(_background));
            }
        };

        UIButton _exit = new UIButton("Exit", Commands.get("Menu6")) {
            @Override
            public void click() {
                Gdx.app.exit();
            }
        };

        ButtonStyle style = new ButtonStyle(30, 20, 40, 10, 10);
        style.apply(_start, 0, 2);
        style.apply(_options, 0, 1);
        style.apply(_exit, 0, 0);
        if (_saveFilePresent) {
            _load = new UIButton("Continue", Commands.get("Menu1")) {
                @Override
                public void click() {
                    StateManager.get().push(GlobalStateResolver.get().loadGame());
                }
            };
            style.apply(_load, 0, 3);
            _corruptSave = Systems.get(TextPool.class).write("\t\t\t\t\tUnable to load the save file.\n It is most likely from an older version of the game.", Screen.pos(20, 70));
            _corruptSave.setVisible(false);
        }


        Text versionDisplay = Systems.get(TextPool.class).write("Version " + _version, Screen.pos(5, 5));
        versionDisplay.setFont("Console", 24);

        Text developedDisplay = Systems.get(TextPool.class).write("Developed by Simple Path Studios", Screen.pos(40, 5));
        developedDisplay.setFont("Console", 24);

        Text twitterDisplay = Systems.get(TextPool.class).write("Twitter @XBigTK13X", Screen.pos(80, 5));
        twitterDisplay.setFont("Console", 24);
    }

    private void start() {
        StateManager.get().push(GlobalStateResolver.get().newGame());
    }

    @Override
    public void draw() {
        Window.get().schedule(_background, DrawDepths.get("GameBackground"));
        Window.get().schedule(_logo, DrawDepths.get("Logo"));
    }

    @Override
    public void update() {
        if (Persistence.get() != null && Persistence.get().isSaveBad() && !_corruptSave.isVisible()) {
            _corruptSave.setVisible(true);
            _load.setVisible(false);
        }
        if (DevConfig.EndToEndStateTest || DevConfig.BotEnabled) {
            start();
        }
    }

    @Override
    public void asyncUpdate() {
    }

    @Override
    public void load() {
        StateManager.clearTimes();
        Tutorials.get().clearCompletion();
    }

    @Override
    public void unload() {
    }

    @Override
    public String getName() {
        return "Main Menu";
    }

    @Override
    public void pause() {
    }
}
