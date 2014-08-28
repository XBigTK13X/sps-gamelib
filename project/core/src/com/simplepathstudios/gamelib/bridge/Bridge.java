package com.simplepathstudios.gamelib.bridge;

import com.simplepathstudios.gamelib.core.Logger;
import com.simplepathstudios.gamelib.core.SpsConfig;
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
                    for (String command : override.keySet()) {
                        Commands.add(new Command(command, Contexts.get(override.get(command))));
                    }
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
