package com.simplepathstudios.gamelib.movie;

import com.badlogic.gdx.audio.Music;
import com.simplepathstudios.gamelib.audio.MusicPlayer;
import com.simplepathstudios.gamelib.entity.Entity;

public class Movie extends Entity {
    private Video _video;
    private Music _music;
    private String _musicId;

    public Movie(String musicId) {
        super();
        _music = MusicPlayer.get().music(musicId);
        _musicId = musicId;
        _video = new Video();
    }

    public Video getVideo() {
        return _video;
    }

    public Music getMusic() {
        return _music;
    }

    public String getMusicId() {
        return _musicId;
    }

    @Override
    public void update() {
        _video.play(_music.getPosition());
        if (!_music.isPlaying()) {
            setActive(false);
        }
    }

    @Override
    public void draw() {
    }
}
