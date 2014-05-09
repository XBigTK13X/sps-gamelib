package com.simplepathstudios.gamelib.desktop.bootstrap;

import com.simplepathstudios.gamelib.audio.MusicPlayer;
import com.simplepathstudios.gamelib.audio.RandomSoundPlayer;
import com.simplepathstudios.gamelib.audio.SoundPlayer;
import com.simplepathstudios.gamelib.bridge.SpriteTypes;
import com.simplepathstudios.gamelib.bridge.Sps;
import com.simplepathstudios.gamelib.color.Color;
import com.simplepathstudios.gamelib.console.DevConsole;
import com.simplepathstudios.gamelib.core.Loader;
import com.simplepathstudios.gamelib.core.RNG;
import com.simplepathstudios.gamelib.data.DevConfig;
import com.simplepathstudios.gamelib.data.GameConfig;
import com.simplepathstudios.gamelib.data.Options;
import com.simplepathstudios.gamelib.data.UserFiles;
import com.simplepathstudios.gamelib.display.Assets;
import com.simplepathstudios.gamelib.display.SpriteSheetManager;
import com.simplepathstudios.gamelib.display.Window;
import com.simplepathstudios.gamelib.display.render.FrameStrategy;
import com.simplepathstudios.gamelib.entity.Entities;
import com.simplepathstudios.gamelib.input.Input;
import com.simplepathstudios.gamelib.input.InputBindings;
import com.simplepathstudios.gamelib.input.Players;
import com.simplepathstudios.gamelib.input.provider.DefaultStateProvider;
import com.simplepathstudios.gamelib.particle.simple.ParticleEngine;
import com.simplepathstudios.gamelib.preload.PreloadChain;
import com.simplepathstudios.gamelib.preload.PreloadChainLink;
import com.simplepathstudios.gamelib.states.Systems;
import com.simplepathstudios.gamelib.task.GameTasks;
import com.simplepathstudios.gamelib.text.TextPool;
import com.simplepathstudios.gamelib.thread.GameMonitor;
import com.simplepathstudios.gamelib.ui.Buttons;
import com.simplepathstudios.gamelib.ui.MultiText;
import com.simplepathstudios.gamelib.ui.Tooltips;

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
                Players.init();
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

        preload.add(new PreloadChainLink("Register state instanced game systems") {
            @Override
            public void process() {
                Systems.register(GameTasks.class);
                Systems.register(Entities.class);
                Systems.register(ParticleEngine.class);
                Systems.register(TextPool.class);
                Systems.register(Tooltips.class);
                Systems.register(Buttons.class);
                Systems.newInstances();
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
