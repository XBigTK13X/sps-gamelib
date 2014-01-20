package sps.states;

import sps.entities.EntityManager;
import sps.particles.ParticleEngine;
import sps.text.TextPool;
import sps.ui.Buttons;
import sps.ui.Tooltips;

public class StateDependentComponents {
    public final EntityManager EntityManager;
    public final ParticleEngine ParticleEngine;
    public final TextPool TextPool;
    public final Tooltips Tooltips;
    public final Buttons Buttons;

    public StateDependentComponents(EntityManager entityManager, ParticleEngine particleEngine, TextPool textPool, Tooltips tooltips, Buttons buttons) {
        EntityManager = entityManager;
        ParticleEngine = particleEngine;
        TextPool = textPool;
        Tooltips = tooltips;
        Buttons = buttons;
    }
}
