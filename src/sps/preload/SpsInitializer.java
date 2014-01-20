package sps.preload;

import sps.audio.MusicPlayer;
import sps.audio.RandomSoundPlayer;
import sps.audio.SoundPlayer;
import sps.bridge.SpriteTypes;
import sps.bridge.Sps;
import sps.color.Color;
import sps.console.DevConsole;
import sps.core.Loader;
import sps.core.RNG;
import sps.display.Assets;
import sps.display.SpriteSheetManager;
import sps.display.Window;
import sps.display.render.FrameStrategy;
import sps.io.*;
import sps.main.DesktopTarget;
import sps.ui.MultiText;
import sps.util.DevConfig;
import sps.util.GameConfig;
import sps.util.GameMonitor;

import java.io.File;

public class SpsInitializer {
    public static PreloadChain getChain() {
        PreloadChain preload = new PreloadChain(false) {
            @Override
            public void finish() {

            }
        };

        final String error = "Unfortunately, an error caused the game to freeze last time it was played.";
        preload.add(new PreloadChainLink("Prevent game from freezing the system.") {
            @Override
            public void process() {
                GameMonitor.monitor(UserFiles.crash(), error, GameConfig.ThreadMaxStalledMilliseconds);
            }
        });

        preload.add(new PreloadChainLink("Read bridge config") {
            @Override
            public void process() {
                RNG.naturalReseed();
                Sps.setup(DesktopTarget.get(), true);
            }
        });

        preload.add(new PreloadChainLink("Read input bindings") {
            @Override
            public void process() {
                InputBindings.reload(UserFiles.input());
            }
        });

        preload.add(new PreloadChainLink("Read default fonts") {
            @Override
            public void process() {
                Assets.get().fontPack().setDefault("Aller/Aller_Rg.ttf", 50);
                Assets.get().fontPack().cacheFont("keys", "Keycaps Regular.ttf", 30);
                Assets.get().fontPack().cacheFont("UIButton", "neris/Neris-SemiBold.otf", 70);
                Assets.get().fontPack().cacheFont("Console", "ubuntu/UbuntuMono-R.ttf", 24);
            }
        });

        preload.add(new PreloadChainLink("Read sound files") {
            @Override
            public void process() {
                for (File file : Loader.get().sound("").listFiles()) {
                    if (!file.getName().contains(".placeholder")) {
                        if (file.isDirectory()) {
                            RandomSoundPlayer.setup(file.getName());
                        }
                        else {
                            SoundPlayer.get().add(file.getName(), file.getName());
                        }
                    }
                }
            }
        });

        preload.add(new PreloadChainLink("Read music files") {
            @Override
            public void process() {
                for (File file : Loader.get().music("").listFiles()) {
                    if (!file.getName().contains(".placeholder")) {
                        MusicPlayer.get().add(file.getName(), file.getName());
                    }
                }
            }
        });

        preload.add(new PreloadChainLink("Prepare render management") {
            @Override
            public void process() {
                Window.setWindowBackground(Color.BLACK);
                Window.get(false).screenEngine().setStrategy(new FrameStrategy());
                Window.get(true).screenEngine().setStrategy(new FrameStrategy());
                Input.get().setup(new DefaultStateProvider());
                SpriteSheetManager.setup(SpriteTypes.getDefs());
            }
        });

        preload.add(new PreloadChainLink("Check for automated bot strategy") {
            @Override
            public void process() {
                if (DevConfig.BotEnabled) {
                    Options.get().GraphicsLowQuality = true;
                    Options.get().FullScreen = false;
                    Options.get().WindowResolutionY = 100;
                    Options.get().WindowResolutionX = 100;
                    Options.get().apply();
                    Options.get().save();
                }
            }
        });

        preload.add(new PreloadChainLink("Setup multitext panels") {
            @Override
            public void process() {
                MultiText.setDefaultFont("Console", 24);
            }
        });

        preload.add(new PreloadChainLink("Setup dev console") {
            @Override
            public void process() {
                DevConsole.get().setFont("Console", 24);
            }
        });

        preload.add(new PreloadChainLink("Loading game options") {
            @Override
            public void process() {
                Options.load();
                Options.get().apply();
            }
        });
        return preload;
    }
}
