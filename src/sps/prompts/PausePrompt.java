package sps.prompts;

import com.badlogic.gdx.graphics.g2d.Sprite;
import sps.bridge.Commands;
import sps.bridge.DrawDepths;
import sps.color.Color;
import sps.display.Screen;
import sps.display.Window;
import sps.draw.ProcTextures;
import sps.draw.SpriteMaker;
import sps.states.StateManager;
import sps.text.Text;
import sps.text.TextPool;

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
        __pausedText = TextPool.get().write("     PAUSED\n" + Commands.get("Pause") + " to continue", Screen.pos(45, 55));
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
