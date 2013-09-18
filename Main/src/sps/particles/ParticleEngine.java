package sps.particles;

import com.badlogic.gdx.graphics.Color;
import sps.core.Point2;
import sps.entities.Entity;

public class ParticleEngine {

    private static ParticleEngine __instance;

    public static ParticleEngine get() {
        if (__instance == null) {
            __instance = new ParticleEngine();
        }
        return __instance;
    }

    public static void set(ParticleEngine particleEngine) {
        __instance = particleEngine;
    }

    public static void reset() {
        __instance = new ParticleEngine();
    }

    private ParticleEngine() {
        for (int ii = 0; ii < __particles.length; ii++) {
            __particles[ii] = new Particle2();

        }
        for (int ii = 0; ii < __emitters.length; ii++) {
            __emitters[ii] = new Emitter();
            __emitters[ii].IsActive = false;
        }
    }

    private int __emitterIndex;
    private final Particle2[] __particles = new Particle2[1000];
    private final Emitter[] __emitters = new Emitter[100];
    private int __particleIndex;

    public void emit(ParticleBehavior behavior, Point2 position, Color baseColor) {
        while (__emitters[__emitterIndex].IsActive) {
            __emitterIndex = (__emitterIndex + 1) % __emitters.length;
        }

        __emitters[__emitterIndex].reset(behavior, position, baseColor);
    }

    public Emitter emit(ParticleBehavior behavior, Entity entity, Color baseColor) {
        while (__emitters[__emitterIndex].IsActive) {
            __emitterIndex = (__emitterIndex + 1) % __emitters.length;
        }

        __emitters[__emitterIndex].reset(behavior, entity, baseColor);
        return __emitters[__emitterIndex];
    }

    public void update() {
        for (Emitter __emitter : __emitters) {
            __emitter.update();
        }
    }

    public void draw() {
        for (Emitter __emitter : __emitters) {
            __emitter.draw();
        }
    }

    public Particle2 createParticle(ParticleBehavior behavior, Point2 position, Color baseColor) {
        setIndexToInactiveParticle();
        __particles[__particleIndex].reset(behavior, position, baseColor);
        return __particles[__particleIndex];
    }

    public Particle2 createParticle(ParticleBehavior behavior, Entity entity, Color baseColor) {
        setIndexToInactiveParticle();
        __particles[__particleIndex].reset(behavior, entity, baseColor);
        return __particles[__particleIndex];
    }

    private void setIndexToInactiveParticle() {
        while (__particles[__particleIndex].IsActive) {
            __particleIndex = (__particleIndex + 1) % __particles.length;
        }
    }
}
