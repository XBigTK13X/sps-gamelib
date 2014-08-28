package com.simplepathstudios.gamelib.pregame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.simplepathstudios.gamelib.bridge.Commands;
import com.simplepathstudios.gamelib.core.SpsConfig;
import com.simplepathstudios.gamelib.states.StateManager;
import com.simplepathstudios.gamelib.ui.ButtonStyle;
import com.simplepathstudios.gamelib.ui.UIButton;

public class GameOptionsMenu extends OptionsState {
    public GameOptionsMenu(Sprite background) {
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

        final UIButton commandLabels = new UIButton("Keyboard Labels: " + (SpsConfig.get().uiButtonKeyboardLabelsEnabled ? "Enabled" : "Disabled"), Commands.get("Menu3")) {
            @Override
            public void click() {
                SpsConfig.get().uiButtonKeyboardLabelsEnabled = !SpsConfig.get().uiButtonKeyboardLabelsEnabled;
                SpsConfig.getInstance().save();
                SpsConfig.getInstance().apply();
                setMessage("Keyboard Labels: " + (SpsConfig.get().uiButtonKeyboardLabelsEnabled ? "Enabled" : "Disabled"));
                layout();
            }
        };


        final UIButton introEnabled = new UIButton("Intro Video: " + (SpsConfig.get().showIntro ? "Enabled" : "Disabled"), Commands.get("Menu1")) {
            @Override
            public void click() {
                SpsConfig.get().showIntro = !SpsConfig.get().showIntro;
                SpsConfig.getInstance().save();
                SpsConfig.getInstance().apply();
                setMessage("Intro Video: " + (SpsConfig.get().showIntro ? "Enabled" : "Disabled"));
                layout();
            }
        };

        final UIButton tutorialQueryEnabled = new UIButton("Tutorial Prompt: " + (SpsConfig.get().tutorialQueryEnabled ? "Enabled" : "Disabled"), Commands.get("Menu2")) {
            @Override
            public void click() {
                SpsConfig.get().tutorialQueryEnabled = !SpsConfig.get().tutorialQueryEnabled;
                SpsConfig.getInstance().save();
                SpsConfig.getInstance().apply();
                setMessage("Tutorial Prompt: " + (SpsConfig.get().tutorialQueryEnabled ? "Enabled" : "Disabled"));
                layout();
            }
        };

        ButtonStyle style = new ButtonStyle(10, 20, 80, 10, 10);
        style.apply(introEnabled, 0, 4);
        style.apply(commandLabels, 0, 3);
        style.apply(tutorialQueryEnabled, 0, 2);
        style.apply(back, 0, 0);
    }
}
