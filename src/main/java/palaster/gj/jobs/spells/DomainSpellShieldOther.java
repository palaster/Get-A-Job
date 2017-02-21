package palaster.gj.jobs.spells;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.gj.jobs.JobCleric.EnumDomain;

public class DomainSpellShieldOther implements IDomainSpell {

	@Override
	public EnumDomain getDomain() { return EnumDomain.COMMUNITY; }

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) { return ActionResult.newResult(EnumActionResult.PASS, playerIn.getHeldItem(handIn)); }

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) { return EnumActionResult.PASS; }

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
		if(target.getActivePotionEffect(MobEffects.ABSORPTION) == null) {
			target.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 1200, 0, true, false));
			return true;
		}
		return false;
	}
}
