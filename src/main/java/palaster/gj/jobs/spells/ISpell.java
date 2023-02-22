package palaster.gj.jobs.spells;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public interface ISpell {

	Component getSpellName();

	default int getCost() { return 0; }

	default boolean canCast(Player player) { return true; }

	default void applyCost(Player player) {}

	default InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand hand) { return InteractionResult.PASS; }

	default InteractionResult useOn(UseOnContext useOnContext) { return InteractionResult.PASS; }

    default int getUseDuration(ItemStack itemStack) { return 0; }

	default ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) { return itemStack; }

	default InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		if(getUseDuration(player.getItemInHand(hand)) > 0) {
			player.startUsingItem(hand);
			return InteractionResultHolder.consume(player.getItemInHand(hand));
		}
		return InteractionResultHolder.pass(player.getItemInHand(hand));
	}
}
