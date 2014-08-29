package com.simplepathstudios.gamelib.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.simplepathstudios.gamelib.bridge.DrawDepth;
import com.simplepathstudios.gamelib.color.Color;
import com.simplepathstudios.gamelib.core.RNG;

public class Animation {
    protected Point2 _position = new Point2(0, 0);
    private int _currentFrame;
    private SpriteDefinition _spriteDefinition;
    private float _animationLengthSeconds;
    private Color _color = Color.WHITE;
    private Sprite _sprite;
    private DrawDepth _depth;
    private int _rotation = 0;
    private boolean animationEnabled = true;
    private float flashes = 30;
    private float flashCount = flashes + 1;
    private Color flashColor = Color.BLUE;
    private int alternateCount = 7;
    private boolean alternate;
    private SpriteEdge _edge = null;
    private boolean _dynamicEdges = false;
    private boolean _lastFlipX = false;
    private boolean _lastFlipY = false;

    public Animation(SpriteDefinition spriteDefinition, DrawDepth depth) {
        _depth = depth;
        _spriteDefinition = spriteDefinition;
        _animationLengthSeconds = _spriteDefinition.TimeSeconds;
        _sprite = Assets.get().sprite(_spriteDefinition.Index);
    }

    public void setAnimationEnabled(boolean value) {
        animationEnabled = value;
    }

    public void draw() {
        if (_color.a > 0) {
            _sprite.setRotation(_rotation);
            updateAnimation();

            if (flashCount < flashes) {
                if (flashCount % alternateCount == 1) {
                    alternate = !alternate;
                }
                flashCount++;
                if (flashCount >= flashes) {
                    alternate = false;
                }
            }

            Color renderColor = (alternate) ? _color.mul(flashColor) : _color;
            _sprite.setPosition(_position.X, _position.Y);
            _sprite.setColor(renderColor.getGdxColor());
            Window.get().schedule(_sprite, _depth);
        }
    }

    private void updateAnimation() {
        if (animationEnabled && _spriteDefinition.MaxFrame > 1) {
            _animationLengthSeconds -= Gdx.graphics.getDeltaTime();
            if (_animationLengthSeconds <= 0) {
                _animationLengthSeconds = _spriteDefinition.TimeSeconds;
            }
            _currentFrame = (int) (((_spriteDefinition.TimeSeconds - _animationLengthSeconds) / _spriteDefinition.TimeSeconds) * _spriteDefinition.MaxFrame);
        }
        _sprite = Assets.get().sprite(_currentFrame, _spriteDefinition.Index);
    }

    public void setSpriteInfo(SpriteDefinition sprite) {
        if (_spriteDefinition != sprite) {
            _spriteDefinition = sprite;
            _currentFrame = 0;
        }
    }

    public void setPosition(float x, float y) {
        _position.reset(x, y);
    }

    public Color getColor() {
        return _color;
    }

    public void setColor(Color color) {
        _color = color;
    }

    private void setFlip(boolean x, boolean y) {
        if (x != _lastFlipX) {
            _lastFlipX = x;
            for (int frame = 0; frame < _spriteDefinition.MaxFrame; frame++) {
                Sprite sprite = Assets.get().sprite(frame, _spriteDefinition.Index);
                sprite.flip(true, sprite.isFlipY());
            }
        }
        if (y != _lastFlipY) {
            _lastFlipY = y;
            for (int frame = 0; frame < _spriteDefinition.MaxFrame; frame++) {
                Sprite sprite = Assets.get().sprite(frame, _spriteDefinition.Index);
                sprite.flip(sprite.isFlipX(), true);
            }
        }
    }

    public void flipX() {
        setFlip(!_lastFlipX, _lastFlipY);
    }

    public void flipY() {
        setFlip(_lastFlipX, !_lastFlipY);
    }

    public void setAlpha(float alpha) {
        _color = new Color(_color.r, _color.g, _color.b, alpha);
    }

    public void setDrawDepth(DrawDepth depth) {
        _depth = depth;
    }

    public DrawDepth getDepth() {
        return _depth;
    }

    public void setRotation(int degrees) {
        _rotation = degrees;
    }

    public void setEdge(SpriteEdge edge) {
        _currentFrame = edge.Frame;
        _rotation = edge.Rotation;
        _edge = edge;
    }

    public void gotoRandomFrame() {
        gotoRandomFrame(true);
    }

    public void gotoRandomFrame(boolean disableAnimation) {
        setAnimationEnabled(!disableAnimation);
        _currentFrame = RNG.next(0, _spriteDefinition.MaxFrame, false);
    }

    public void flash(Color color) {
        flashCount = 0;
        flashColor = color;
    }

    public SpriteEdge getSpriteEdge() {
        return _edge;
    }

    public void setDynamicEdges(boolean value) {
        _dynamicEdges = value;
    }

    public boolean hasDynamicEdges() {
        return _dynamicEdges;
    }
}
