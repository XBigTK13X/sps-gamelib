package com.simplepathstudios.gamelib.desktop.sample;

import com.simplepathstudios.gamelib.desktop.bootstrap.DesktopTarget;

public class SampleMain {
    public static void main(String[] args) {
        DesktopTarget.start("Sample Game", SamplePreload.getDelayedPreloader(), new SampleResolver(), args);
    }
}
