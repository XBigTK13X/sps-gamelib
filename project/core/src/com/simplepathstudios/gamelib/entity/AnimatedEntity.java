package com.simplepathstudios.gamelib.entity;

import com.simplepathstudios.gamelib.bridge.DrawDepth;
import com.simplepathstudios.gamelib.display.Point2;
import com.simplepathstudios.gamelib.display.Animation;
import com.simplepathstudios.gamelib.display.SpriteDefinition;
import com.simplepathstudios.gamelib.display.SpriteEdge;

public class AnimatedEntity extends Entity {
    private Animation _graphic;
    private float _lastMoveX = 1;
    private float _lastMoveY = 1;

    public AnimatedEntity(Point2 location, SpriteDefinition spriteDefinition, DrawDepth depth) {
        super();
        _graphic = new Animation(spriteDefinition, depth);
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

    public boolean move(float amountX, float amountY) {
        if ((amountX > 0 && _lastMoveX <= 0) || (amountX < 0 && _lastMoveX >= 0)) {
            _lastMoveX = amountX;
            getGraphic().flipX();
        }
        return super.move(amountX, amountY);
    }

    protected void setSpriteInfo(SpriteDefinition spriteDefinition) {
        _graphic.setSpriteInfo(spriteDefinition);
    }

    public DrawDepth getDepth() {
        return _graphic.getDepth();
    }

    public void recalculateEdge() {
        if (_graphic.hasDynamicEdges()) {
            _graphic.setEdge(SpriteEdge.determine());
        }
    }

    public Animation getGraphic() {
        return _graphic;
    }
}
