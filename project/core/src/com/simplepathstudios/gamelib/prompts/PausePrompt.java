package com.simplepathstudios.gamelib.prompts;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.simplepathstudios.gamelib.bridge.Commands;
import com.simplepathstudios.gamelib.bridge.DrawDepths;
import com.simplepathstudios.gamelib.color.Color;
import com.simplepathstudios.gamelib.display.Screen;
import com.simplepathstudios.gamelib.display.Window;
import com.simplepathstudios.gamelib.draw.ProcTextures;
import com.simplepathstudios.gamelib.draw.SpriteMaker;
import com.simplepathstudios.gamelib.states.StateManager;
import com.simplepathstudios.gamelib.states.Systems;
import com.simplepathstudios.gamelib.text.Text;
import com.simplepathstudios.gamelib.text.TextPool;

public class PausePrompt {
    private static PausePrompt __instance;

    public static PausePrompt get() {
        if (__instance == null) {
            __instance = new PausePrompt();
        }
        return __instance;
    }

    private Sprite __pausedScreen;
    private Text __pausedText;
    private boolean _active;

    public PausePrompt() {
        Color[][] tbg = ProcTextures.monotone((int) Screen.width(50), (int) Screen.height(50), new Color(.5f, .1f, .5f, .7f));
        __pausedScreen = SpriteMaker.fromColors(tbg);
        __pausedScreen.setPosition(Screen.width(25), Screen.height(25));
        __pausedText = Systems.get(TextPool.class).write("     PAUSED\n" + Commands.get("Pause") + " to continue", Screen.pos(45, 55));
        __pausedText.setDepth(DrawDepths.get("PauseText"));
        __pausedText.setVisible(false);
        __pausedText.setMoveable(false);
    }

    public boolean isActive() {
        return _active;
    }

    public void setActive(boolean active) {
        _active = active;
        __pausedText.setVisible(active);
        StateManager.get().setSuspend(active);
    }

    public void updateAndDraw() {
        if (_active && !ExitPrompt.get().isActive()) {
            Window.get(true).schedule(__pausedScreen, DrawDepths.get("PauseScreen"));
            __pausedText.draw();
        }
    }
}
