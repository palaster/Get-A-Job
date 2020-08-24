package palaster.gj.jobs.abilities;

import javax.annotation.Nonnull;

import palaster.gj.api.capabilities.IRPG;
import palaster.gj.api.jobs.abilities.IAbility;
import palaster.gj.jobs.JobBloodSorcerer;

public class DarkStalkerAbility implements IAbility {

	@Override
	public boolean isAvailable(@Nonnull IRPG RPG) { return RPG.getJob() != null && RPG.getJob() instanceof JobBloodSorcerer; }
}
