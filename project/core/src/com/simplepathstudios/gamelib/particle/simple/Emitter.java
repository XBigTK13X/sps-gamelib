package com.simplepathstudios.gamelib.particle.simple;

import com.simplepathstudios.gamelib.color.Color;
import com.simplepathstudios.gamelib.display.Point2;
import com.simplepathstudios.gamelib.entity.Entity;
import com.simplepathstudios.gamelib.states.Systems;

public class Emitter extends PEComponent {
    private Particle2[] _particles = new Particle2[10];
    private int _index = 0;
    ParticleBehavior _behavior;

    public Emitter() {
    }

    public void update() {
        if (IsActive) {
            IsActive = false;
            for (int ii = 0; ii < _index; ii++) {
                if (_particles[ii].IsActive) {
                    IsActive = true;
                    _particles[ii].update();
                }
            }
        }
    }

    public void draw() {
        if (IsActive) {
            for (int ii = 0; ii < _index; ii++) {
                _particles[ii].draw();
            }
        }
    }

    public void reset(ParticleBehavior behavior, Point2 position, Color baseColor) {
        IsActive = true;
        _behavior = behavior;
        _index = 0;
        if (behavior.getParticleCount() > _particles.length) {
            _particles = new Particle2[behavior.getParticleCount()];
        }
        while (_index < behavior.getParticleCount()) {
            _particles[_index] = Systems.get(ParticleEngine.class).createParticle(behavior, position, baseColor);
            _index++;
        }
    }

    public void reset(ParticleBehavior behavior, Entity entity, Color baseColor) {
        IsActive = true;
        _behavior = behavior;
        _index = 0;
        if (behavior.getParticleCount() > _particles.length) {
            _particles = new Particle2[behavior.getParticleCount()];
        }
        while (_index < behavior.getParticleCount()) {
            _particles[_index] = Systems.get(ParticleEngine.class).createParticle(behavior, entity, baseColor);
            _index++;
        }
    }

    public void clear() {
        for (Particle2 p : _particles) {
            if (p != null) {
                p.clear();
            }
        }
    }
}
