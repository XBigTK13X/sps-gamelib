package sps.entities;

import sps.bridge.*;
import sps.core.Point2;
import sps.display.Window;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class EntityManager {

    public static EntityManager __instance;

    public static void set(EntityManager entityManager) {
        __instance = entityManager;
    }

    public static EntityManager get() {
        if (__instance == null) {
            __instance = new EntityManager();
        }
        return __instance;
    }

    public static void reset() {
        __instance = new EntityManager();
    }

    private EntityManager() {
    }

    private List<Entity> _contents = new ArrayList<Entity>();
    private List<Entity> players = new ArrayList<Entity>();
    private HashMap<EntityType, List<Entity>> entityBuckets = new HashMap<EntityType, List<Entity>>();
    private HashMap<ActorType, List<IActor>> actorBuckets = new HashMap<ActorType, List<IActor>>();

    public Entity addEntity(Entity entity) {
        entity.loadContent();
        _contents.add(entity);
        Collections.sort(_contents);
        addToBuckets(entity);
        return entity;
    }

    public void addEntities(List<? extends Entity> cache) {
        for (Entity e : cache) {
            addEntity(e);
        }
    }

    private void addToBuckets(Entity entity) {
        if (entity.getEntityType() == EntityTypes.get(Sps.Entities.Actor)) {
            IActor actor = (IActor) entity;
            if (actor.getActorType() == ActorTypes.get(Sps.Actors.Player)) {
                players.add(entity);
            }
            if (!actorBuckets.containsKey(actor)) {
                actorBuckets.put(actor.getActorType(), new ArrayList<IActor>());
            }
            actorBuckets.get(actor.getActorType()).add(actor);
        }
        if (!entityBuckets.containsKey(entity.getEntityType())) {
            entityBuckets.put(entity.getEntityType(), new ArrayList<Entity>());
        }
        entityBuckets.get(entity.getEntityType()).add(entity);
    }

    public Entity getEntity(EntityType type) {
        if (_contents != null) {
            return entityBuckets.get(type).get(0);
        }
        return null;
    }

    private final List<Entity> _gopResults = new ArrayList<Entity>();
    private final List<Entity> _goResults = new ArrayList<Entity>();

    public List<Entity> getAllEntities() {
        return _contents;
    }

    public List<Entity> getEntities(EntityType type, Point2 target) {
        if (_contents != null) {
            _gopResults.clear();
            _goResults.clear();
            for (Entity goResult : _goResults) {
                if (goResult.getEntityType() == type) {
                    _gopResults.add(goResult);
                }
            }
            return _gopResults;
        }
        return null;
    }

    private List<Entity> empty = new ArrayList<Entity>();

    public List<Entity> getEntities(EntityType type) {
        if (entityBuckets.get(type) == null) {
            return empty;
        }
        return entityBuckets.get(type);
    }

    private final List<IActor> _creatures = new ArrayList<IActor>();

    public List<IActor> getActors(ActorType type) {
        _creatures.clear();
        if (type != ActorTypes.get(Sps.ActorGroups.Non_Player)) {
            if (actorBuckets.get(type) != null) {
                _creatures.addAll(actorBuckets.get(type));
            }
        }
        else {
            for (Entity elem : _contents) {
                if (elem.getEntityType() == EntityTypes.get(Sps.Entities.Actor)) {
                    if (((IActor) elem).getActorType() != ActorTypes.get(Sps.Actors.Player)) {
                        _creatures.add(((IActor) elem));
                    }
                }
            }
        }
        return _creatures;
    }

    private IActor _nextResult;

    private final List<IActor> _creatures2 = new ArrayList<IActor>();
    private Point2 buffer = new Point2(0, 0);

    public void removeEntity(Entity target) {
        _contents.remove(target);
        if (target.getEntityType() == EntityTypes.get(Sps.Entities.Actor)) {
            IActor actor = (IActor) target;
            if (actor.getActorType() == ActorTypes.get(Sps.Actors.Player)) {
                players.remove(target);
            }
            actorBuckets.remove(actor);
        }
        entityBuckets.get(target.getEntityType()).remove(target);
    }

    public void clear() {
        _contents.clear();
        actorBuckets.clear();
        entityBuckets.clear();
        players.clear();
    }

    public void update() {
        for (int ii = 0; ii < _contents.size(); ii++) {
            if (ii >= _contents.size()) {
                return;
            }
            if (!_contents.get(ii).isActive()) {
                removeEntity(_contents.get(ii));
                ii--;
                continue;
            }
            _contents.get(ii).update();
        }
    }

    public void draw() {
        if (Window.get() != null) {
            for (Entity component : _contents) {
                component.draw();
            }
        }
    }

    public void loadContent() {
        if (Window.get() != null) {
            for (Entity component : _contents) {
                component.loadContent();
            }
        }
    }

    public List<Entity> getPlayers() {
        return players;
    }

    public Entity getPlayer() {
        if (players.size() > 0) {
            return players.get(0);
        }
        return null;
    }

    public IActor getNearestPlayer(Entity target) {
        List<Entity> actors = getPlayers();
        if (actors.size() > 0) {
            Entity closest = actors.get(0);
            for (Entity actor : actors) {
                if (HitTest.getDistanceSquare(target, actor) < HitTest.getDistanceSquare(target, closest)) {
                    closest = actor;
                }
            }
            return (IActor) closest;
        }
        return null;
    }

    public Entity getNearest(Entity source, EntityType type) {
        List<Entity> targets = entityBuckets.get(type);
        if (targets.size() > 0) {
            Entity closest = targets.get(0);
            for (Entity target : targets) {
                if (target != source) {
                    if (HitTest.getDistanceSquare(source, target) < HitTest.getDistanceSquare(source, closest)) {
                        closest = target;
                    }
                }
            }
            return closest;
        }
        return null;
    }

    public void removeFromPlay(Entity entity) {
        //EntityCache.get().addToCache(entity);
        removeEntity(entity);
    }

    public void recalculateEdges() {
        for (int ii = 0; ii < _contents.size(); ii++) {
            if (ii >= _contents.size()) {
                return;
            }
            _contents.get(ii).recalculateEdge();
        }
    }
}
