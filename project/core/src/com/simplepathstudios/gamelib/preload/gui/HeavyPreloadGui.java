package com.simplepathstudios.gamelib.preload.gui;

import com.simplepathstudios.gamelib.color.Color;
import com.simplepathstudios.gamelib.color.Colors;
import com.simplepathstudios.gamelib.display.Screen;
import com.simplepathstudios.gamelib.display.Window;
import com.simplepathstudios.gamelib.states.Systems;
import com.simplepathstudios.gamelib.text.Text;
import com.simplepathstudios.gamelib.text.TextPool;
import com.simplepathstudios.gamelib.ui.Meter;
import com.simplepathstudios.gamelib.ui.MultiText;

import java.text.NumberFormat;

public class HeavyPreloadGui implements PreloadGui {

    private MultiText _loadingMessage;
    private Text _percentDisplay;
    private Meter _loadingMeter;


    private static final NumberFormat _df = NumberFormat.getPercentInstance();

    private String getProgress(int percent) {
        return _df.format((float) percent / 100) + " complete";
    }

    @Override
    public void init() {
        _percentDisplay = Systems.get(TextPool.class).write("", Screen.pos(40, 20));
        _loadingMessage = new MultiText(Screen.pos(10, 50), 6, Color.GRAY.newAlpha(.5f), (int) Screen.width(80), (int) Screen.height(20));
        _loadingMeter = new Meter(90, 9, Colors.randomPleasant(), Screen.pos(5, 30), false);
    }

    @Override
    public void update(int percent) {
        _loadingMeter.setPercent(percent);
        _percentDisplay.setMessage(getProgress(percent));
    }

    @Override
    public void update(String message) {
        _loadingMessage.add(message);
    }

    @Override
    public void draw() {
        _loadingMeter.draw();
        _loadingMessage.draw();
    }
}
