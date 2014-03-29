package sps.input.gamepad;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.io.FileUtils;
import sps.core.Loader;
import sps.core.Logger;
import sps.util.JSON;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PreconfiguredGamepadInputs {

    private static Map<String, GamepadInput> __inputs;

    public static Collection<GamepadInput> getAll() {
        if (__inputs == null) {
            init();
        }
        return __inputs.values();
    }

    private static void init() {
        __inputs = new HashMap<>();
        try {
            JsonObject json = JSON.getObject(FileUtils.readFileToString(Loader.get().data("gamepad.cfg")));
            JsonObject rawVariables = json.get("vars").getAsJsonObject();
            GamepadAdapter.DeadZone = rawVariables.get("deadZone").getAsFloat();
            GamepadAdapter.MaxInputsPerDevice = rawVariables.get("genericButtonMax").getAsInt();
            GamepadAdapter.ZeroPoint = rawVariables.get("zeroPoint").getAsFloat();

            JsonArray bindings = json.getAsJsonArray("bindings");
            for (JsonElement binding : bindings) {
                String inputName = binding.getAsJsonObject().get("id").getAsString();
                __inputs.put(inputName, GamepadInput.parse(binding.toString()));
            }

        }
        catch (Exception e) {
            Logger.exception(e);
        }
    }

    public static GamepadInput get(String controlName) {
        if (__inputs == null) {
            init();
        }
        for (String key : __inputs.keySet()) {
            if (key.equalsIgnoreCase(controlName)) {
                return __inputs.get(key);
            }
        }
        return null;
    }
}
