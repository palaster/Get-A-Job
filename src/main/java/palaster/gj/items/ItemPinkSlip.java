package palaster.gj.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import palaster.gj.api.capabilities.IRPG;
import palaster.gj.api.capabilities.RPGCapability.RPGCapabilityProvider;
import palaster.gj.core.proxy.CommonProxy;
import palaster.gj.libs.LibMod;
import palaster.libpal.core.helpers.PlayerHelper;
import palaster.libpal.items.ItemModSpecial;

public class ItemPinkSlip extends ItemModSpecial {

	public ItemPinkSlip() {
		super();
		setCreativeTab(CommonProxy.tabGJ);
		setRegistryName(LibMod.MODID, "pinkSlip");
		setUnlocalizedName("pinkSlip");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn) {
		if(!itemStackIn.isRemote) {
			final IRPG rpg = RPGCapabilityProvider.get(worldIn);
			if(rpg != null)
				if(rpg.getJob() != null) {
					rpg.setJob(worldIn, null);
					PlayerHelper.sendChatMessageToPlayer(worldIn, net.minecraft.util.text.translation.I18n.translateToLocal("gj.job.fired"));
					return ActionResult.newResult(EnumActionResult.SUCCESS, ItemStack.field_190927_a);
				}
		}
		return super.onItemRightClick(itemStackIn, worldIn, playerIn);
	}
}
