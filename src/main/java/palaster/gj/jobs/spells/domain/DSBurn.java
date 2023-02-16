package palaster.gj.jobs.spells.domain;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import palaster.gj.jobs.spells.IDomainSpell;

public class DSBurn implements IDomainSpell {

	@Override
	public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand hand) {
		livingEntity.setSecondsOnFire(5);
		return InteractionResult.SUCCESS;
	}

	@Override
	public InteractionResult useOn(UseOnContext useOnContext) {
		Player player = useOnContext.getPlayer();
    	Level level = useOnContext.getLevel();
    	BlockPos blockpos = useOnContext.getClickedPos();
    	BlockState blockstate = level.getBlockState(blockpos);
    	if(CampfireBlock.canLight(blockstate)) {
    		level.playSound(player, blockpos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.4F + 0.8F);
    		level.setBlock(blockpos, blockstate.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
    		return InteractionResult.sidedSuccess(level.isClientSide);
    	} else {
    		BlockPos blockpos1 = blockpos.relative(useOnContext.getClickedFace());
    		if(BaseFireBlock.canBePlacedAt(level, blockpos1, useOnContext.getHorizontalDirection())) {
    			level.playSound(player, blockpos1, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.4F + 0.8F);
    			level.setBlock(blockpos1, BaseFireBlock.getState(level, blockpos1), 11);
    			return InteractionResult.sidedSuccess(level.isClientSide);
    		} else
    			return InteractionResult.FAIL;
    	}
	}
}
