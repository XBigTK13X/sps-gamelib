package sps.display;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import sps.core.Logger;
import sps.core.SpsConfig;
import sps.display.render.RenderStrategy;
import sps.display.render.Renderer;
import sps.display.render.StretchStrategy;

public class Window {
    private static RenderStrategy __defaultStrategy = new StretchStrategy();
    private static Renderer __dynamic;
    private static Renderer __fixed;
    private static boolean __tipHasBeenDisplayed = false;
    private static ApplicationListener __app;
    private static Color __bgColor = Color.BLACK;

    private Window() {

    }

    public static void clear() {
        Gdx.gl.glClearColor(Window.__bgColor.r, Window.__bgColor.g, Window.__bgColor.b, Window.__bgColor.a);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    }

    public static Renderer get() {
        return get(false);
    }

    public static Renderer get(boolean fixed) {
        if (__dynamic == null || __fixed == null) {
            int width = SpsConfig.get().virtualWidth;
            int height = SpsConfig.get().virtualHeight;
            Logger.info("Virtual resolution: " + width + "W, " + height + "H");
            __dynamic = new Renderer(width, height);
            __dynamic.setStrategy(__defaultStrategy);
            __fixed = new Renderer(width, height);
            __fixed.setStrategy(__defaultStrategy);
            __fixed.setListening(true);
            Logger.info("Window resolution: " + Gdx.graphics.getWidth() + "W, " + Gdx.graphics.getHeight() + "H");
        }
        return fixed ? __fixed : __dynamic;
    }

    public static void setRefreshInstance(ApplicationListener app) {
        __app = app;
    }

    public static void setWindowBackground(Color color) {
        __bgColor = color;
    }

    public static void resize(int width, int height, boolean changeWindow) {
        if (__app == null) {
            if (!__tipHasBeenDisplayed) {
                Logger.info("If the app is registered with Renderer.get().setRefreshInstance(this); in the create method, then the screen will update without a manual resizing.");
                __tipHasBeenDisplayed = true;
            }
        }
        else {
            if (changeWindow) {
                __app.resize(width, height);
                Gdx.graphics.setDisplayMode(width, height, Gdx.graphics.isFullscreen());
            }
        }
        get(true).resize(width, height);
        get(false).resize(width, height);
    }

    public static void toggleFullScreen(boolean enableFullScreen, int width, int height) {
        int resolutionX = 0;
        int resolutionY = 0;
        boolean isFullScreen = Gdx.graphics.isFullscreen();

        boolean apply = false;

        if (isFullScreen && !enableFullScreen) {
            resolutionX = width;
            resolutionY = height;
            apply = true;
        }
        else if (!isFullScreen && enableFullScreen) {
            resolutionX = Gdx.graphics.getDesktopDisplayMode().width;
            resolutionY = Gdx.graphics.getDesktopDisplayMode().height;
            apply = true;
        }


        if (apply) {
            Gdx.graphics.setDisplayMode(resolutionX, resolutionY, enableFullScreen);
            resize(resolutionX, resolutionY, false);
        }
    }
}