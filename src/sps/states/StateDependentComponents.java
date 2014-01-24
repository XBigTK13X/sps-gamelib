package sps.states;

import sps.entity.Entities;
import sps.particle.simple.ParticleEngine;
import sps.text.TextPool;
import sps.ui.Buttons;
import sps.ui.Tooltips;

public class StateDependentComponents {
    public final ParticleEngine ParticleEngine;
    public final TextPool TextPool;
    public final Tooltips Tooltips;
    public final Buttons Buttons;
    public final Entities LightEntities;

    public StateDependentComponents(Entities entities, ParticleEngine particleEngine, TextPool textPool, Tooltips tooltips, Buttons buttons) {
        LightEntities = entities;
        ParticleEngine = particleEngine;
        TextPool = textPool;
        Tooltips = tooltips;
        Buttons = buttons;
    }
}
