package sps.entities;

import com.badlogic.gdx.Gdx;
import sps.bridge.DrawDepth;
import sps.bridge.EntityType;
import sps.bridge.SpriteType;
import sps.core.Point2;
import sps.core.SpsConfig;
import sps.display.Animation;
import sps.display.SpriteEdge;
import sps.display.SpriteInfo;

public class Entity implements Comparable<Entity> {

    private static int isNeg = 1;
    private static int factorsOfSpriteHeight = 0;
    protected final Animation _graphic = new Animation();
    protected final Point2 _location = new Point2(0, 0);
    private final Point2 oldLocation = new Point2(0, 0);
    private final Point2 target = new Point2(0, 0);
    protected boolean _isActive = true;
    protected Boolean _isBlocking;
    protected SpriteType _assetName;
    protected boolean _isOnBoard = true;
    protected EntityType _entityType;
    private boolean _isInteracting = false;
    private boolean facingLeft = true;

    private int _width;
    private int _height;

    private static float normalizeDistance(float amount) {
        isNeg = (amount < 0) ? -1 : 1;
        amount = Math.abs(amount);
        factorsOfSpriteHeight = (int) Math.floor(amount / SpsConfig.get().spriteHeight);
        factorsOfSpriteHeight = (factorsOfSpriteHeight == 0 && amount != 0) ? 1 : factorsOfSpriteHeight;
        return (SpsConfig.get().spriteHeight * factorsOfSpriteHeight * isNeg);
    }

    protected void initialize(int width, int height, Point2 location, SpriteType spriteType, EntityType entityType, DrawDepth depth) {
        _width = width;
        _height = height;
        _assetName = spriteType;
        _entityType = entityType;
        _location.copy(location);
        _graphic.setPosition(_location);
        _graphic.setDrawDepth(depth);
        _graphic.loadContent(spriteType);
    }

    public void loadContent() {
        _graphic.loadContent(_assetName);
    }

    public void draw() {
        if (_isOnBoard && _isActive) {
            _graphic.draw();
        }
    }

    public void hide() {
        _isOnBoard = false;
    }

    public void show() {
        _isOnBoard = true;
    }

    public void update() {
    }

    public void updateLocation(Point2 location) {
        oldLocation.reset(_location.X, _location.Y);
        _graphic.setPosition(location);
        _location.reset(location.X, location.Y);
    }

    public void setFacingLeft(boolean value) {
        facingLeft = value;
        _graphic.flip(!facingLeft, false);
    }

    public boolean move(float amountX, float amountY) {
        return move(amountX, amountY, false);
    }

    public boolean move(float amountX, float amountY, boolean normalizeToSpriteDimensions) {
        if (normalizeToSpriteDimensions) {
            amountX = normalizeDistance(amountX);
            amountY = normalizeDistance(amountY);
        }
        amountX *= Gdx.graphics.getDeltaTime();
        amountY *= Gdx.graphics.getDeltaTime();
        target.reset(_location.X + amountX, _location.Y + amountY);
        if (amountX > 0) {
            setFacingLeft(false);
        }
        if (amountX < 0) {
            setFacingLeft(true);
        }
        if (target.X != 0 || target.Y != 0) {
            updateLocation(target);
            return true;
        }
        return false;
    }

    public boolean isActive() {
        return _isActive;
    }

    public void setInactive() {
        _isActive = false;
    }

    public boolean isBlocking() {
        if (_isBlocking == null) {
            return false;
        }
        return _isBlocking;
    }

    public Point2 getLocation() {
        return _location;
    }

    public void setLocation(Point2 location) {
        _graphic.setPosition(location);
        _location.copy(location);
    }

    public boolean isGraphicLoaded() {
        return (_graphic != null);
    }

    protected void setSpriteInfo(SpriteInfo sprite) {
        _graphic.setSpriteInfo(sprite);
    }

    public DrawDepth getDepth() {
        return _graphic.getDepth();
    }

    public void setInteraction(boolean isInteracting) {
        _isInteracting = isInteracting;
    }

    public boolean isInteracting() {
        return _isInteracting;
    }

    public void setInteracting(boolean isInteracting) {
        _isInteracting = isInteracting;
    }

    public EntityType getEntityType() {
        return _entityType;
    }

    public void recalculateEdge() {
        if (_graphic.hasDynamicEdges()) {
            _graphic.setEdge(SpriteEdge.determine(_entityType, _location));
        }
    }

    public int getWidth() {
        return _width;
    }

    public int getHeight() {
        return _height;
    }

    public void setSize(int width, int height) {
        _width = width;
        _height = height;
    }

    @Override
    public int compareTo(Entity entity) {
        return getDepth().DrawDepth - entity.getDepth().DrawDepth;
    }
}
