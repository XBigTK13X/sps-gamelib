import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Color;
import sps.bridge.DrawDepths;
import sps.bridge.Spx;
import sps.core.Core;
import sps.core.Point2;
import sps.graphics.Assets;
import sps.graphics.FrameStrategy;
import sps.graphics.Renderer;

public class TestGame implements ApplicationListener {
    @Override
    public void create() {
        Spx.setup();
        Renderer.get().setStrategy(new FrameStrategy());
        Renderer.get().setWindowsBackground(Color.GRAY);
    }

    @Override
    public void resize(int width, int height) {
        Renderer.get().resize(width, height);
    }

    @Override
    public void render() {
        //Update

        //Draw
        Renderer.get().begin();
        Renderer.get().draw(Assets.get().sprite(0, 1), new Point2(0, 0), DrawDepths.get(Core.DrawDepths.Animated_Texture), Color.WHITE);
        Renderer.get().draw(Assets.get().sprite(0, 1), new Point2(0, Renderer.get().VirtualHeight - 64), DrawDepths.get(Core.DrawDepths.Animated_Texture), Color.WHITE);
        Renderer.get().draw(Assets.get().sprite(0, 1), new Point2(Renderer.get().VirtualWidth - 64, 0), DrawDepths.get(Core.DrawDepths.Animated_Texture), Color.WHITE);
        Renderer.get().draw(Assets.get().sprite(0, 1), new Point2(Renderer.get().VirtualWidth - 64, Renderer.get().VirtualHeight - 64), DrawDepths.get(Core.DrawDepths.Animated_Texture), Color.WHITE);
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
