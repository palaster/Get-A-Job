package palaster.gj.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import palaster.gj.api.capabilities.IRPG;
import palaster.gj.api.capabilities.RPGCapability.RPGCapabilityProvider;
import palaster.gj.libs.LibMod;
import palaster.libpal.core.helpers.PlayerHelper;
import palaster.libpal.items.ItemModSpecial;

public class ItemPinkSlip extends ItemModSpecial {

	public ItemPinkSlip() {
		super();
		setRegistryName(LibMod.MODID, "pink_slip");
		setUnlocalizedName("pink_slip");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if(!worldIn.isRemote) {
			final IRPG rpg = RPGCapabilityProvider.get(playerIn);
			if(rpg != null)
				if(rpg.getJob() != null && rpg.getJob().canLeave()) {
					rpg.setJob(playerIn, null);
					PlayerHelper.sendChatMessageToPlayer(playerIn, net.minecraft.util.text.translation.I18n.translateToLocal("gj.job.fired"));
					return ActionResult.newResult(EnumActionResult.SUCCESS, ItemStack.EMPTY);
				}
		}
		return super.onItemRightClick(worldIn, playerIn, hand);
	}
}
