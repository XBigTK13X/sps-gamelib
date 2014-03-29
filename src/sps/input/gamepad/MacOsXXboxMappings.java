package sps.input.gamepad;

import java.util.HashMap;
import java.util.Map;

public class MacOsXXboxMappings {
    public static final Map<String, GamepadInput> inputs = new HashMap<String, GamepadInput>();

    static {
        inputs.put("A", GamepadInput.createButton(11));
        inputs.put("B", GamepadInput.createButton(12));
        inputs.put("X", GamepadInput.createButton(13));
        inputs.put("Y", GamepadInput.createButton(14));
        inputs.put("DPadUp", GamepadInput.createButton(0));
        inputs.put("DPadLeft", GamepadInput.createButton(2));
        inputs.put("DPadDown", GamepadInput.createButton(1));
        inputs.put("DpadRight", GamepadInput.createButton(3));
        inputs.put("Start", GamepadInput.createButton(4));
        inputs.put("Back", GamepadInput.createButton(5));
        inputs.put("LeftShoulder", GamepadInput.createButton(8));
        inputs.put("RightShoulder", GamepadInput.createButton(9));
        inputs.put("LeftStickButton", GamepadInput.createButton(6));
        inputs.put("RightStickButton", GamepadInput.createButton(7));
        inputs.put("LeftStickUp", GamepadInput.createNegativeAxis(3));
        inputs.put("LeftStickDown", GamepadInput.createPositiveAxis(3));
        inputs.put("LeftStickLeft", GamepadInput.createNegativeAxis(2));
        inputs.put("LeftStickRight", GamepadInput.createPositiveAxis(2));
        inputs.put("RightStickUp", GamepadInput.createNegativeAxis(5));
        inputs.put("RightStickDown", GamepadInput.createPositiveAxis(5));
        inputs.put("RightStickLeft", GamepadInput.createNegativeAxis(4));
        inputs.put("RightStickRight", GamepadInput.createPositiveAxis(4));
        inputs.put("RightTrigger", GamepadInput.createPositiveAxis(1));
        inputs.put("LeftTrigger", GamepadInput.createPositiveAxis(0));
    }
}
