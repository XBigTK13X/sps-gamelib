package com.simplepathstudios.gamelib.task;

public abstract class EachFrameGameTask extends GameTask {
    public EachFrameGameTask(String name) {
        super(name);
        makeEachFrameTask();
    }

    @Override
    public boolean start() {
        return true;
    }

    @Override
    public boolean updateAction() {
        return action();
    }

    public abstract boolean action();
}
