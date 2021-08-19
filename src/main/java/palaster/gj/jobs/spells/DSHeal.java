package palaster.gj.jobs.spells;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraftforge.common.util.LazyOptional;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGProvider;

public class DSHeal implements IDomainSpell {
	@Override
    public ActionResultType interactLivingEntity(ItemStack itemStack, PlayerEntity playerEntity, LivingEntity livingEntity, Hand hand) {
    	LazyOptional<IRPG> lazy_optional_rpg = playerEntity.getCapability(RPGProvider.RPG_CAPABILITY, null);
    	IRPG rpg = lazy_optional_rpg.orElse(null);
        if(rpg != null) {
            livingEntity.heal(rpg.getIntelligence());
            return ActionResultType.SUCCESS;
        }
    	return IDomainSpell.super.interactLivingEntity(itemStack, playerEntity, livingEntity, hand);
    }
}
