package palaster.gj.jobs.spells.domain;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGProvider;

public class DSHeal implements IDomainSpell {
    @Override
    public InteractionResult interactLivingEntity(net.minecraft.world.item.ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand hand) {
        LazyOptional<IRPG> lazy_optional_rpg = player.getCapability(RPGProvider.RPG_CAPABILITY, null);
    	IRPG rpg = lazy_optional_rpg.orElse(null);
        if(rpg != null) {
            livingEntity.heal(rpg.getIntelligence());
            return InteractionResult.SUCCESS;
        }
        return IDomainSpell.super.interactLivingEntity(itemStack, player, livingEntity, hand);
    }
}
