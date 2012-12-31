import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import sps.bridge.DrawDepth;
import sps.bridge.DrawDepths;
import sps.bridge.Spx;
import sps.core.Core;
import sps.core.Point2;
import sps.graphics.Assets;
import sps.graphics.Renderer;

public class TestGame implements ApplicationListener {
    @Override
    public void create() {
        Spx.setup();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
        Renderer.get().begin();
        Renderer.get().draw(Assets.get().sprite(0, 1), new Point2(0, 0), DrawDepths.get(Core.DrawDepths.Animated_Texture),Color.WHITE);
        Renderer.get().draw(Assets.get().sprite(0, 1), new Point2(0, Renderer.get().VirtualHeight - 64), DrawDepths.get(Core.DrawDepths.Animated_Texture),Color.WHITE);
        Renderer.get().draw(Assets.get().sprite(0, 1), new Point2(Renderer.get().VirtualWidth - 64, 0), DrawDepths.get(Core.DrawDepths.Animated_Texture),Color.WHITE);
        Renderer.get().draw(Assets.get().sprite(0, 1), new Point2(Renderer.get().VirtualWidth - 64, Renderer.get().VirtualHeight - 64), DrawDepths.get(Core.DrawDepths.Animated_Texture),Color.WHITE);
        Renderer.get().end();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
