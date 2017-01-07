package palaster.gj.items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.gj.core.proxy.CommonProxy;
import palaster.libpal.core.helpers.NBTHelper;
import palaster.libpal.items.ItemModSpecial;

public abstract class ItemModStaff extends ItemModSpecial {

	public static String TAG_INT_POWER = "gj:StaffPower";
	public String[] powers = new String[] {};

	public ItemModStaff() { this(256); }
	
	public ItemModStaff(int maxDamage) {
		super(maxDamage);
		setCreativeTab(CommonProxy.tabGJ);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if(stack.hasTagCompound())
			tooltip.add(I18n.format("bb.staff.active") + ": " + I18n.format(((ItemModStaff) stack.getItem()).powers[getActivePower(stack)]));
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) { NBTHelper.setIntegerToItemStack(stack, TAG_INT_POWER, 0); }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if(!worldIn.isRemote && playerIn.isSneaking()) {
			ItemStack stack = playerIn.getHeldItem(hand);
			if(stack != null)
				if(getActiveMax(stack) != 0)
					if(getActivePower(stack) == (getActiveMax(stack) - 1)) {
						setActivePower(stack, 0);
						return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
					} else {
						setActivePower(stack, getActivePower(stack) + 1);
						return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
					}
		}
		return super.onItemRightClick(worldIn, playerIn, hand);
	}
	
	public static final void setActivePower(ItemStack stack, int value) {
		if(getActiveMax(stack) == 0)
			NBTHelper.setIntegerToItemStack(stack, TAG_INT_POWER, 0);
		else
			NBTHelper.setIntegerToItemStack(stack, TAG_INT_POWER, value);
	}
	
	public static final int getActivePower(ItemStack stack) { return NBTHelper.getIntegerFromItemStack(stack, TAG_INT_POWER); }
	
	public static final int getActiveMax(ItemStack stack) { return ((ItemModStaff) stack.getItem()).powers.length; }
}
