package palaster.gj.api.jobs.abilities;

import javax.annotation.Nonnull;

import palaster.gj.api.capabilities.rpg.IRPG;

public interface IAbility {

    boolean isAvailable(@Nonnull IRPG RPG);
}