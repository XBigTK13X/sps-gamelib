package com.simplepathstudios.gamelib.particle;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.simplepathstudios.gamelib.bridge.DrawDepth;

public class ParticleLease {
    public final ParticleEffectPool.PooledEffect Effect;
    public final String Label;
    public final DrawDepth Depth;

    public ParticleLease(ParticleEffectPool.PooledEffect effect, String label, DrawDepth depth) {
        Label = label;
        Effect = effect;
        Depth = depth;
    }
}
