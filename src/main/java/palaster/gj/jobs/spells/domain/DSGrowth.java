package palaster.gj.jobs.spells.domain;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class DSGrowth implements IDomainSpell {

	@Override
	public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand hand) {
		if(livingEntity instanceof AgeableMob && ((AgeableMob) livingEntity).isBaby()) {
			((AgeableMob) livingEntity).setAge(0);
			return InteractionResult.SUCCESS;
		}
		return IDomainSpell.super.interactLivingEntity(itemStack, player, livingEntity, hand);
	}

	@Override
	public InteractionResult useOn(UseOnContext useOnContext) {
		if(useOnContext.getLevel() instanceof ServerLevel) {
			BlockState state = useOnContext.getLevel().getBlockState(useOnContext.getClickedPos());
			if(state != null && state.getBlock() != Blocks.AIR)
				if(state.getBlock() instanceof BonemealableBlock) {
					((BonemealableBlock) state.getBlock()).performBonemeal((ServerLevel) useOnContext.getLevel(), useOnContext.getLevel().random, useOnContext.getClickedPos(), state);
					return InteractionResult.SUCCESS;
				}
		}
		return IDomainSpell.super.useOn(useOnContext);
	}
}
