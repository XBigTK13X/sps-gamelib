package com.simplepathstudios.gamelib.particle.simple.behaviors;

import com.simplepathstudios.gamelib.particle.simple.Particle2;
import com.simplepathstudios.gamelib.particle.simple.ParticleBehavior;

public class RotateBehavior extends ParticleBehavior {
    protected static ParticleBehavior __instance;

    public static ParticleBehavior getInstance() {
        if (__instance == null) {
            __instance = new RotateBehavior();
        }
        return __instance;
    }

    @Override
    public int getParticleCount() {
        return 1;
    }

    @Override
    public void update(Particle2 particle) {
        if (particle.Entity != null) {
            particle.Radius = 18;
            particle.setSize(15f, 15f);
            particle.Angle += Math.PI / 30;
            particle.Position.setX(particle.Entity.getPosition().X - particle.Width / 2 + (float) Math.cos(particle.Angle) * particle.Radius);
            particle.Position.setY(particle.Entity.getPosition().Y - particle.Height / 2 + (float) Math.sin(particle.Angle) * particle.Radius);
        }
    }

    @Override
    public void setup(Particle2 particle) {
    }
}
