package sps.pregame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import sps.data.Options;
import sps.bridge.Commands;
import sps.states.StateManager;
import sps.ui.ButtonStyle;
import sps.ui.UIButton;

public class OptionsMenu extends OptionsState {
    public OptionsMenu(Sprite background) {
        super(background);
    }

    @Override
    public void create() {
        final UIButton gameplay = new UIButton("Gameplay", Commands.get("Menu1")) {
            @Override
            public void click() {
                StateManager.get().push(new GameOptionsMenu(_background));
            }
        };

        final UIButton video = new UIButton("Video", Commands.get("Menu2")) {
            @Override
            public void click() {
                StateManager.get().push(new VideoOptionsMenu(_background));
            }
        };

        final UIButton audio = new UIButton("Audio", Commands.get("Menu3")) {
            @Override
            public void click() {
                StateManager.get().push(new AudioOptionsMenu(_background));
            }
        };

        final UIButton controls = new UIButton("Controls", Commands.get("Menu4")) {
            @Override
            public void click() {
                StateManager.get().push(new ControlConfigMenu(_background,ConfigurableCommands.get()));
            }
        };

        final UIButton back = new UIButton("Back", Commands.get("Menu6")) {
            @Override
            public void click() {
                StateManager.get().pop();
            }
        };

        final UIButton defaults = new UIButton("Reset Defaults", Commands.get("Menu9")) {
            @Override
            public void click() {
                Options.resetToDefaults();
            }
        };

        ButtonStyle style = new ButtonStyle(5, 30, 30, 10, 10);

        style.apply(gameplay, 0, 3);
        style.apply(video, 1, 3);
        style.apply(audio, 2, 3);

        style.apply(controls, 1, 2);
        style.apply(defaults, 2, 0);
        style.apply(back, 0, 0);
    }
}
