package sample;

import sps.draw.BackgroundCache;
import sps.preload.DelayedPreloader;
import sps.preload.PreloadChain;
import sps.preload.PreloadChainLink;

public class SampleGamePreload {
    public static DelayedPreloader getDelayedPreloader() {
        DelayedPreloader preloader = new DelayedPreloader() {
            @Override
            public PreloadChain createChain() {
                return SampleGamePreload.getChain();
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

        chain.add(new PreloadChainLink("Generating background sprites.", 3) {
            @Override
            public void process() {
                BackgroundCache.cacheScreenSize();
            }
        });

        chain.add(new PreloadChainLink("Doing other important things", 30) {
            @Override
            public void process() {
                int i = 0;
            }
        });

        return chain;
    }
}

