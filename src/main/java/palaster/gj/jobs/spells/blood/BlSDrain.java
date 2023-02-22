package palaster.gj.jobs.spells.blood;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGProvider;

public class BlSDrain implements IBloodSpell {

    @Override
    public int getCost() { return 50; }

    @Override
    public Component getSpellName() { return Component.translatable("gj.job.blood_sorcerer.spell.drain"); }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand hand) {
        LazyOptional<IRPG> lazy_optional_rpg = player.getCapability(RPGProvider.RPG_CAPABILITY, null);
    	IRPG rpg = lazy_optional_rpg.orElse(null);
    	if(rpg != null) {
            livingEntity.hurt(DamageSource.MAGIC, rpg.getIntelligence());
            player.heal(rpg.getIntelligence() / 4);
            return InteractionResult.SUCCESS;
        }
        return IBloodSpell.super.interactLivingEntity(itemStack, player, livingEntity, hand);
    }
}
