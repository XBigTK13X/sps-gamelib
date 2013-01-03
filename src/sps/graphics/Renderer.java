package sps.graphics;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import sps.bridge.DrawDepth;
import sps.core.Logger;
import sps.core.Point2;
import sps.core.Settings;

public class Renderer {

    private static Renderer instance;
    private static RenderStrategy defaultStrategy = new StretchStrategy();
    private ApplicationListener refreshInstance;

    public static Renderer get() {
        if (instance == null) {
            int width = Settings.get().spriteWidth * Settings.get().tileMapWidth;
            int height = Settings.get().spriteHeight * Settings.get().tileMapHeight;
            Logger.info("Virtual resolution: " + width + "W, " + height + "H");
            instance = new Renderer(width, height);
            instance.setStrategy(defaultStrategy);
        }
        return instance;
    }

    public static void setVirtualResolution(int width, int height) {
        instance = new Renderer(width, height);
        instance.setStrategy(defaultStrategy);

    }

    // This is the resolution used by the game internally
    public final int VirtualHeight;
    public final int VirtualWidth;
    public final float VirtualAspectRatio;
    private int Height;
    private int Width;

    public SpriteBatch batch;
    public OrthographicCamera camera;
    private RenderStrategy strategy;
    private Color bgColor;

    private Renderer(int width, int height) {
        VirtualWidth = width;
        VirtualHeight = height;
        Height = height;
        Width = width;
        VirtualAspectRatio = (float) width / (float) height;
        batch = new SpriteBatch();
        bgColor = Color.WHITE;
        strategy = new StretchStrategy();
        resize(width, height);
    }

    public void setRefreshInstance(ApplicationListener app) {
        refreshInstance = app;
    }

    public void setWindowsBackground(Color bgColor) {
        this.bgColor = bgColor;
    }

    public void setStrategy(RenderStrategy strategy) {

        this.strategy = strategy;
        camera = strategy.createCamera();
        if (refreshInstance != null) {
            refreshInstance.resize(getWidth(), getHeight());
        }
        else {
            Logger.info("If the app is registered with Renderer.get().setRefreshInstance(this); in the create method, then the screen will update without a manual resizing.");
        }
    }

    public void toggleFullScreen() {
        Gdx.graphics.setDisplayMode(Gdx.graphics.getDesktopDisplayMode().width, Gdx.graphics.getDesktopDisplayMode().height, !Gdx.graphics.isFullscreen());
        resize(Gdx.graphics.getDesktopDisplayMode().width, Gdx.graphics.getDesktopDisplayMode().height);
    }

    public Point2 center() {
        return new Point2(VirtualWidth / 2, VirtualHeight / 2);
    }

    public void begin() {
        Gdx.gl.glClearColor(bgColor.r, bgColor.g, bgColor.b, bgColor.a);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        camera.update();
        strategy.begin(camera, batch);
        batch.begin();
    }

    public void end() {
        batch.end();
    }

    public void resize(int width, int height) {
        Height = height;
        Width = width;
        strategy.resize(width, height);
    }

    public int getHeight() {
        return Height;
    }

    public int getWidth() {
        return Width;
    }

    // Sprite rendering
    public void draw(Sprite sprite, Point2 position, DrawDepth depth, Color color) {
        render(sprite, position, depth, color, Settings.get().spriteWidth, Settings.get().spriteHeight);
    }

    public void draw(Sprite sprite, Point2 position, DrawDepth depth, Color color, boolean flipX, boolean flipY) {
        render(sprite, position, depth, color, flipX ? -1 : 1 * Settings.get().spriteWidth, flipY ? -1 : 1 * Settings.get().spriteHeight);
    }

    public void draw(Sprite sprite, Point2 position, DrawDepth depth, Color color, float scaleX, float scaleY) {
        render(sprite, position, depth, color, scaleX, scaleY);
    }

    private void render(Sprite sprite, Point2 position, DrawDepth depth, Color color, float scaleX, float scaleY) {
        sprite.setColor(color);
        sprite.setSize(scaleX, scaleY);
        sprite.setPosition(position.X, position.Y);
        sprite.draw(batch);
    }

    // String rendering
    public void drawString(String content, Point2 location, Color filter, float scale, DrawDepth depth) {
        renderString(content, location, filter, scale, depth);
    }

    private void renderString(String content, Point2 location, Color filter, float scale, DrawDepth depth) {
        Assets.get().font().setScale(scale);
        Assets.get().font().setColor(filter);
        Assets.get().font().draw(batch, content, location.PosX, location.PosY);
    }
}
