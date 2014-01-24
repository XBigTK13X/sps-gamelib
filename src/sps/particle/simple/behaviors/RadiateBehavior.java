package sps.particle.simple.behaviors;

import sps.particle.simple.Particle2;
import sps.particle.simple.ParticleBehavior;

public class RadiateBehavior extends ParticleBehavior {
    protected static ParticleBehavior __instance;

    public static ParticleBehavior getInstance() {
        if (__instance == null) {
            __instance = new RadiateBehavior();
        }
        return __instance;
    }

    @Override
    public void update(Particle2 particle) {
        particle.Position.setX(particle.Position.X + (float) Math.cos(particle.Angle) * particle.MoveSpeed);
        particle.Position.setY(particle.Position.Y + (float) Math.sin(particle.Angle) * particle.MoveSpeed);
    }

    @Override
    public void setup(Particle2 particle) {
    }
}
