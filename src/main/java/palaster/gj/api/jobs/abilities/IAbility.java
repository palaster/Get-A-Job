package palaster.gj.api.jobs.abilities;

import javax.annotation.Nonnull;

import palaster.gj.api.capabilities.rpg.IRPG;

@FunctionalInterface
public interface IAbility {
    boolean isAvailable(@Nonnull IRPG rpg);
}