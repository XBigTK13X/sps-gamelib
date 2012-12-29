package sps.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import sps.bridge.DrawDepth;
import sps.bridge.DrawDepths;
import sps.bridge.SpriteType;
import sps.core.Core;
import sps.core.Point2;

public class AnimatedTexture {
    private int _currentFrame;
    private SpriteInfo _spriteInfo;
    private int _animationTimer;
    private Color _color = Color.WHITE;
    private Sprite _sprite;
    private DrawDepth _depth;
    private int _rotation = 0;
    private boolean animationEnabled = true;

    protected Point2 _position = new Point2(0, 0);

    public AnimatedTexture() {
        _depth = DrawDepths.get(Core.DrawDepths.Animated_Texture);
        setEdge(SpriteEdge.None);
    }

    public void setAnimationEnabled(boolean value) {
        animationEnabled = value;
    }

    public void loadContent(SpriteType assetName) {
        _spriteInfo = SpriteSheetManager.getSpriteInfo(assetName);
        _animationTimer = Core.AnimationFps;
    }

    public void draw() {
        if (_sprite == null) {
            _sprite = Assets.get().sprite(_spriteInfo.SpriteIndex);
        }
        if (_color.a > 0) {
            _sprite.setRotation(_rotation);
            _sprite = Assets.get().sprite(_currentFrame, _spriteInfo.SpriteIndex);
            updateAnimation();
            Renderer.get().draw(_sprite, _position, _depth, _color);
        }
    }

    private void updateAnimation() {
        if (animationEnabled && _spriteInfo.MaxFrame != 1) {
            _animationTimer--;
            if (_animationTimer <= 0) {
                _currentFrame = (_currentFrame + 1) % _spriteInfo.MaxFrame;
                _animationTimer = Core.AnimationFps;
            }
        }
    }

    public void setSpriteInfo(SpriteInfo sprite) {
        if (_spriteInfo != sprite) {
            _spriteInfo = sprite;
            _currentFrame = 0;
        }
    }

    public void setPosition(Point2 position) {
        _position.reset(position.PosX, position.PosY);
    }

    public void setColor(Color color) {
        _color = color;
    }

    public Color getColor() {
        return _color;
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
    }
}
