package palaster.gj.jobs.abilities;

import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.jobs.abilities.IAbility;
import palaster.gj.jobs.JobCleric;

import javax.annotation.Nonnull;

public class DivineSmackdownAbility implements IAbility {

    @Override
    public boolean isAvailable(@Nonnull IRPG RPG) { return RPG.getJob() != null && RPG.getJob() instanceof JobCleric; }
}