package com.simplepathstudios.gamelib.input.gamepad;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.google.gson.JsonObject;
import com.simplepathstudios.gamelib.input.PlayerIndex;
import org.apache.commons.lang3.SystemUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GamepadInput implements Serializable {
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

    final private Device _inputType;
    final private Integer _hardwareId;
    final private Float _threshold;
    final private String _direction;
    final private String _name;

    private GamepadInput(String name, Device inputType, Integer hardwareId, Float threshold, String direction) {
        _inputType = inputType;
        _hardwareId = hardwareId;
        _threshold = threshold;
        _direction = direction;
        _name = name;
    }

    public GamepadInput(String name, int buttonIndex) {
        this(name, Device.Button, buttonIndex, null, null);
    }

    public GamepadInput(String name, int hardwareIndex, String direction, float threshold) {
        this(name, Device.Axis, hardwareIndex, threshold, direction);
    }

    public GamepadInput(String name, int index, String direction) {
        this(name, Device.Pov, index, null, direction);
    }

    public static GamepadInput fromSerializable(Object serializable, Float deadZone) {
        String controlName = serializable.toString();
        if (controlName.equalsIgnoreCase("null") || controlName == null) {
            return null;
        }

        Map<String, Object> bindings = (Map<String, Object>) serializable;

        String name = bindings.get("id").toString();
        String inputType = bindings.get("type").toString();
        String os = getPlatformString();

        Map<String, Object> configs = (Map<String, Object>) bindings.get("config");

        Map<String, Object> platformBinding = (Map<String, Object>) configs.get(os);
        if (platformBinding.containsKey("type")) {
            inputType = platformBinding.get("type").toString();
        }
        int hardwareIndex = (int) platformBinding.get("id");

        switch (inputType) {
            case "button":
                return new GamepadInput(name, hardwareIndex);
            case "axis":
                if (platformBinding.containsKey("direction")) {
                    String axisDirection = platformBinding.get("direction").toString();
                    String bounds = platformBinding.get("bound").toString();
                    float threshold = 0;
                    switch (bounds) {
                        case "deadZone":
                            threshold = deadZone;
                            break;
                        case "-deadZone":
                            threshold = -deadZone;
                            break;
                    }
                    return new GamepadInput(name, hardwareIndex, axisDirection, threshold);
                }
                else {
                    return new GamepadInput(name, hardwareIndex, null, 0f);
                }
            case "pov":
                String povDirection = platformBinding.get("direction").toString();
                return new GamepadInput(name, hardwareIndex, povDirection);
            default:
                return null;
        }
    }

    private static PovDirection getPovDirection(String name) {
        for (PovDirection direction : PovDirection.values()) {
            if (direction.name().equalsIgnoreCase(name)) {
                return direction;
            }
        }
        return null;
    }

    private static String getPlatformString() {
        return SystemUtils.IS_OS_MAC ? "mac" : SystemUtils.IS_OS_WINDOWS ? "windows" : "linux";
    }

    public Map<String, Object> getSerializable() {
        Map<String, Object> serializable = new HashMap<>();
        serializable.put("id", _name);
        serializable.put("type", _inputType.name().toLowerCase());

        Map<String, Object> platformBlock = new HashMap<>();
        platformBlock.put("id", _hardwareId);
        switch (_inputType) {
            case Pov:
                platformBlock.put("direction", _direction);
                break;
            case Axis:
                platformBlock.put("direction", _direction);
                platformBlock.put("bound", _threshold);
                break;
            default:
                break;
        }

        Map<String, Object> configBlock = new HashMap<>();
        configBlock.put(getPlatformString(), platformBlock);
        serializable.put("config", configBlock);

        return serializable;
    }

    public boolean isActive(PlayerIndex playerIndex) {
        Controller controller = Controllers.getControllers().get(playerIndex.GamepadIndex);
        switch (_inputType) {
            case Button:
                return GamepadAdapter.get().isDown(controller, _hardwareId);
            case Pov:
                return GamepadAdapter.get().isPovActive(controller, _hardwareId, getPovDirection(_direction));
            case Axis:
                if (_direction == null) {
                    return getVector(playerIndex) != 0f;
                }
                if (_direction.equalsIgnoreCase("above")) {
                    return GamepadAdapter.get().isAxisGreaterThan(controller, _hardwareId, _threshold);
                }
                else {
                    return GamepadAdapter.get().isAxisLessThan(controller, _hardwareId, _threshold);
                }
        }
        return false;
    }

    public boolean isAxis() {
        return _inputType == Device.Axis;
    }

    public float getVector(PlayerIndex playerIndex) {
        if (_inputType == Device.Axis) {
            Controller controller = Controllers.getControllers().get(playerIndex.GamepadIndex);
            float scaled = GamepadAdapter.get().getScaledAxis(controller, _hardwareId, 1f);
            if (_direction == null) {
                return scaled;
            }
            if ((_direction.equalsIgnoreCase("above") && scaled > 0) || ((_direction.equalsIgnoreCase("below") && scaled < 0))) {
                return scaled;
            }
        }
        return 0f;
    }

    public String getName() {
        return _name;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int hashCode() {
        int hash = _inputType.ordinal();
        hash += _hardwareId * 100;
        hash += (_direction == null) ? 0 : _direction.hashCode();
        hash += (_threshold == null) ? 0 : _threshold;
        return hash;
    }

    @Override
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
}