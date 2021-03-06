package com.simplepathstudios.gamelib.movie;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.simplepathstudios.gamelib.bridge.DrawDepths;
import com.simplepathstudios.gamelib.display.Screen;
import com.simplepathstudios.gamelib.display.Window;
import com.simplepathstudios.gamelib.states.Systems;
import com.simplepathstudios.gamelib.text.Text;
import com.simplepathstudios.gamelib.text.TextPool;

import java.util.ArrayList;
import java.util.List;

public class Video {
    private class Strip {
        private Sprite _image;
        private float _seconds;
        private String _message;

        public Strip(float seconds, String message, Sprite image) {
            _message = message;
            _seconds = seconds;
            _image = image;
        }

        public Strip(float seconds, String message) {
            this(seconds, message, null);
        }

        public String getMessage() {
            return _message;
        }

        public boolean onScreen(float timeSecs) {
            return timeSecs >= _seconds;
        }
    }

    private List<Strip> _strips;
    private Text _subtitle;

    public Video() {
        _strips = new ArrayList<>();
        _subtitle = Systems.get(TextPool.class).write("", Screen.pos(5, 50));
        _subtitle.setDepth(DrawDepths.get("MovieSubtitle"));
    }

    public void addStrip(float timeSeconds, String message) {
        _strips.add(new Strip(timeSeconds, message));
    }

    private boolean _firstFrame = true;
    private Sprite _current;

    public void play(float timeSecs) {
        for (int ii = 0; ii < _strips.size(); ii++) {
            if (ii == 0 && _firstFrame || _strips.get(ii).onScreen(timeSecs)) {
                _current = _strips.get(ii)._image;
                _subtitle.setMessage(_strips.get(ii).getMessage());
                _strips.remove(ii);
                ii--;
                _firstFrame = false;
            }
        }
        if (_current != null) {
            Window.get().schedule(_current, DrawDepths.get("MovieImage"));
        }
    }
}
