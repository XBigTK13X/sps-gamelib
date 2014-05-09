package com.simplepathstudios.gamelib.thread;

import com.simplepathstudios.gamelib.bridge.Commands;
import com.simplepathstudios.gamelib.display.Screen;
import com.simplepathstudios.gamelib.input.InputWrapper;
import com.simplepathstudios.gamelib.states.GlobalStateResolver;
import com.simplepathstudios.gamelib.states.State;
import com.simplepathstudios.gamelib.states.StateManager;
import com.simplepathstudios.gamelib.states.Systems;
import com.simplepathstudios.gamelib.text.Text;
import com.simplepathstudios.gamelib.text.TextPool;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class CrashNotification implements State {
    private File _notice;

    public CrashNotification(File notice) {
        _notice = notice;
    }

    @Override
    public void create() {
        String message = "";
        try {
            message = FileUtils.readFileToString(_notice);
            FileUtils.forceDelete(_notice);
        }
        catch (Exception e) {
            message = "A previous crash was detected, but a reason for the crash was not found.";
        }
        String proceed = "\n\nPress " + Commands.get("Confirm") + " to continue";
        Text display = Systems.get(TextPool.class).write(message + proceed, Screen.pos(10, 80));
        display.setFont("Console", 30);
    }

    @Override
    public void draw() {

    }

    @Override
    public void update() {
        if (InputWrapper.isActive("Confirm")) {
            StateManager.get().push(GlobalStateResolver.get().createInitial());
        }
    }

    @Override
    public void asyncUpdate() {

    }

    @Override
    public void load() {

    }

    @Override
    public void unload() {

    }

    @Override
    public String getName() {
        return "Crash Notification";
    }

    @Override
    public void pause() {

    }
}
