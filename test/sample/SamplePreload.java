package sample;

import sps.data.GameState;
import sps.data.GameStateHandler;
import sps.data.Persistence;
import sps.pregame.ConfigurableCommands;
import sps.preload.DelayedPreloader;
import sps.preload.PreloadChain;
import sps.preload.PreloadChainLink;

public class SamplePreload {
    public static DelayedPreloader getDelayedPreloader() {
        DelayedPreloader preloader = new DelayedPreloader() {
            @Override
            public PreloadChain createChain() {
                return SamplePreload.getChain();
            }
        };
        return preloader;
    }

    public static PreloadChain getChain() {
        PreloadChain chain = new PreloadChain() {
            @Override
            public void finish() {

            }
        };

        chain.add(new PreloadChainLink() {
            @Override
            public void process() {
                ConfigurableCommands.get().add("Confirm", "Confirm");
                ConfigurableCommands.get().add("Exit", "Exit Game");
                ConfigurableCommands.get().add("Pause", "Pause Game");
                ConfigurableCommands.get().add("Help", "Show Tutorial");
                ConfigurableCommands.get().add("AdvanceTutorial", "Advance Tutorial");
            }
        });

        return chain;
    }
}

