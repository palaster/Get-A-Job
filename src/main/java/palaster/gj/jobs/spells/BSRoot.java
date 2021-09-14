package palaster.gj.jobs.spells;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;

public class BSRoot implements IBotanySpell {

	@Override
	public int getCost() { return 250; }
	
	@Override
	public ActionResultType interactLivingEntity(ItemStack itemStack, PlayerEntity playerEntity, LivingEntity livingEntity, Hand hand) {
		livingEntity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 200, 7, false, true, false));
		return ActionResultType.SUCCESS;
	}
}
