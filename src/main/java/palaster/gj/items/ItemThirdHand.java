package palaster.gj.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import palaster.gj.GetAJob;
import palaster.gj.core.proxy.CommonProxy;
import palaster.gj.libs.LibMod;
import palaster.libpal.items.ItemModSpecial;

public class ItemThirdHand extends ItemModSpecial {

	public ItemThirdHand() {
		super();
		setCreativeTab(CommonProxy.tabGJ);
		setRegistryName(LibMod.MODID, "thirdHand");
		setUnlocalizedName("thirdHand");
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new ICapabilitySerializable<NBTBase>() {

			private final ItemStackHandler itemstackHandler = new ItemStackHandler(1) {
				@Override
				public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
					if(stack != null && stack.getMaxStackSize() == 1)
						return super.insertItem(slot, stack, simulate);
					return stack;
				}
			};

			@Override
			public boolean hasCapability(Capability<?> capability, EnumFacing facing) { return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY; }

			@Override
			public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
				if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
					return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemstackHandler);
		        return null;
			}

			@Override
			public NBTBase serializeNBT() { return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(itemstackHandler, null); }

			@Override
			public void deserializeNBT(NBTBase nbt) { CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(itemstackHandler, null, nbt); }
		};
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn) {
		if(!itemStackIn.isRemote && worldIn.isSneaking())
			if(!worldIn.getHeldItem(playerIn).func_190926_b()) {
				worldIn.openGui(GetAJob.instance, 0, itemStackIn, worldIn.getPosition().getX(), worldIn.getPosition().getY(), worldIn.getPosition().getZ());
				return ActionResult.newResult(EnumActionResult.SUCCESS, worldIn.getHeldItem(playerIn));
			}
		return super.onItemRightClick(itemStackIn, worldIn, playerIn);
	}
}
