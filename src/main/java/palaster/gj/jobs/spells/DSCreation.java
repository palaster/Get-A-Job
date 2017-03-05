package palaster.gj.jobs.spells;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.gj.jobs.JobCleric.EnumDomain;

public class DSCreation implements IDomainSpell {

	@Override
	public EnumDomain getDomain() { return EnumDomain.CREATION; }
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(worldIn.getBlockState(pos.add(0, 1, 0)) == null || worldIn.getBlockState(pos.add(0, 1, 0)).getBlock() == Blocks.AIR) {
			worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + .5, pos.getY() + 1 + .25, pos.getZ() + .5, new ItemStack(Blocks.PLANKS, 16)));
			return EnumActionResult.SUCCESS;
		}
		return IDomainSpell.super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
}