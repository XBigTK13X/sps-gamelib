package sps.preload;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.lwjgl.opengl.Display;
import sps.bridge.Sps;
import sps.core.RNG;
import sps.core.SpsEngineChainLink;
import sps.draw.SpriteMaker;
import sps.preload.PreloadChain;
import sps.preload.PreloadChainLink;

public class SpsBootstrap implements SpsEngineChainLink{
    private SpriteBatch _batch;
    private PreloadChain _bootstrap;
    private Sprite _logo;
    private int _windowWidth;
    private int _windowHeight;

    public SpsBootstrap() {
        _bootstrap = new PreloadChain(false) {
            @Override
            public void finish() {

            }
        };
        _bootstrap.add(new PreloadChainLink("Bootstrap spritebatch") {
            @Override
            public void process() {
                _batch = new SpriteBatch();
            }
        });
        _bootstrap.add(new PreloadChainLink("Bootstrap logo") {
            @Override
            public void process() {
                _logo = SpriteMaker.fromGraphic("sps-gamelib-logo.png");
                _windowHeight = Display.getHeight();
                _windowWidth = Display.getWidth();
                _logo.setPosition((_windowWidth - _logo.getWidth()) / 2, (_windowHeight - _logo.getHeight()) / 2);
            }
        });
    }

    public boolean isFinished() {
        return _bootstrap.isFinished();
    }

    public void update() {
        _bootstrap.update();
    }

    public void draw() {
        if (_logo != null) {
            _batch.begin();
            _logo.draw(_batch);
            _batch.end();
        }
    }
}
