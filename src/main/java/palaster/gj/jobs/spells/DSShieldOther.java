package palaster.gj.jobs.spells;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;

public class DSShieldOther implements IDomainSpell {
	@Override
	public ActionResultType interactLivingEntity(ItemStack itemStack, PlayerEntity playerEntity, LivingEntity livingEntity, Hand hand) {
		if(!livingEntity.hasEffect(Effects.ABSORPTION)) {
			livingEntity.addEffect(new EffectInstance(Effects.ABSORPTION, 1200, 0, true, false));
			return ActionResultType.SUCCESS;
		}
		return IDomainSpell.super.interactLivingEntity(itemStack, playerEntity, livingEntity, hand);
	}
	
}