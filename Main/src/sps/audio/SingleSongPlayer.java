package sps.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import sps.core.Loader;

public class SingleSongPlayer extends MusicPlayer {
    public SingleSongPlayer(String fileName, boolean loop) {
        FileHandle mainThemeFh = Loader.get().music(fileName);
        _song = Gdx.audio.newMusic(mainThemeFh);
        _song.setLooping(loop);
    }

    public SingleSongPlayer(String fileName) {
        this(fileName, true);
    }

    @Override
    public void start() {
        _song.play();
    }


    @Override
    public void stop() {
        _song.stop();
    }
}
