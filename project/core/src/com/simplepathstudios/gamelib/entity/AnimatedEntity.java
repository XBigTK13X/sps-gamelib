package com.simplepathstudios.gamelib.entity;

import com.simplepathstudios.gamelib.bridge.DrawDepth;
import com.simplepathstudios.gamelib.bridge.SpriteType;
import com.simplepathstudios.gamelib.core.Point2;
import com.simplepathstudios.gamelib.display.Animation;
import com.simplepathstudios.gamelib.display.SpriteEdge;
import com.simplepathstudios.gamelib.display.SpriteInfo;

public class AnimatedEntity extends Entity {
    private Animation _graphic;
    private boolean _facingLeft = true;

    public AnimatedEntity(Point2 location, SpriteType spriteType, DrawDepth depth) {
        super();
        _graphic = new Animation(spriteType, depth);
        setPosition(location.X, location.Y);
    }

    @Override
    public void draw() {
        _graphic.draw();
    }

    @Override
    public void setPosition(float x, float y) {
        _graphic.setPosition(x, y);
        super.setPosition(x, y);
    }

    public void setFacingLeft(boolean value) {
        _facingLeft = value;
        _graphic.flip(!_facingLeft, false);
    }

    public boolean move(float amountX, float amountY) {
        if (amountX > 0) {
            setFacingLeft(false);
        }
        if (amountX < 0) {
            setFacingLeft(true);
        }
        return super.move(amountX, amountY);
    }

    protected void setSpriteInfo(SpriteInfo spriteInfo) {
        _graphic.setSpriteInfo(spriteInfo);
    }

    public DrawDepth getDepth() {
        return _graphic.getDepth();
    }

    public void recalculateEdge() {
        if (_graphic.hasDynamicEdges()) {
            _graphic.setEdge(SpriteEdge.determine());
        }
    }
}
