package sps.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import sps.bridge.DrawDepth;
import sps.bridge.DrawDepths;
import sps.core.Logger;
import sps.core.Point2;
import sps.display.Window;

public class LightEntity {
    private Point2 _position;
    private Sprite _sprite;
    private boolean _active;
    private DrawDepth _depth;

    public LightEntity() {
        LightEntities.get().add(this);
        _position = new Point2(0, 0);
        _depth = DrawDepths.get("Default");
        _active = true;
    }

    public void setSprite(Sprite sprite) {
        _sprite = sprite;
    }

    public void setActive(boolean active) {
        _active = active;
    }

    public void setPosition(Point2 position) {
        _position.reset(position.X, position.Y);
    }

    public Point2 getPosition() {
        return _position;
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
            _position.reset(_position.X + amountX, _position.Y + amountY);
            _sprite.setPosition(_position.X, _position.Y);
            return true;
        }
        return false;
    }
}
