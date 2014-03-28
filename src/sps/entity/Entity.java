package sps.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import sps.bridge.DrawDepth;
import sps.bridge.DrawDepths;
import sps.core.Point2;
import sps.display.Window;
import sps.states.Systems;
import sps.util.BoundingBox;

public class Entity {
    private Point2 _position;
    private Sprite _sprite;
    private boolean _active;
    private DrawDepth _depth;
    private BoundingBox _bounds;

    public Entity() {
        Systems.get(Entities.class).add(this);
        _position = new Point2(0, 0);
        _depth = DrawDepths.get("Default");
        _active = true;
        _bounds = BoundingBox.empty();
    }

    private void resetBounds() {
        if (_sprite != null) {
            BoundingBox.fromDimensions(_bounds, _position.X, _position.Y, (int) _sprite.getWidth(), (int) _sprite.getHeight());
        }
    }

    public void setSprite(Sprite sprite) {
        _sprite = sprite;
        resetBounds();
    }

    public Sprite getSprite(){
        return _sprite;
    }

    public void setActive(boolean active) {
        _active = active;
    }

    public void setPosition(float x, float y) {
        _position.reset(x, y);
        if (_sprite != null) {
            _sprite.setPosition(x, y);
        }
        resetBounds();
    }

    public void setPosition(Point2 position){
        setPosition(position.X,position.Y);
    }

    public Point2 getPosition() {
        return _position;
    }

    public BoundingBox getBounds() {
        return _bounds;
    }

    public void setDrawDepth(DrawDepth depth) {
        _depth = depth;
    }

    public boolean isActive() {
        return _active;
    }

    public void update() {

    }

    public void draw() {
        if (_sprite != null) {
            Window.get().schedule(_sprite, _depth);
        }
    }

    public boolean move(float amountX, float amountY) {
        amountX *= Gdx.graphics.getDeltaTime();
        amountY *= Gdx.graphics.getDeltaTime();
        if (amountX != 0 || amountY != 0) {
            setPosition(_position.X + amountX, _position.Y + amountY);
            return true;
        }
        return false;
    }
}
