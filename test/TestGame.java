import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import sps.bridge.DrawDepth;
import sps.bridge.DrawDepths;
import sps.core.Core;
import sps.core.Point2;
import sps.graphics.Assets;
import sps.graphics.Renderer;

public class TestGame implements ApplicationListener {
    ShapeRenderer r;

    @Override
    public void create() {
        r = new ShapeRenderer();
    }

    @Override
    public void resize(int width, int height) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render() {
        Renderer.get().begin();
        Renderer.get().draw(Assets.get().sprite(0, 1), new Point2(0, 0), DrawDepths.get(Core.DrawDepths.Animated_Texture),Color.WHITE);
        Renderer.get().end();
    }

    @Override
    public void pause() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resume() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void dispose() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
