package com.simplepathstudios.gamelib.bridge;

import com.simplepathstudios.gamelib.core.Logger;
import com.simplepathstudios.gamelib.data.SpsConfig;
import com.simplepathstudios.gamelib.display.Assets;

import java.util.Map;

public class Bridge {
    private static boolean __setup = false;

    public static void setup(boolean enableGraphics) {
        if (!__setup) {
            try {
                if (enableGraphics) {
                    Assets.get();
                }

                int drawDepth = 0;
                for (String context : SpsConfig.get().commandContexts) {
                    Contexts.add(new Context(context));
                }
                for (Map<String, String> override : SpsConfig.get().contextOverrides) {
                    String command = override.get("command");
                    String context = override.get("context");
                    if (SpsConfig.get().inputLoadLogging) {
                        Logger.info("Overriding " + command + " context to " + context);
                    }
                    Commands.add(new Command(command, Contexts.get(context)));
                }
                for (String drawDepthName : SpsConfig.get().drawDepths) {
                    DrawDepths.add(new DrawDepth(drawDepthName, drawDepth++));
                }
            }
            catch (Exception e) {
                Logger.exception("Error occurred while parsing bridge.cfg.", e);
            }
            __setup = true;
        }
    }

    private Bridge() {

    }
}
