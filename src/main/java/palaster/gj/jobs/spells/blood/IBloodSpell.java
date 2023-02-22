package palaster.gj.jobs.spells.blood;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGProvider;
import palaster.gj.jobs.BloodSorcererJob;
import palaster.gj.jobs.spells.ISpell;

public interface IBloodSpell extends ISpell {

    int getBloodCost();

    @Override
    default boolean canCast(Player player) {
        LazyOptional<IRPG> lazy_optional_rpg = player.getCapability(RPGProvider.RPG_CAPABILITY, null);
		final IRPG rpg = lazy_optional_rpg.orElse(null);
        if(rpg != null && rpg.getJob() instanceof BloodSorcererJob)
            return true;
        else
            return false;
    }

    @Override
    default void applyCost(Player player) {
        LazyOptional<IRPG> lazy_optional_rpg = player.getCapability(RPGProvider.RPG_CAPABILITY, null);
		final IRPG rpg = lazy_optional_rpg.orElse(null);
        if(rpg != null && rpg.getJob() instanceof BloodSorcererJob)
            ((BloodSorcererJob) rpg.getJob()).removeBlood(player, getBloodCost());
    }
}
