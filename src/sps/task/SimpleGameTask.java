package sps.task;

public abstract class SimpleGameTask extends GameTask {

    public SimpleGameTask(String name) {
        super(name);
    }

    public abstract void action();

    @Override
    public boolean start() {
        action();
        return true;
    }
}
