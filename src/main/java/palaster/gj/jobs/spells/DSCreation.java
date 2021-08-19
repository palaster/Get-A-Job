package palaster.gj.jobs.spells;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;

public class DSCreation implements IDomainSpell {
	@Override
	public ActionResultType useOn(ItemUseContext itemUseContext) {
		BlockPos pos = itemUseContext.getClickedPos();
		BlockState state = itemUseContext.getLevel().getBlockState(pos.relative(itemUseContext.getClickedFace()));
		if(state == null || state.getBlock() == Blocks.AIR) {
			itemUseContext.getLevel().addFreshEntity(new ItemEntity(itemUseContext.getLevel(), pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, new ItemStack(Items.OAK_PLANKS, 16)));
			return ActionResultType.SUCCESS;
		}
		return IDomainSpell.super.useOn(itemUseContext);
	}
}