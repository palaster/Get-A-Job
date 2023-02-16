package palaster.gj.jobs.spells.botany;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import palaster.gj.jobs.spells.IBotanySpell;

public class BoSRoot implements IBotanySpell {

	@Override
	public int getCost() { return 250; }

	@Override
	public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, net.minecraft.world.entity.LivingEntity livingEntity, InteractionHand hand) {
		livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 7, false, true, false));
		return InteractionResult.SUCCESS;
	}
}