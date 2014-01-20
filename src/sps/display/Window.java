package sps.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL10;
import sps.color.Color;
import sps.console.DevConsole;
import sps.core.SpsConfig;
import sps.display.render.RenderStrategy;
import sps.display.render.Renderer;
import sps.display.render.StretchStrategy;

public class Window {
    private static RenderStrategy __defaultStrategy = new StretchStrategy();
    private static Renderer __dynamic;
    private static Renderer __fixed;
    private static Color __bgColor = Color.BLACK;

    public static int Height;
    public static int Width;

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
            if (SpsConfig.get().displayLoggingEnabled) {
                DevConsole.get().add("Virtual resolution: " + width + "W, " + height + "H");
            }
            __dynamic = new Renderer(width, height);
            __dynamic.screenEngine().setStrategy(__defaultStrategy);
            __fixed = new Renderer(width, height);
            __fixed.screenEngine().setStrategy(__defaultStrategy);
            if (SpsConfig.get().displayLoggingEnabled) {
                DevConsole.get().add("Window resolution: " + Gdx.graphics.getWidth() + "W, " + Gdx.graphics.getHeight() + "H");
            }
            Width = width;
            Height = height;
        }
        return fixed ? __fixed : __dynamic;
    }

    public static void setWindowBackground(Color color) {
        __bgColor = color;
    }

    public static void resize(int width, int height, boolean enableFullScreen) {
        if (SpsConfig.get().displayLoggingEnabled) {
            DevConsole.get().add("== Resizing window: " + width + ", " + height + ", " + (enableFullScreen ? "FullScreen" : "Windowed"));
        }
        if (enableFullScreen) {
            Graphics.DisplayMode nativeMode = Gdx.graphics.getDesktopDisplayMode();
            width = nativeMode.width;
            height = nativeMode.height;
            if (SpsConfig.get().displayLoggingEnabled) {
                DevConsole.get().add("Fullscreen enabled. Ignore parameters and resize to native resolution:  " + width + "x" + height);
            }
        }
        Gdx.graphics.setDisplayMode(width, height, enableFullScreen);
        Height = height;
        Width = width;
        get(true).screenEngine().resize(width, height);
        get(false).screenEngine().resize(width, height);
    }

    public static void processDrawCalls() {
        Window.clear();
        get().processScheduledApiCalls();
        get(true).processScheduledApiCalls();
    }
}