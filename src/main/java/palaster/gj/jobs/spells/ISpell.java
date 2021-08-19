package palaster.gj.jobs.spells;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public interface ISpell {
	default ActionResultType interactLivingEntity(ItemStack itemStack, PlayerEntity playerEntity, LivingEntity livingEntity, Hand hand) { return ActionResultType.PASS; }
	
	default ActionResultType useOn(ItemUseContext itemUseContext) { return ActionResultType.PASS; }
	
	default ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) { return ActionResult.pass(playerEntity.getItemInHand(hand)); }
}
