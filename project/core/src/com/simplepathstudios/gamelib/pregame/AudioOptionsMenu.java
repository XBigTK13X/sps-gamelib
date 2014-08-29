package com.simplepathstudios.gamelib.pregame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.simplepathstudios.gamelib.bridge.Commands;
import com.simplepathstudios.gamelib.data.SpsConfig;
import com.simplepathstudios.gamelib.states.StateManager;
import com.simplepathstudios.gamelib.ui.ButtonStyle;
import com.simplepathstudios.gamelib.ui.UIButton;

public class AudioOptionsMenu extends OptionsState {
    public AudioOptionsMenu(Sprite background) {
        super(background);
    }

    @Override
    public void create() {
        final UIButton back = new UIButton("Back", Commands.get("Menu6")) {
            @Override
            public void click() {
                StateManager.get().pop();
            }
        };

        final UIButton musicEnabled = new UIButton("Music: " + (SpsConfig.get().musicEnabled? "Enabled" : "Disabled"), Commands.get("Menu1")) {
            @Override
            public void click() {
                SpsConfig.get().musicEnabled= !SpsConfig.get().musicEnabled;
                SpsConfig.getInstance().save();
                SpsConfig.getInstance().apply();
                setMessage("Music: " + (SpsConfig.get().musicEnabled? "Enabled" : "Disabled"));
                layout();
            }
        };

        final UIButton soundEnabled = new UIButton("Sound: " + (SpsConfig.get().musicEnabled? "Enabled" : "Disabled"), Commands.get("Menu2")) {
            @Override
            public void click() {
                SpsConfig.get().soundEnabled = !SpsConfig.get().soundEnabled;
                SpsConfig.getInstance().save();
                SpsConfig.getInstance().apply();
                setMessage("Sound: " + (SpsConfig.get().soundEnabled ? "Enabled" : "Disabled"));
                layout();
            }
        };

        ButtonStyle style = new ButtonStyle(30, 35, 40, 10, 10);
        style.apply(musicEnabled, 0, 2);
        style.apply(soundEnabled, 0, 1);
        style.apply(back, 0, 0);
    }
}
