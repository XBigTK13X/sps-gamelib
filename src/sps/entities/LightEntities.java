package sps.entities;

import sps.bridge.ActorTypes;
import sps.bridge.EntityTypes;
import sps.bridge.Sps;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LightEntities {
    public static LightEntities __instance;

    public static void set(LightEntities entities) {
        __instance = entities;
    }

    public static LightEntities get() {
        if (__instance == null) {
            __instance = new LightEntities();
        }
        return __instance;
    }

    public static void reset() {
        __instance = new LightEntities();
    }


    private Map<Class, List<LightEntity>> _buckets;
    private List<LightEntity> _entities;

    private LightEntities() {
        _buckets = new HashMap();
        _entities = new LinkedList();
    }

    public void add(LightEntity entity) {
        _entities.add(entity);
        if (!_buckets.containsKey(entity)) {
            _buckets.put(entity.getClass(), new LinkedList());
        }
        _buckets.get(entity.getClass()).add(entity);
    }

    public <T extends LightEntity> T first(Class<T> type) {
        return type.cast(_buckets.get(type).get(0));
    }

    public <T extends LightEntity> List<T> all(Class<T> type) {
        @SuppressWarnings("unchecked")
        List<T> result = (List) _buckets.get(type);
        return result;
    }

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

    public void draw() {
        for (LightEntity entity:_entities){
            entity.draw();
        }
    }

    private void remove(LightEntity target) {
        _entities.remove(target);
        _buckets.get(target.getClass()).remove(target);
    }
}
