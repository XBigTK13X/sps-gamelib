package com.simplepathstudios.gamelib.console;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.simplepathstudios.gamelib.bridge.DrawDepths;
import com.simplepathstudios.gamelib.color.Color;
import com.simplepathstudios.gamelib.core.Logger;
import com.simplepathstudios.gamelib.display.Point2;
import com.simplepathstudios.gamelib.data.SpsConfig;
import com.simplepathstudios.gamelib.display.Screen;
import com.simplepathstudios.gamelib.prompts.PausePrompt;
import com.simplepathstudios.gamelib.states.Systems;
import com.simplepathstudios.gamelib.text.Text;
import com.simplepathstudios.gamelib.text.TextPool;
import com.simplepathstudios.gamelib.ui.MultiText;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DevConsole {
    private static final int __consoleBufferSize = 40;

    private static DevConsole __instance;

    public static DevConsole get() {
        if (__instance == null) {
            __instance = new DevConsole();
        }
        return __instance;
    }

    private final Text _input;
    private final Map<String, DevConsoleAction> _actions;
    private boolean[] _locked = new boolean[256];
    private boolean _active;
    private MultiText _multiText;

    private DevConsole() {
        _active = false;

        _actions = new HashMap<>();
        _input = Systems.get(TextPool.class).write("", new Point2(50, Screen.get().VirtualHeight - 50));
        _input.setDepth(DrawDepths.get("DevConsoleText"));

        _multiText = new MultiText(new Point2(0, 0), __consoleBufferSize, Color.BLACK.newAlpha(.75f), Screen.get().VirtualWidth, Screen.get().VirtualHeight - 100);
        _multiText.setBackgroundDepth(DrawDepths.get("DevConsole"));
        _multiText.setTextDepth(DrawDepths.get("DevConsoleText"));
        _multiText.setVisible(false);

        register(new DevConsoleAction("stop") {
            @Override
            public String act(int[] input) {
                toggle();
                PausePrompt.get().setActive(false);
                return "";
            }
        });

        register(new DevConsoleAction("kill") {
            @Override
            public String act(int[] input) {
                Gdx.app.exit();
                return "";
            }
        });

        register(new DevConsoleAction("fs") {
            @Override
            public String act(int[] input) {
                SpsConfig.get().fullScreen = !SpsConfig.get().fullScreen;
                SpsConfig.getInstance().apply();
                return null;
            }
        });

        register(new DevConsoleAction("help") {
            @Override
            public String act(int[] input) {
                String result = "";
                for (String id : _actions.keySet()) {
                    result += id + ", ";
                }
                return result;
            }
        });

        Logger.info("The development console is initialized");
        add("The development console has been started.");
    }

    public void setFont(String fontLabel, int pointSize) {
        _input.setFont(fontLabel, pointSize);
        _input.setMoveable(false);
        _multiText.setFont(fontLabel, pointSize);
    }

    public void add(String message) {
        if (SpsConfig.get().devConsoleEnabled) {
            _multiText.add(message);
        }
    }

    public void toggle() {
        if (SpsConfig.get().devConsoleEnabled) {
            _active = !_active;
            if (!PausePrompt.get().isActive() && _active) {
                PausePrompt.get().setActive(true);
            }

            _multiText.setVisible(_active);
            _input.setMessage("");
        }
    }

    public boolean isActive() {
        return _active;
    }

    private String getInput() {
        return _input.getMessage();
    }

    private void appendInput(String input) {
        String scrub = getInput();
        _input.setMessage(scrub + input);
    }

    public void register(DevConsoleAction action) {
        _actions.put(action.Id.toLowerCase(), action);
    }

    private void takeAction() {
        String input = getInput();
        if (input.trim().length() > 0) {
            try {
                DevParsedCommand command = new DevParsedCommand(input);
                if (_actions.containsKey(command.Id.toLowerCase())) {
                    String result = _actions.get(command.Id.toLowerCase()).act(command.Arguments);
                    if (result != null && !result.isEmpty()) {
                        add(result);
                    }
                }
                else {
                    add("Unknown command: " + input);
                }
            }
            catch (Exception e) {
                add("Exception caught while parsing command.");
            }
        }
        _input.setMessage("");
    }

    public void updateAndDraw() {
        if (_active) {
            try {
                for (Field keys : Input.Keys.class.getFields()) {
                    int key = keys.getInt(null);
                    if (key != Input.Keys.ANY_KEY) {
                        boolean pressed = Gdx.input.isKeyPressed(key);
                        if (pressed && !_locked[key]) {
                            if (key == Input.Keys.ENTER) {
                                if (!_input.getMessage().isEmpty()) {
                                    takeAction();
                                }
                            }
                            else if (key == Input.Keys.SPACE) {
                                appendInput(" ");
                            }
                            else if (key == Input.Keys.BACKSPACE || key == Input.Keys.DEL) {
                                if (getInput().length() > 0) {
                                    _input.setMessage(_input.getMessage().substring(0, _input.getMessage().length() - 1));
                                }
                            }
                            else {
                                //Only deal with single characters
                                String chars = Input.Keys.toString(key);
                                if (chars != null && chars.length() == 1) {
                                    if (!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && !Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
                                        chars = chars.toLowerCase();
                                    }
                                    appendInput(chars);
                                }
                            }
                            _locked[key] = true;
                        }
                        else if (!pressed) {
                            _locked[key] = false;
                        }
                    }
                }
            }
            catch (Exception swallow) {
                Logger.exception(swallow);
            }

            _multiText.draw();
            _input.draw();
        }
    }
}
