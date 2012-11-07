package sps.graphics;

import aigilas.management.SpriteType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import sps.bridge.DrawDepth;
import sps.core.GameManager;
import sps.core.Point2;

public class AnimatedTexture {
    private int _currentFrame;
    private SpriteInfo _spriteInfo;
    private int _animationTimer;
    private Color _color = Color.WHITE;
    private Sprite _sprite;
    private DrawDepth _depth = DrawDepth.AnimatedTexture;

    protected Point2 _position = Point2.Zero;

    public void loadContent(SpriteType assetName) {
        _spriteInfo = SpriteSheetManager.getSpriteInfo(assetName);
        _currentFrame = 0;
        _animationTimer = GameManager.AnimationFps;
    }

    public void draw() {
        if (_sprite == null) {
            _sprite = Assets.get().sprite(_spriteInfo.SpriteIndex);
        }
        if (_color.a > 0) {
            Assets.get().setIndices(_sprite, _currentFrame, _spriteInfo.SpriteIndex);
            updateAnimation();
            Renderer.get().draw(_sprite, _position, _depth, _color);
        }
    }

    private void updateAnimation() {
        if (_spriteInfo.MaxFrame != 1) {
            _animationTimer--;
            if (_animationTimer <= 0) {
                _currentFrame = (_currentFrame + 1) % _spriteInfo.MaxFrame;
                _animationTimer = GameManager.AnimationFps;
            }
        }
    }

    public void setSpriteInfo(SpriteInfo sprite) {
        if (_spriteInfo != sprite) {
            _spriteInfo = sprite;
            _currentFrame = 0;
        }
    }

    public void setPosition(float x, float y) {
        _position.X = (int) x;
        _position.Y = (int) y;
    }

    public void setPosition(Point2 position) {
        _position = new Point2(position.PosX, position.PosY);
    }

    public void setColor(Color color) {
        _color = color;
    }

    public Color getColor() {
        return _color;
    }

    public void setAlpha(int alpha) {
        _color = new Color(_color.r, _color.g, _color.b, alpha);
    }

    public void setDrawDepth(DrawDepth depth) {
        _depth = depth;
    }

    public DrawDepth getDepth() {
        return _depth;
    }
}