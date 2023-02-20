package palaster.gj.jobs.spells.botany;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGProvider;
import palaster.gj.jobs.BotanistJob;
import palaster.gj.jobs.spells.ISpell;

public interface IBotanySpell extends ISpell {
    int getCost();

    @Override
    default boolean canCast(Player player) {
        LazyOptional<IRPG> lazy_optional_rpg = player.getCapability(RPGProvider.RPG_CAPABILITY, null);
		final IRPG rpg = lazy_optional_rpg.orElse(null);
        if(rpg != null && rpg.getJob() instanceof BotanistJob)
            return rpg.getMagick() >= getCost();
        else
            return false;
    }

    @Override
    default void applyCost(Player player) {
        LazyOptional<IRPG> lazy_optional_rpg = player.getCapability(RPGProvider.RPG_CAPABILITY, null);
		final IRPG rpg = lazy_optional_rpg.orElse(null);
        if(rpg != null)
            rpg.setMagick(rpg.getMagick() - getCost());
    }
}
