package palaster.gj.jobs.spells;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public interface ISpell {
	default InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand hand) { return InteractionResult.PASS; }

	default InteractionResult useOn(UseOnContext useOnContext) { return InteractionResult.PASS; }

	default InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) { return InteractionResultHolder.pass(player.getItemInHand(hand)); }
}
