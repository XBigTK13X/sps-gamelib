package com.simplepathstudios.gamelib.data;

import com.simplepathstudios.gamelib.core.Loader;
import com.simplepathstudios.gamelib.core.Logger;
import com.simplepathstudios.gamelib.display.Window;
import com.simplepathstudios.gamelib.input.InputBindings;
import com.simplepathstudios.gamelib.util.Maths;
import com.simplepathstudios.gamelib.util.YAML;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class SpsConfig {
    private static SpsConfig __instance;
    private static File __config = UserFiles.config();
    private static File __defaultConfig = Loader.get().data("config.yaml");

    private SpsConfigData _spsConfigData;

    private static void init() {
        if (__instance == null) {
            __instance = new SpsConfig();
            __instance.load();
        }
    }

    public static SpsConfigData get() {
        init();
        return __instance.getData();
    }

    public static SpsConfig getInstance() {
        init();
        return __instance;
    }

    private SpsConfig() {

    }

    public SpsConfigData getData() {
        return _spsConfigData;
    }

    public void resetToDefaults() {
        if (__config.exists()) {
            try {
                Logger.info("Removing user's config data: " + __config.getAbsolutePath());
                FileUtils.forceDelete(__config);
            }
            catch (Exception e) {
                Logger.exception(e, false);
            }
        }
        try {
            load();
            apply();
            InputBindings.bindAll();
        }
        catch (Exception e) {
            Logger.exception(e, false);
        }
    }

    private void load() {
        if (__config.exists()) {
            try {
                Logger.info("Load sps config from: " + __config.getAbsolutePath());
                String yaml = FileUtils.readFileToString(__config);
                _spsConfigData = (SpsConfigData) YAML.getObject(yaml, SpsConfigData.getYamlConstructor());
                return;
            }
            catch (Exception e) {
                Logger.exception(e, false);
            }
        }
        try {
            Logger.info("Load sps config from: " + __defaultConfig.getAbsolutePath());
            String yaml = FileUtils.readFileToString(__defaultConfig);
            _spsConfigData = (SpsConfigData) YAML.getObject(yaml, SpsConfigData.getYamlConstructor());
            _spsConfigData.loadVarsFromMaps();
        }
        catch (Exception e) {
            Logger.exception(e, false);
        }
    }

    public void save() {
        Logger.info("Saving user config to: " + __config.getAbsolutePath());
        try {
            String yaml = YAML.toString(_spsConfigData);
            FileUtils.write(__config, yaml);
        }
        catch (Exception e) {
            Logger.exception(e, false);
        }
    }

    public void apply() {
        Window.resize(get().resolutionWidth, get().resolutionHeight, get().fullScreen);
        SpsConfig.get().musicEnabled = get().musicEnabled;
        SpsConfig.get().soundEnabled = get().soundEnabled;
        int brightness = (int) (Maths.percentToValue(-25, 0, get().brightness));
        Window.get().screenEngine().setBrightness(brightness);
        Window.get(true).screenEngine().setBrightness(brightness);
    }
}