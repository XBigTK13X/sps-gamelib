package sps.entity;

import sps.states.GameSystem;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Entities implements GameSystem {
    private Map<Class, List<Entity>> _buckets;
    private List<Entity> _entities;

    public Entities() {
        _buckets = new HashMap<>();
        _entities = new LinkedList<>();
    }

    public void add(Entity entity) {
        _entities.add(entity);
        if (!_buckets.containsKey(entity)) {
            _buckets.put(entity.getClass(), new LinkedList<Entity>());
        }
        _buckets.get(entity.getClass()).add(entity);
    }

    public <T extends Entity> T getFirst(Class<T> type) {
        return type.cast(_buckets.get(type).get(0));
    }

    public <T extends Entity> List<T> getAll(Class<T> type) {
        if (!_buckets.containsKey(type)) {
            return new LinkedList<T>();
        }
        @SuppressWarnings("unchecked")
        List<T> result = (List) _buckets.get(type);
        return result;
    }

    @Override
    public void update() {
        for (int ii = 0; ii < _entities.size(); ii++) {
            if (ii >= _entities.size()) {
                return;
            }
            if (!_entities.get(ii).isActive()) {
                remove(_entities.get(ii));
                ii--;
                continue;
            }
            _entities.get(ii).update();
        }
    }

    @Override
    public void draw() {
        for (Entity entity : _entities) {
            entity.draw();
        }
    }

    private void remove(Entity target) {
        _entities.remove(target);
        _buckets.get(target.getClass()).remove(target);
    }
}
