package palaster.gj.jobs.spells;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class DSCreation implements IDomainSpell {

	@Override
	public InteractionResult useOn(UseOnContext useOnContext) {
		BlockPos pos = useOnContext.getClickedPos();
		BlockState state = useOnContext.getLevel().getBlockState(pos.relative(useOnContext.getClickedFace()));
		if(state == null || state.getBlock() == Blocks.AIR) {
			useOnContext.getLevel().addFreshEntity(new ItemEntity(useOnContext.getLevel(), pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, new ItemStack(Items.OAK_PLANKS, 16)));
			return InteractionResult.SUCCESS;
		}
		return IDomainSpell.super.useOn(useOnContext);
	}
}