package com.simplepathstudios.gamelib.data;

import com.simplepathstudios.gamelib.core.Logger;
import com.simplepathstudios.gamelib.util.Parse;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.constructor.Constructor;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class SpsConfigData {
    private static Constructor __yamlConstructor;

    public static Constructor getYamlConstructor() {
        if (__yamlConstructor == null) {
            __yamlConstructor = new Constructor(SpsConfigData.class);
            TypeDescription configData = new TypeDescription(SpsConfigData.class);
            configData.putListPropertyType("commandContexts", String.class);
            configData.putListPropertyType("contextOverrides", Map.class);
            configData.putListPropertyType("drawDepths", String.class);
            configData.putMapPropertyType("gameOptions", String.class, Object.class);
            configData.putMapPropertyType("engineOptions", String.class, Object.class);
            configData.putMapPropertyType("devOptions", String.class, Object.class);
            __yamlConstructor.addTypeDescription(configData);
        }
        return __yamlConstructor;
    }

    public SpsConfigData() {

    }

    public List<String> commandContexts;
    public List<Map<String, String>> contextOverrides;
    public List<String> drawDepths;
    public Map<String, Object> gameOptions;
    public Map<String, Object> engineOptions;
    public Map<String, Object> devOptions;
    public List<Map<String, Object>> inputBindings;

    //gameOptions
    public Boolean disableCloudyTextures;
    public Boolean enableFontOutlines;
    public Boolean collectMetaData;
    public Integer playersMax;
    public Boolean graphicsLowQuality;
    public Boolean tutorialEnabled;
    public Boolean showIntro;
    public Boolean tutorialQueryEnabled;
    public Integer brightness;
    public Boolean settingsDetected;
    public Boolean uiButtonKeyboardLabelsEnabled;

    //engineOptions
    public Boolean fullScreen;
    public Boolean musicEnabled;
    public Boolean soundEnabled;
    public Integer resolutionWidth;
    public Integer resolutionHeight;
    public Integer virtualWidth;
    public Integer virtualHeight;
    public Boolean vSyncEnabled;
    public Float gameTasksTimeDilation;
    public Boolean gameTasksTimeDilationEnabled;
    public Integer particleEffectPoolLimit;
    public Integer particleEffectPoolStartSize;
    public Integer maxColorLookupSize;
    public Float gamepadAxisDeadZone;
    public Boolean devConsoleEnabled;
    public Boolean viewPaths;
    public Boolean controllersEnabled;
    public Integer threadMaxStalledMilliseconds;
    public Integer spriteWidth;
    public Integer spriteHeight;
    public Integer tileMapHeight;
    public Integer tileMapWidth;
    public Boolean entityGridEnabled;
    public Boolean displayLogging;
    public Boolean taskLogging;
    public Boolean inputLoadLogging;

    //devOptions
    public Boolean endToEndTest;
    public Boolean botEnabled;
    public Boolean timeStates;
    public Boolean shortcutsEnabled;

    public void loadVarsFromMaps() {
        for (Field field : SpsConfigData.class.getDeclaredFields()) {
            Object value = null;
            String name = field.getName();
            if (gameOptions.containsKey(name)) {
                value = gameOptions.get(name);
            }
            else if (engineOptions.containsKey(name)) {
                value = engineOptions.get(name);
            }
            else if (devOptions.containsKey(name)) {
                value = devOptions.get(name);
            }
            else {
                switch (name) {
                    case "__yamlConstructor":
                    case "commandContexts":
                    case "contextOverrides":
                    case "drawDepths":
                    case "gameOptions":
                    case "engineOptions":
                    case "devOptions":
                    case "inputBindings":
                        break;
                    default:
                        Logger.info("Unable to find config entry for: " + name);
                        break;
                }
            }
            if (value != null) {
                try {
                    if (value.getClass() == Double.class) {
                        //TODO Must be a better way, but I don't have Internet access to see how :p
                        Logger.info("Converting double found in sps config to float: " + name);
                        value = Parse.floa(value.toString());
                    }
                    field.set(this, value);
                }
                catch (Exception e) {
                    Logger.exception(e, true);
                }
            }
        }
    }

    public void dumpVarsToMaps() {
        //TODO Store everything in a user's YAML file
        throw new NotImplementedException();
    }
}
