package com.simplepathstudios.gamelib.bridge;

import com.simplepathstudios.gamelib.core.Loader;
import com.simplepathstudios.gamelib.core.Logger;
import com.simplepathstudios.gamelib.display.Assets;
import org.apache.commons.io.FileUtils;

public class Bridge {
    private static boolean __setup = false;

    public static void setup(boolean enableGraphics) {
        if (!__setup) {
            try {
                if (enableGraphics) {
                    Assets.get();
                }
                boolean processDrawDepths = false;
                int drawDepth = 0;
                for (String line : FileUtils.readLines(Loader.get().data("bridge.cfg"))) {
                    if (!line.contains("#")) {
                        String[] values = line.split(",");
                        String name = values[0];
                        if (name.equals("context")) {
                            String id = values[1];
                            Contexts.add(new Context(id));
                        }
                        if (name.equals("command")) {
                            String id = values[1];
                            String context = values[2];
                            Commands.add(new Command(id, Contexts.get(context)));
                        }
                        if (name.equalsIgnoreCase("StartDrawDepths")) {
                            processDrawDepths = true;
                        }
                        if (processDrawDepths) {
                            String id = values[0];
                            DrawDepths.add(new DrawDepth(id, drawDepth++));
                        }
                        if (name.equalsIgnoreCase("EndDrawDepths")) {
                            processDrawDepths = false;
                        }
                    }
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
