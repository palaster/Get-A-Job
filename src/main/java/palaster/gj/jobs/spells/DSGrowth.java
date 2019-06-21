package palaster.gj.jobs.spells;

import net.minecraft.block.IGrowable;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.gj.jobs.JobCleric.EnumDomain;

public class DSGrowth implements IDomainSpell {
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
		if(target instanceof EntityAgeable) {
			((EntityAgeable) target).setGrowingAge(0);
			return true;
		}
		return IDomainSpell.super.itemInteractionForEntity(stack, playerIn, target, hand);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(worldIn.getBlockState(pos) != null && worldIn.getBlockState(pos).getBlock() != Blocks.AIR)
			if(worldIn.getBlockState(pos).getBlock() instanceof IGrowable) {
				((IGrowable) worldIn.getBlockState(pos).getBlock()).grow(worldIn, worldIn.rand, pos, worldIn.getBlockState(pos));
				return EnumActionResult.SUCCESS;
			}
		return IDomainSpell.super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
}
