package sps.display.render;

import com.badlogic.gdx.graphics.g2d.Sprite;
import sps.bridge.DrawDepth;
import sps.color.Color;
import sps.core.Logger;
import sps.core.Point2;
import sps.display.DrawApiCall;
import sps.particles.ParticleLease;

public class Renderer {
    private ScreenRenderEngine _screenEngine;
    private RenderScheduler _scheduler;

    public Renderer(int width, int height) {
        _screenEngine = new ScreenRenderEngine(width, height);
        _scheduler = new RenderScheduler();
    }

    public void processScheduledApiCalls() {
        _scheduler.sort();
        _screenEngine.begin();

        for (RenderApiCall call : _scheduler.getRenderApiCalls()) {
            //Sprite
            if (call.Sprite != null) {
                _screenEngine.render(call.Sprite);
            }
            //Text
            else if (call.Content != null) {
                _screenEngine.render(call.Content, call.Location, call.Filter, call.FontLabel, call.PointSize, call.Scale);
            }
            //Particle
            else if (call.Particles != null) {
                _screenEngine.render(call.Particles);
            }
            else {
                Logger.exception(new Exception("Unknown render call"));
            }
        }
        _screenEngine.end();

        for (DrawApiCall call : _scheduler.getDrawApiCalls()) {
            _screenEngine.render(call);
        }

        _scheduler.clear();
    }

    public void schedule(DrawApiCall apiCall) {
        _scheduler.schedule(apiCall);
    }

    public void schedule(Sprite sprite, DrawDepth depth) {
        _scheduler.schedule(sprite, depth);
    }

    public void schedule(String content, Point2 location, Color filter, String fontLabel, int pointSize, float scale, DrawDepth depth) {
        _scheduler.schedule(content, location, filter, fontLabel, pointSize, scale, depth);
    }

    public void schedule(ParticleLease lease) {
        _scheduler.schedule(lease);
    }

    public ScreenRenderEngine screenEngine() {
        return _screenEngine;
    }
}
