package sps.task;

import sps.core.Logger;
import sps.core.SpsConfig;
import sps.states.GameSystem;

import java.util.Iterator;
import java.util.LinkedList;

public class GameTasks implements GameSystem {
    private LinkedList<GameTask> _tasks;
    private LinkedList<GameTask> _onCompleteTasks;
    private LinkedList<GameTask> _onCancelTasks;
    private GameTask _current;

    public GameTasks() {
        super();
        _tasks = new LinkedList<>();
        _onCompleteTasks = new LinkedList<>();
        _onCancelTasks = new LinkedList<>();
    }

    public void schedule(GameTask task) {
        if (SpsConfig.get().gameTasksTimeDilationEnabled) {
            task.setLengthInSeconds(task.getLengthInSeconds() * SpsConfig.get().gameTasksTimeDilation);
        }
        task.setActive(true);
        if (task.start()) {
            _tasks.add(task);
            task.setStartSuccess(true);
            if (SpsConfig.get().taskLoggingEnabled) {
                String startInfo = "Starting task: " + task.getName();
                if (task.hasActionEachFrame()) {
                    startInfo += ". Acting each frame until complete";
                }
                else {
                    startInfo += ". Acting for " + task.getLengthInSeconds() + " seconds";
                }
                Logger.info(startInfo);
            }
            return;
        }
        else {
            if (SpsConfig.get().taskLoggingEnabled) {
                Logger.info("Unable to start task: " + task.getName());
            }
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
                if (_current.isCancelled()) {
                    if (_current.onCancel() != null) {
                        if (SpsConfig.get().taskLoggingEnabled) {
                            Logger.info("Cancelling task: " + _current.getName());
                        }
                        _onCancelTasks.add(_current.onCancel());
                    }
                }
                else {
                    if (_current.onComplete() != null) {
                        if (SpsConfig.get().taskLoggingEnabled) {
                            Logger.info("Completing task: " + _current.getName());
                        }
                        _onCompleteTasks.add(_current.onComplete());
                    }
                }

                iter.remove();
            }
        }

        for (GameTask task : _onCompleteTasks) {
            schedule(task);
        }
        _onCompleteTasks.clear();

        for (GameTask task : _onCancelTasks) {
            schedule(task);
        }
        _onCancelTasks.clear();

    }

    @Override
    public void draw() {

    }
}
