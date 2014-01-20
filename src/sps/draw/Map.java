package sps.draw;

import com.badlogic.gdx.graphics.g2d.Sprite;
import sps.bridge.DrawDepths;
import sps.color.Color;
import sps.color.RGBA;
import sps.core.Point2;
import sps.core.RNG;
import sps.display.Window;

import java.util.LinkedList;
import java.util.List;

public class Map {
    private static final int HABITABLE = 1;
    private static final int NOT_HABITABLE = 0;
    private static final int TAKEN = 2;

    public static final int NO_SEED = 0;
    public static final int C = 255;
    private static final int __frameSizePixels = 6;

    private Sprite _sprite;
    private Sprite _bg;
    private int[][] _habitableZones;
    private Point2 _position;
    private Color[][] _spriteBase;
    private Color[][] _bgBase;

    private List<Point2> _habitableLocations;

    private static final int __habitBuffer = 25;

    private int _seed;

    private int _habitableMargin;
    private boolean _smoothRegions;

    public Map(int width, int height, Point2 position, int mapSeed, boolean smoothRegions, int pixelMargin) {
        _smoothRegions = smoothRegions;
        _habitableMargin = pixelMargin;
        if (mapSeed == NO_SEED) {
            mapSeed = RNG.next(0, Integer.MAX_VALUE);
        }
        RNG.seed(mapSeed);
        _habitableLocations = new LinkedList<>();
        _position = position;
        _spriteBase = ProcTextures.perlin(width, height, new RGBA(0, 255, 255).toColor(), new RGBA(255, 255, 255).toColor(), 9, true);
        _habitableZones = new int[width][height];

        if (_smoothRegions) {
            TextureManipulation.blurNaive(_spriteBase, 2);
        }

        for (int ii = 0; ii < width; ii++) {
            for (int jj = 0; jj < height; jj++) {
                int elevation = (int) (255 * _spriteBase[ii][jj].r);
                _spriteBase[ii][jj] = Biome.getColor(elevation);
                if (ii > __habitBuffer && ii < width - __habitBuffer && jj > __habitBuffer && jj < height - __habitBuffer) {
                    _habitableZones[ii][jj] = Biome.fromElevation(elevation).Habitable ? HABITABLE : NOT_HABITABLE;
                    if (_habitableZones[ii][jj] == HABITABLE) {
                        _habitableLocations.add(new Point2(ii, jj));
                    }
                }
                else {
                    _habitableZones[ii][jj] = NOT_HABITABLE;
                }
            }
        }

        _bgBase = ProcTextures.monotone(width + __frameSizePixels, height + __frameSizePixels, Color.WHITE);

        regenerateTextures();

        RNG.naturalReseed();
        _seed = mapSeed;
    }

    public void regenerateTextures() {
        _bg = SpriteMaker.fromColors(_bgBase);
        _sprite = SpriteMaker.fromColors(_spriteBase);

        _bg.setPosition(_position.X - __frameSizePixels / 2, _position.Y - __frameSizePixels / 2);
        _sprite.setPosition(_position.X, _position.Y);
    }

    public void resetSpace(Point2 location) {
        int x = (int) (location.X);
        int y = (int) (location.Y);
        _habitableZones[x][y] = HABITABLE;
        _habitableLocations.add(new Point2(x, y));
    }

    public Point2 getOpenSpace() {
        int tries = 100;
        while (tries-- > 0) {
            Point2 location = RNG.pick(_habitableLocations);
            if (_habitableZones[(int) location.X][(int) location.Y] == 1) {
                boolean success = true;
                for (int ii = -_habitableMargin; ii < _habitableMargin; ii++) {
                    for (int jj = -_habitableMargin; jj < _habitableMargin; jj++) {
                        int x = (int) (ii + location.X);
                        int y = (int) (jj + location.Y);
                        if (x >= 0 && x < _habitableZones.length && y >= 0 && y < _habitableZones[0].length) {
                            if (_habitableZones[x][y] != HABITABLE) {
                                success = false;
                            }
                        }
                    }
                }
                if (success) {
                    _habitableZones[(int) location.X][(int) location.Y] = TAKEN;
                    _habitableLocations.remove(location);
                    return location;
                }
            }
        }
        return new Point2(RNG.next(0, _habitableZones.length), RNG.next(0, _habitableZones[0].length));
    }

    public Point2 getPosition() {
        return _position;
    }

    public void draw() {
        Window.get().schedule(_bg, DrawDepths.get("PopulationMapBackground"));
        Window.get().schedule(_sprite, DrawDepths.get("PopulationMap"));
    }

    public int getSeed() {
        return _seed;
    }
}
