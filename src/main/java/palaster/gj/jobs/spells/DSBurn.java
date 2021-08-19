package palaster.gj.jobs.spells;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DSBurn implements IDomainSpell {
    @Override
    public ActionResultType interactLivingEntity(ItemStack itemStack, PlayerEntity playerEntity, LivingEntity livingEntity, Hand hand) {
    	livingEntity.setSecondsOnFire(5);
    	return ActionResultType.SUCCESS;
    }

    @Override
    public ActionResultType useOn(ItemUseContext itemUseContext) {
    	PlayerEntity playerentity = itemUseContext.getPlayer();
    	World world = itemUseContext.getLevel();
    	BlockPos blockpos = itemUseContext.getClickedPos();
    	BlockState blockstate = world.getBlockState(blockpos);
    	if(CampfireBlock.canLight(blockstate)) {
    		world.playSound(playerentity, blockpos, SoundEvents.FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
    		world.setBlock(blockpos, blockstate.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
    		return ActionResultType.sidedSuccess(world.isClientSide);
    	} else {
    		BlockPos blockpos1 = blockpos.relative(itemUseContext.getClickedFace());
    		if(AbstractFireBlock.canBePlacedAt(world, blockpos1, itemUseContext.getHorizontalDirection())) {
    			world.playSound(playerentity, blockpos1, SoundEvents.FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
    			world.setBlock(blockpos1, AbstractFireBlock.getState(world, blockpos1), 11);
    			return ActionResultType.sidedSuccess(world.isClientSide);
    		} else
    			return ActionResultType.FAIL;
    	}
    }
}
