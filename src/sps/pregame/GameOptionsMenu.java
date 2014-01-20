package sps.pregame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import sps.io.Options;
import sps.bridge.Commands;
import sps.states.StateManager;
import sps.ui.ButtonStyle;
import sps.ui.UIButton;

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

        final UIButton commandLabels = new UIButton("Keyboard Labels: " + (Options.get().GUIButtonKeyboardLabelsEnabled ? "Enabled" : "Disabled"), Commands.get("Menu3")) {
            @Override
            public void click() {
                Options.get().GUIButtonKeyboardLabelsEnabled = !Options.get().GUIButtonKeyboardLabelsEnabled;
                Options.get().save();
                Options.get().apply();
                setMessage("Keyboard Labels: " + (Options.get().GUIButtonKeyboardLabelsEnabled ? "Enabled" : "Disabled"));
                layout();
            }
        };


        final UIButton introEnabled = new UIButton("Intro Video: " + (Options.get().ShowIntro ? "Enabled" : "Disabled"), Commands.get("Menu1")) {
            @Override
            public void click() {
                Options.get().ShowIntro = !Options.get().ShowIntro;
                Options.get().save();
                Options.get().apply();
                setMessage("Intro Video: " + (Options.get().ShowIntro ? "Enabled" : "Disabled"));
                layout();
            }
        };

        final UIButton tutorialQueryEnabled = new UIButton("Tutorial Prompt: " + (Options.get().TutorialQueryEnabled ? "Enabled" : "Disabled"), Commands.get("Menu2")) {
            @Override
            public void click() {
                Options.get().TutorialQueryEnabled = !Options.get().TutorialQueryEnabled;
                Options.get().save();
                Options.get().apply();
                setMessage("Tutorial Prompt: " + (Options.get().TutorialQueryEnabled ? "Enabled" : "Disabled"));
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
