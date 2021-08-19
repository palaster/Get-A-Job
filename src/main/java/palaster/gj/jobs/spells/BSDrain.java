package palaster.gj.jobs.spells;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraftforge.common.util.LazyOptional;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGProvider;

public class BSDrain implements IBloodSpell {

    @Override
    public int getBloodCost() { return 50; }

    @Override
    public ActionResultType interactLivingEntity(ItemStack itemStack, PlayerEntity playerEntity, LivingEntity livingEntity, Hand hand) {
    	LazyOptional<IRPG> lazy_optional_rpg = playerEntity.getCapability(RPGProvider.RPG_CAPABILITY, null);
    	IRPG rpg = lazy_optional_rpg.orElse(null);
    	if(rpg != null) {
            livingEntity.hurt(DamageSource.MAGIC, rpg.getIntelligence());
            playerEntity.heal(rpg.getIntelligence() / 4);
            return ActionResultType.SUCCESS;
        }
    	return IBloodSpell.super.interactLivingEntity(itemStack, playerEntity, livingEntity, hand);
    }
}
