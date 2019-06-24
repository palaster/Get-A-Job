package palaster.gj.jobs.abilities;

import palaster.gj.api.capabilities.IRPG;
import palaster.gj.api.jobs.abilities.IAbility;
import palaster.gj.jobs.JobBotanist;

import javax.annotation.Nonnull;

public class BountifulHarvestAbility implements IAbility {

    @Override
    public boolean isAvailable(@Nonnull IRPG RPG) { return RPG.getJob() != null && RPG.getJob() instanceof JobBotanist; }
}