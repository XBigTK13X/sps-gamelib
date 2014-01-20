package sps.particles;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import sps.bridge.DrawDepth;

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
