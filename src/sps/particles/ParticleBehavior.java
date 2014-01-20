package sps.particles;

public abstract class ParticleBehavior {
    public int getParticleCount() {
        return 30;
    }

    public abstract void update(Particle2 particle);

    public abstract void setup(Particle2 particle);
}
