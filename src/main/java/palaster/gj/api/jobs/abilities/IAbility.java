package palaster.gj.api.jobs.abilities;

import palaster.gj.api.capabilities.IRPG;

import javax.annotation.Nonnull;

public interface IAbility {

    boolean isAvailable(@Nonnull IRPG RPG);
}