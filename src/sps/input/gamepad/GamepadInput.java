package sps.input.gamepad;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;
import sps.core.Logger;

import java.io.Serializable;

public class GamepadInput implements Serializable {
    final private PovDirection povDirection;
    final private Integer pov;
    final private Integer button;
    final private Integer axis;
    final private Boolean positive;
    final private Boolean nonZero;
    final private Device device;
    final private Float threshold;
    final private Boolean greaterThan;

    private GamepadInput(Integer buttonIndex, Integer axisIndex, Integer povIndex, PovDirection direction, Boolean nonzero, Boolean positive, Float threshold, Boolean greaterThan) {
        pov = povIndex;
        povDirection = direction;
        axis = axisIndex;
        button = buttonIndex;
        this.positive = positive;
        nonZero = nonzero;
        if (pov != null) {
            device = Device.Pov;
        }
        else if (axis != null) {
            device = Device.Axis;
        }
        else {
            device = Device.Button;
        }
        this.threshold = threshold;
        this.greaterThan = greaterThan;
    }

    private GamepadInput() {
        this(null, null, null, null, null, null, null, null);
    }

    public static GamepadInput createButton(int index) {
        return new GamepadInput(index, null, null, null, null, null, null, null);
    }

    public static GamepadInput createNonZeroAxis(int index) {
        return new GamepadInput(null, index, null, null, true, null, null, null);
    }

    public static GamepadInput createPositiveAxis(int index) {
        return new GamepadInput(null, index, null, null, null, true, null, null);
    }

    public static GamepadInput createNegativeAxis(int index) {
        return new GamepadInput(null, index, null, null, null, false, null, null);
    }

    public static GamepadInput createGreaterThanAxis(int index, float threshold) {
        return new GamepadInput(null, index, null, null, null, null, threshold, true);
    }

    public static GamepadInput createLessThanAxis(int index, float threshold) {
        return new GamepadInput(null, index, null, null, null, null, threshold, false);
    }

    public static GamepadInput createPov(int index, PovDirection direction) {
        return new GamepadInput(null, null, index, direction, null, null, null, null);
    }

    public static GamepadInput parse(String source) {
        String[] parts = source.split("/");
        //Wired Xbox 360 controllers are the only supported, non-serialized input
        if (parts.length == 1) {
            if (!parts[0].equalsIgnoreCase("null")) {
                return XBox360GamepadInputs.get(parts[0]).Input;
            }
            return null;
        }
        else {
            Device device = Device.get(parts[0]);
            int index = Integer.parseInt(parts[1]);
            switch (device) {
                case Button:
                    return createButton(index);
                case Pov:
                    int povIndex = index;
                    PovDirection direction = getDirection(parts[2]);
                    return createPov(povIndex, direction);
                case Axis:
                    String trigger = parts[2];
                    if (trigger.equalsIgnoreCase("nonzero")) {
                        return createNonZeroAxis(index);
                    }
                    else if (trigger.equalsIgnoreCase("positive")) {
                        return createPositiveAxis(index);
                    }
                    else {
                        return createNegativeAxis(index);
                    }
            }
        }
        return null;
    }

    private static PovDirection getDirection(String name) {
        for (PovDirection direction : PovDirection.values()) {
            if (direction.name().equalsIgnoreCase(name)) {
                return direction;
            }
        }
        return null;
    }

    public String serialize() {
        String result = "";
        result += device.name() + "/";
        switch (device) {
            case Button:
                result += button;
                break;
            case Pov:
                result += pov + "/";
                result += povDirection.name();
                break;
            case Axis:
                result += axis + "/";
                result += (positive == null) ? "NonZero" : (positive) ? "Positive" : "Negative";
                break;
        }
        return result;
    }

    public boolean isActive(Controller controller) {
        if (threshold != null) {
            if (greaterThan) {
                return GamepadAdapter.get().isAxisGreaterThan(controller, axis, threshold);
            }
            else {
                return GamepadAdapter.get().isAxisLessThan(controller, axis, threshold);
            }
        }
        if (button != null) {
            return GamepadAdapter.get().isDown(controller, button);
        }
        if (axis != null) {
            if (positive != null) {
                if (positive) {
                    return GamepadAdapter.get().isPositive(controller, axis);
                }
                if (!positive) {
                    return GamepadAdapter.get().isNegative(controller, axis);
                }
            }
            else {
                return GamepadAdapter.get().isNotZero(controller, axis);
            }
            Logger.error("Invalid axis ControllerInput defined");
            return false;
        }
        return GamepadAdapter.get().isPovActive(controller, pov, povDirection);
    }

    public int hashCode() {
        int hash = 0;
        hash += (positive != null) ? ((positive) ? 1 : 2) : 0;
        hash += (nonZero != null) ? ((nonZero) ? 5 : 7) : 0;
        hash += (greaterThan != null) ? ((greaterThan) ? 13 : 17) : 0;
        hash += (povDirection != null) ? povDirection.ordinal() * 100 : 0;
        hash += (pov != null) ? pov * 10000 : 0;
        hash += (axis != null) ? axis * 1000000 : 0;
        hash += (button != null) ? button * 100000000 : 0;
        return hash;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }

        GamepadInput rhs = (GamepadInput) obj;
        return rhs.hashCode() == hashCode();
    }

    private enum Device {
        Button,
        Pov,
        Axis;

        public static Device get(String name) {
            for (Device device : values()) {
                if (device.name().equalsIgnoreCase(name)) {
                    return device;
                }
            }
            return null;
        }
    }

}