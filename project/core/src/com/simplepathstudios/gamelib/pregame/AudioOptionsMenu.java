package com.simplepathstudios.gamelib.pregame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.simplepathstudios.gamelib.bridge.Commands;
import com.simplepathstudios.gamelib.data.Options;
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

        final UIButton musicEnabled = new UIButton("Music: " + (Options.get().MusicEnabled ? "Enabled" : "Disabled"), Commands.get("Menu1")) {
            @Override
            public void click() {
                Options.get().MusicEnabled = !Options.get().MusicEnabled;
                Options.get().save();
                Options.get().apply();
                setMessage("Music: " + (Options.get().MusicEnabled ? "Enabled" : "Disabled"));
                layout();
            }
        };

        final UIButton soundEnabled = new UIButton("Sound: " + (Options.get().MusicEnabled ? "Enabled" : "Disabled"), Commands.get("Menu2")) {
            @Override
            public void click() {
                Options.get().SoundEnabled = !Options.get().SoundEnabled;
                Options.get().save();
                Options.get().apply();
                setMessage("Sound: " + (Options.get().SoundEnabled ? "Enabled" : "Disabled"));
                layout();
            }
        };

        ButtonStyle style = new ButtonStyle(30, 35, 40, 10, 10);
        style.apply(musicEnabled, 0, 2);
        style.apply(soundEnabled, 0, 1);
        style.apply(back, 0, 0);
    }
}
