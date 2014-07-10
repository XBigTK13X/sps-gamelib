package com.simplepathstudios.gamelib.pregame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.simplepathstudios.gamelib.bridge.Commands;
import com.simplepathstudios.gamelib.data.Options;
import com.simplepathstudios.gamelib.display.Screen;
import com.simplepathstudios.gamelib.states.StateManager;
import com.simplepathstudios.gamelib.states.Systems;
import com.simplepathstudios.gamelib.text.TextPool;
import com.simplepathstudios.gamelib.ui.ButtonStyle;
import com.simplepathstudios.gamelib.ui.Tooltips;
import com.simplepathstudios.gamelib.ui.UIButton;
import com.simplepathstudios.gamelib.ui.UISlider;
import com.simplepathstudios.gamelib.util.HitTest;

public class VideoOptionsMenu extends OptionsState {
    private UISlider _brightness;

    public VideoOptionsMenu(Sprite background) {
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

        final UIButton fullScreen = new UIButton("Toggle Full Screen", Commands.get("Menu2")) {
            @Override
            public void click() {
                Options.get().FullScreen = !Gdx.graphics.isFullscreen();
                Options.get().save();
                Options.get().apply();
            }
        };
        final UIButton graphicsMode = new UIButton(qualityMessage(Options.get()), Commands.get("Menu1")) {
            @Override
            public void click() {
                Options.get().GraphicsLowQuality = !Options.get().GraphicsLowQuality;
                Options.get().save();
                Options.get().apply();
                setMessage(qualityMessage(Options.get()));
                layout();
            }
        };

        ButtonStyle style = new ButtonStyle(10, 20, 80, 10, 10);
        style.apply(back, 0, 0);
        style.apply(graphicsMode, 0, 3);
        style.apply(fullScreen, 0, 2);

        _brightness = new UISlider(80, 10, (int) Screen.width(10), (int) Screen.height(70)) {
            @Override
            public void onSlide() {
                setBrightnessPercent(getSliderPercent(), true);
            }
        };

        setBrightnessPercent(Options.get().Brightness, false);

        final UIButton brightnessReset = new UIButton("") {
            @Override
            public void click() {
                setBrightnessPercent(100, true);
            }
        };
        Systems.get(TextPool.class).write("Brightness", Screen.pos(15, 77));

        brightnessReset.setSize(5, 5);
        brightnessReset.setScreenPercent(3, 73);
        Systems.get(Tooltips.class).add(new Tooltips.User() {
            @Override
            public boolean isActive() {
                return HitTest.mouseInside(brightnessReset.getSprite());
            }

            @Override
            public String message() {
                return "Reset brightness.";
            }
        });
    }

    private void setBrightnessPercent(int brightness, boolean persist) {
        _brightness.setSliderPercent(brightness);
        if (persist) {
            Options.get().Brightness = brightness;
            Options.get().apply();
            Options.get().save();
        }
    }

    private String qualityMessage(Options options) {
        return "Graphics Mode: " + (options.GraphicsLowQuality ? "Fast" : "Pretty");
    }

    @Override
    public void draw() {
        super.draw();
        _brightness.draw();
    }
}