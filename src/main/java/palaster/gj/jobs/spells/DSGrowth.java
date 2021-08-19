package palaster.gj.jobs.spells;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.server.ServerWorld;

public class DSGrowth implements IDomainSpell {
	@Override
	public ActionResultType interactLivingEntity(ItemStack itemStack, PlayerEntity playerEntity, LivingEntity livingEntity, Hand hand) {
		if(livingEntity instanceof AgeableEntity && ((AgeableEntity) livingEntity).isBaby()) {
			((AgeableEntity) livingEntity).setAge(0);
			return ActionResultType.SUCCESS;
		}
		return IDomainSpell.super.interactLivingEntity(itemStack, playerEntity, livingEntity, hand);
	}
	
	@Override
	public ActionResultType useOn(ItemUseContext itemUseContext) {
		if(itemUseContext.getLevel() instanceof ServerWorld) {
			BlockState state = itemUseContext.getLevel().getBlockState(itemUseContext.getClickedPos());
			if(state != null && state.getBlock() != Blocks.AIR)
				if(state.getBlock() instanceof IGrowable) {
					((IGrowable) state.getBlock()).performBonemeal((ServerWorld) itemUseContext.getLevel(), itemUseContext.getLevel().random, itemUseContext.getClickedPos(), state);
					return ActionResultType.SUCCESS;
				}
		}
		return IDomainSpell.super.useOn(itemUseContext);
	}
}
