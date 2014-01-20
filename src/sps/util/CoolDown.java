package sps.util;

import com.badlogic.gdx.Gdx;

public class CoolDown {
    private float _coolDown;
    private float _coolDownMax;
    private final float _initialCoolDownMax;

    public CoolDown(float lengthInSeconds) {
        if (lengthInSeconds < 0) {
            lengthInSeconds = 0;
        }
        _coolDownMax = lengthInSeconds;
        _coolDown = _coolDownMax;
        _initialCoolDownMax = _coolDownMax;
    }

    public void reset() {
        _coolDown = _coolDownMax;
    }

    public void reset(float lengthInSeconds) {
        if (lengthInSeconds < 0) {
            lengthInSeconds = 0;
        }
        _coolDown = lengthInSeconds;
        _coolDownMax = lengthInSeconds;
    }

    public boolean updateAndCheck() {
        update();
        if (isCooled()) {
            reset();
            return true;
        }
        return false;
    }

    public void update() {
        _coolDown -= Gdx.graphics.getDeltaTime();
        if (_coolDown <= 0) {
            _coolDown = 0;
        }
    }

    public boolean isCooled() {
        return _coolDown <= 0;
    }

    public float getSecondsLeft() {
        return _coolDown;
    }

    public float getSecondsMax() {
        return _coolDownMax;
    }


    public float getInitialTimeMax() {
        return _initialCoolDownMax;
    }

    public void delay(float lengthInSeconds) {
        _coolDown += lengthInSeconds;
    }

    public int getPercentCompletion() {
        return (int) Maths.valueToPercent(0, getSecondsMax(), getSecondsLeft());
    }

    public void zeroOut() {
        _coolDown = 0;
    }
}
