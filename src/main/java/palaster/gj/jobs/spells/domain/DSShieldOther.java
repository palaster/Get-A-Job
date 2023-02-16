package palaster.gj.jobs.spells.domain;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import palaster.gj.jobs.spells.IDomainSpell;

public class DSShieldOther implements IDomainSpell {
	@Override
	public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand hand) {
		if(!livingEntity.hasEffect(MobEffects.ABSORPTION)) {
			livingEntity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 1200, 0, true, false));
			return InteractionResult.SUCCESS;
		}
		return IDomainSpell.super.interactLivingEntity(itemStack, player, livingEntity, hand);
	}
}