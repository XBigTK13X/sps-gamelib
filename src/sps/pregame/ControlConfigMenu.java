package sps.pregame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import sps.data.UserFiles;
import org.apache.commons.io.FileUtils;
import sps.bridge.Command;
import sps.bridge.Commands;
import sps.core.Logger;
import sps.display.Screen;
import sps.input.InputBindings;
import sps.input.KeyCatcher;
import sps.input.Keys;
import sps.states.StateManager;
import sps.states.Systems;
import sps.text.Text;
import sps.text.TextPool;
import sps.ui.ButtonStyle;
import sps.ui.UIButton;
import sps.time.CoolDown;

import java.util.*;

public class ControlConfigMenu extends OptionsState {
    private static class PrettyCommand {
        public final String Command;
        public final String Display;

        public PrettyCommand(String command, String display) {
            Display = display;
            Command = command;
        }
    }

    public ControlConfigMenu(Sprite background, ConfigurableCommands commands) {
        super(background);
        _commands = commands;
    }

    private ConfigurableCommands _commands;

    private List<PrettyCommand> _prettyCommands;
    private Map<Command, UIButton> _buttons;
    private Map<Command, String> _chords;
    private CoolDown _delay;

    private Text _prompt;
    private UIButton _save;
    private UIButton _cancel;
    private Command _current;

    private int _columnHeight = 7;

    private String _chord;
    private KeyCatcher _catcher = new KeyCatcher() {
        @Override
        public void onDown(int keyCode) {
            for (Command command : Commands.values()) {
                if (command != _current) {
                    for (Keys key : command.keys()) {
                        if (key.getKeyCode() == keyCode) {
                            _prompt.setMessage("Duplicate key detected.\nUnable to save.");
                        }
                    }
                }
            }
            _chord += Keys.fromCode(keyCode) + "+";
            updateUI();
        }

        @Override
        public void onUp(int keyCode) {
            _chord = _chord.replace(Keys.fromCode(keyCode) + "+", "");
            updateUI();
        }
    };

    @Override
    public void create() {
        setupCommandNames();
        _buttons = new HashMap<>();
        _chords = new HashMap<>();
        _delay = new CoolDown(.1f);
        _delay.zeroOut();

        _prompt = Systems.get(TextPool.class).write("", Screen.pos(20, 13));
        _prompt.setVisible(false);

        ButtonStyle style = new ButtonStyle(20, 18, 15, 10, 15);
        int ii = 0;
        for (final PrettyCommand pretty : _prettyCommands) {
            final Command command = Commands.get(pretty.Command);
            UIButton config = new UIButton(getCommandInputString(command, command.toString())) {
                @Override
                public void click() {
                    configure(command);
                }
            };
            config.setFont("UIButton", 24);
            style.apply(config, ii / _columnHeight, _columnHeight - (ii++ % _columnHeight + 1));
            _buttons.put(command, config);
        }

        _save = new UIButton("Save", Commands.get("Menu1")) {
            @Override
            public void click() {
                for (Command command : _chords.keySet()) {
                    String chord = _chords.get(command);
                    if (chord.length() > 0) {
                        String[] keyIds = chord.split("\\+");
                        List<Keys> keys = new ArrayList<>();
                        int ii = 0;
                        for (String id : keyIds) {
                            keys.add(Keys.fromName(id));
                        }
                        command.bind(keys);
                    }
                }
                try {
                    FileUtils.writeLines(UserFiles.input(), InputBindings.toConfig());
                }
                catch (Exception e) {
                    Logger.exception(e, false);
                }
                _catcher.setActive(false);
                StateManager.get().pop();
            }
        };

        _cancel = new UIButton("Cancel", Commands.get("Menu6")) {
            @Override
            public void click() {
                _catcher.setActive(false);
                StateManager.get().pop();
            }
        };

        ButtonStyle style2 = new ButtonStyle(10, 5, 40, 10, 10);
        style2.apply(_save, 0, 0);
        style2.apply(_cancel, 1, 0);
    }

    private void setupCommandNames() {
        _prettyCommands = new LinkedList<>();
        for (String commandId : _commands.getIds()) {
            _prettyCommands.add(new PrettyCommand(commandId, _commands.getDisplay(commandId)));
        }
    }

    private void updateUI() {
        if (_current != null) {
            if (!_chord.isEmpty()) {
                _chords.put(_current, _chord);
                _delay.reset();
                _buttons.get(_current).setMessage(getCommandInputString(_current, "[" + getPersistableChord() + "]"));
            }
        }
    }

    private String getPersistableChord() {
        String chordFormat = _chord;
        if (chordFormat.length() > 0) {
            if (chordFormat.charAt(_chord.length() - 1) == '+') {
                chordFormat = _chord.substring(0, _chord.length() - 1);
            }
        }
        return chordFormat;
    }

    private void configure(Command command) {
        _chord = "";
        _current = command;
        _catcher.setActive(true);
        _save.setVisible(false);
        _cancel.setVisible(false);
        _prompt.setVisible(true);
        _prompt.setMessage("Waiting for a keypress...");
    }

    private String getCommandInputString(Command command, String chord) {
        for (PrettyCommand pretty : _prettyCommands) {
            if (command.name().equals(pretty.Command)) {
                return pretty.Display + "\n" + chord;
            }
        }
        return null;
    }

    @Override
    public void update() {
        if (!_delay.isCooled()) {
            if (_delay.updateAndCheck()) {
                if (!_prompt.getMessage().contains("Duplicate")) {
                    _prompt.setVisible(false);
                    _save.setVisible(true);
                }
                _cancel.setVisible(true);
                _delay.zeroOut();
                _catcher.setActive(false);
            }
        }
    }
}
