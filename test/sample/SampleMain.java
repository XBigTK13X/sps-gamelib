package sample;

import sps.main.DesktopTarget;

public class SampleMain {
    public static void main(String[] args) {
        DesktopTarget.start("Sample Game", SampleGamePreload.getDelayedPreloader(), new SampleGameStateResolver(), args);
    }
}
