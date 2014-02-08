package sps.task;

import sps.core.Logger;
import sps.states.GameSystem;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GameTasks implements GameSystem {
    private List<GameTask> _tasks;
    private GameTask _current;

    public GameTasks() {
        super();
        _tasks = new LinkedList<>();
    }

    public void schedule(GameTask task) {
        schedule(task, false);
    }

    public void schedule(GameTask task, boolean perpetual) {
        task.setPerpetual(perpetual);
        task.setActive(true);
        if (task.start()) {
            _tasks.add(task);
            task.setStartSuccess(true);
            Logger.info("Starting task: " + task.getName());
            return;
        }
        else {
            task.setStartSuccess(false);
        }
    }

    @Override
    public void update() {
        Iterator<GameTask> iter = _tasks.iterator();
        while (iter.hasNext()) {
            _current = iter.next();
            _current.update();
            if (!_current.isActive()) {
                Logger.info("Completed task, removing: " + _current.getName());
                _current.complete();
                iter.remove();
            }
        }
    }

    @Override
    public void draw() {

    }
}
