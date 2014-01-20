package sps.main;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.lwjgl.opengl.Display;
import sps.bridge.Sps;
import sps.core.RNG;
import sps.draw.SpriteMaker;
import sps.preload.PreloadChain;
import sps.preload.PreloadChainLink;

public class SpsLoader {
    private SpriteBatch _batch;
    private PreloadChain _bootstrap;
    private PreloadChain _preload;
    private Sprite _logo;
    private int _windowWidth;
    private int _windowHeight;

    public SpsLoader(PreloadChain preload) {
        _preload = preload;
        _bootstrap = new PreloadChain(false) {
            @Override
            public void finish() {

            }
        };
        _bootstrap.add(new PreloadChainLink() {
            @Override
            public void process() {
                _batch = new SpriteBatch();
            }
        });
        _bootstrap.add(new PreloadChainLink() {
            @Override
            public void process() {
                _logo = SpriteMaker.fromGraphic("sps-gamelib-logo.png");
                _windowHeight = Display.getHeight();
                _windowWidth = Display.getWidth();
                _logo.setPosition((_windowWidth - _logo.getWidth()) / 2, (_windowHeight - _logo.getHeight()) / 2);
            }
        });
        _bootstrap.add(new PreloadChainLink() {
            @Override
            public void process() {
                RNG.naturalReseed();
                Sps.setup(DesktopTarget.get(),true);
            }
        });
    }

    public boolean isFinished() {
        return _preload.isFinished();
    }

    public void update() {
        if (_bootstrap.isFinished()) {
            _preload.update();
        }
        else {
            _bootstrap.update();
        }
    }

    public void draw() {
        if (_logo != null) {
            _batch.begin();
            _logo.draw(_batch);
            _batch.end();
        }
    }
}
