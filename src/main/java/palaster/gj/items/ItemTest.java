package palaster.gj.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import palaster.gj.api.capabilities.IRPG;
import palaster.gj.api.capabilities.RPGCapability.RPGCapabilityProvider;
import palaster.gj.jobs.JobBloodSorcerer;
import palaster.gj.libs.LibMod;
import palaster.libpal.items.ItemModSpecial;

public class ItemTest extends ItemModSpecial {

	public ItemTest() {
		setRegistryName(LibMod.MODID, "test");
		setUnlocalizedName("test");
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(!worldIn.isRemote) {
			ItemStack stack = playerIn.getHeldItem(handIn);
			IRPG rpg = RPGCapabilityProvider.get(playerIn);
			if(rpg != null && rpg.getJob() != null)
				if(rpg.getJob() instanceof JobBloodSorcerer) {
					((JobBloodSorcerer) rpg.getJob()).setBloodRegen(playerIn, ((JobBloodSorcerer) rpg.getJob()).getBloodRegen() + 1);
					return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
				}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}
