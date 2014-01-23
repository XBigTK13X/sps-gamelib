package sps.states;

import sps.entities.EntityManager;
import sps.entities.LightEntities;
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
    public final LightEntities LightEntities;

    public StateDependentComponents(LightEntities lightEntities, EntityManager entityManager, ParticleEngine particleEngine, TextPool textPool, Tooltips tooltips, Buttons buttons) {
        LightEntities = lightEntities;
        EntityManager = entityManager;
        ParticleEngine = particleEngine;
        TextPool = textPool;
        Tooltips = tooltips;
        Buttons = buttons;
    }
}
