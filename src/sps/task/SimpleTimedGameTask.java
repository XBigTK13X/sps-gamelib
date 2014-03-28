package sps.task;

public abstract class SimpleTimedGameTask extends GameTask {
    public SimpleTimedGameTask(String name, float lengthInSeconds){
        super(name,lengthInSeconds);
    }

    public abstract void action();

    @Override
    public boolean start() {
        action();
        return true;
    }
}
