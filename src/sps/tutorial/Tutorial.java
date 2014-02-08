package sps.tutorial;

import com.badlogic.gdx.graphics.g2d.Sprite;
import sps.bridge.Commands;
import sps.bridge.DrawDepths;
import sps.bridge.SpriteTypes;
import sps.color.Color;
import sps.color.Colors;
import sps.display.*;
import sps.draw.SpriteMaker;
import sps.input.InputWrapper;
import sps.states.StateManager;
import sps.states.Systems;
import sps.text.Text;
import sps.text.TextPool;

import java.util.ArrayList;
import java.util.List;

public class Tutorial {
    private static Sprite __background;
    private static Sprite __arrow;

    private boolean _finished;

    private List<Step> _steps;
    private Step _currentStep;
    private int _currentStepIndex;
    private Text _display;
    private boolean _arrowVisible = true;

    public Tutorial() {
        if (__background == null) {
            Color bg = Color.BLACK;
            bg = bg.newAlpha(.8f);
            __background = SpriteMaker.pixel(bg);
            __background.setSize(Screen.width(100), Screen.height(100));

            SpriteInfo arrowInfo = SpriteSheetManager.getSpriteInfo(SpriteTypes.get("Arrow"));
            __arrow = Assets.get().sprite(arrowInfo.SpriteIndex);
        }
        _steps = new ArrayList<>();
    }

    public void load() {
        StateManager.get().setSuspend(true);
        _display = Systems.get(TextPool.class).write("", Screen.pos(10, 30));
        _display.setDepth(DrawDepths.get("TutorialText"));
        _display.setMoveable(false);
        _currentStepIndex = 0;
        _finished = false;
    }

    public void addStep(String message) {
        _steps.add(new Step(-1, -1, message));
    }

    public void addStep(String message, int xPercent, int yPercent) {
        _steps.add(new Step((int) Screen.width(xPercent), (int) Screen.height(yPercent), message));
    }

    private void refreshDisplay() {
        _currentStep = _steps.get(_currentStepIndex);
        String message = _currentStep.getMessage();
        message += "\n\n\t\t(Press " + Commands.get("AdvanceTutorial") + " to continue)";
        _display.setMessage(message);
        if (_currentStep.getArrowLocation().X >= 0 && _currentStep.getArrowLocation().Y >= 0) {
            __arrow.setPosition(_currentStep.getArrowLocation().X, _currentStep.getArrowLocation().Y);
            __arrow.setRotation(45);
            __arrow.setColor(Colors.randomPleasant().getGdxColor());
            _arrowVisible = true;
        }
        else {
            _arrowVisible = false;
        }
    }

    private int _rotationLimit = 45;
    private int _rotation = _rotationLimit;
    private int _arrowRotationDirection = 1;

    public void update() {
        if (_finished) {
            return;
        }
        if (_rotation >= _rotationLimit) {
            _arrowRotationDirection = -1;
        }
        if (_rotation <= -_rotationLimit) {
            _arrowRotationDirection = 1;
        }
        _rotation += _arrowRotationDirection;
        __arrow.setRotation(_rotation);

        Window.get(true).schedule(__background, DrawDepths.get("TutorialBackground"));
        if (_arrowVisible) {
            Window.get(true).schedule(__arrow, DrawDepths.get("TutorialArrow"));
        }

        if (_steps.size() > 0 && _display.getMessage().isEmpty()) {
            refreshDisplay();
        }
        if (InputWrapper.isActive("AdvanceTutorial")) {
            if (_currentStepIndex < _steps.size() - 1) {
                _currentStepIndex++;
                refreshDisplay();
            }
            else {
                close();
            }
        }
    }

    public void close() {
        _finished = true;
        _display.setVisible(false);
        StateManager.get().setSuspend(false);
    }

    public boolean isFinished() {
        return _finished;
    }
}
