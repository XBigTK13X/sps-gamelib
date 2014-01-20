package sps.draw;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import sps.color.Color;
import sps.core.Loader;

public class SpriteMaker {
    private static Color co;

    public static Sprite fromColors(Color[][] colors) {
        Pixmap textureBase = new Pixmap(colors.length, colors[0].length, Pixmap.Format.RGBA8888);
        for (int ii = 0; ii < colors.length; ii++) {
            for (int jj = 0; jj < colors[0].length; jj++) {
                co = colors[ii][jj];
                if (co != null) {
                    textureBase.setColor(co.getGdxColor());
                    textureBase.drawPixel(ii, colors[0].length - jj);
                }
            }
        }
        return new Sprite(new Texture(textureBase));
    }

    public static Sprite pixel(Color color) {
        Pixmap textureBase = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        textureBase.setColor(color.getGdxColor());
        textureBase.drawPixel(0, 0);
        return new Sprite(new Texture(textureBase));
    }

    public static Sprite fromGraphic(String graphicsPath) {
        return new Sprite(new Texture(new FileHandle(Loader.get().graphics(graphicsPath))));
    }
}
