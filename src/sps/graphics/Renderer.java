package sps.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import sps.bridge.DrawDepth;
import sps.core.Logger;
import sps.core.Point2;
import sps.core.Settings;

public class Renderer {

    private static Renderer instance;

    public static Renderer get() {
        if (instance == null) {
            int width = Settings.get().spriteWidth * Settings.get().tileMapWidth;
            int height = Settings.get().spriteHeight * Settings.get().tileMapHeight;
            Logger.info("Virtual resolution: " + width + "W, " + height + "H");
            instance = new Renderer(width, height);
        }
        return instance;
    }

    public static void setVirtualResolution(int width, int height) {
        instance = new Renderer(width, height);
    }

    // This is the resolution used by the game internally
    public final int VirtualHeight;
    public final int VirtualWidth;
    public final float VirtualAspectRatio;

    public final SpriteBatch batch;
    public OrthographicCamera camera;
    private boolean stretch = false;
    private Rectangle viewport;
    private Color bgColor;

    private Renderer(int width, int height) {
        VirtualWidth = width;
        VirtualHeight = height;
        VirtualAspectRatio = (float) width / (float) height;
        camera = new OrthographicCamera(width, height);
        batch = new SpriteBatch();
        bgColor = Color.WHITE;
        resize(width, height);
    }

    public void setWindowsBackground(Color bgColor) {
        this.bgColor = bgColor;
    }

    public void allowStretching(boolean stretch) {
        this.stretch = stretch;
        if (stretch) {
            camera = new OrthographicCamera();
            camera.setToOrtho(false, VirtualWidth, VirtualHeight);
        }
        else {
            camera = new OrthographicCamera(VirtualWidth, VirtualHeight);
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
        //camera.apply(Gdx.gl10.);
        if (stretch) {
            batch.setProjectionMatrix(camera.combined);
        }
        else {
            Logger.info(viewport.x + "x, " + viewport.y + "y : " + viewport.width + "w, " + viewport.height + " h");
            //camera.setToOrtho(false,viewport.width,viewport.height);
            Gdx.gl.glViewport((int) viewport.x, (int) viewport.y, (int) viewport.width, (int) viewport.height);
        }
        batch.begin();
    }

    public void end() {
        batch.end();
    }

    public void resize(int width, int height) {
        if (!stretch) {
            Logger.info("Scaling to: " + width + "W, " + height + "H");
            float aspectRatio = (float) width / (float) height;
            float scale = 1f;
            Vector2 crop = new Vector2(0f, 0f);

            if (aspectRatio > VirtualAspectRatio) {
                scale = (float) height / (float) VirtualHeight;
                crop.x = (width - VirtualWidth * scale) / 2f;
            }
            else if (aspectRatio < VirtualAspectRatio) {
                scale = (float) width / (float) VirtualWidth;
                crop.y = (height - VirtualHeight * scale) / 2f;
            }
            else {
                scale = (float) width / (float) VirtualWidth;
            }

            float w = (float) VirtualWidth * scale;
            float h = (float) VirtualHeight * scale;
            viewport = new Rectangle(crop.x, crop.y, w, h);
        }
    }

    // Sprite rendering
    public void draw(Sprite sprite, Point2 position, DrawDepth depth, Color color) {
        render(sprite, position, depth, color, Settings.get().spriteWidth, Settings.get().spriteHeight);
    }

    public void draw(Sprite sprite, Point2 position, DrawDepth depth, Color color, float scaleX, float scaleY) {
        render(sprite, position, depth, color, scaleX, scaleY);
    }

    private void render(Sprite sprite, Point2 position, DrawDepth depth, Color color, float scaleX, float scaleY) {
        Logger.info(position.toString());
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
