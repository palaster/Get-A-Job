package palaster.gj.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import palaster.gj.api.capabilities.IRPG;
import palaster.gj.api.capabilities.RPGCapability.RPGCapabilityProvider;
import palaster.gj.entities.jobs.JobBloodSorcerer;
import palaster.gj.libs.LibMod;
import palaster.libpal.core.helpers.PlayerHelper;
import palaster.libpal.items.ItemModSpecial;

public class ItemHand extends ItemModSpecial {

	public ItemHand() {
		super();
		setRegistryName(LibMod.MODID, "hand");
		setUnlocalizedName("hand");
		MinecraftForge.EVENT_BUS.register(this);
	}	

	@SubscribeEvent
	public void onPlayerRightClickItem(RightClickItem e) {
		if(!e.getEntityPlayer().world.isRemote)
			if(e.getHand() == EnumHand.MAIN_HAND)
				if(!e.getEntityPlayer().getHeldItem(e.getHand()).isEmpty() && e.getEntityPlayer().getHeldItem(e.getHand()).getItem() instanceof ItemSword)
					if(!e.getEntityPlayer().getHeldItem(EnumHand.OFF_HAND).isEmpty() && e.getEntityPlayer().getHeldItem(EnumHand.OFF_HAND).getItem() == GJItems.hand) {
						IRPG rpg = RPGCapabilityProvider.get(e.getEntityPlayer());
						if(rpg != null && rpg.getJob() != null && rpg.getJob() instanceof JobBloodSorcerer) {
							e.getEntityPlayer().attackEntityFrom(DamageSource.STARVE, 0.5F);
							((JobBloodSorcerer) rpg.getJob()).addBlood(10);
						}
					}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(!worldIn.isRemote) {
			IRPG rpg = RPGCapabilityProvider.get(playerIn);
			if(rpg != null && rpg.getJob() != null && rpg.getJob() instanceof JobBloodSorcerer)
				PlayerHelper.sendChatMessageToPlayer(playerIn, net.minecraft.util.text.translation.I18n.translateToLocal("gj.job.bloodSorcerer.blood") + ":" + ((JobBloodSorcerer) rpg.getJob()).getBloodCurrent() + "/" + ((JobBloodSorcerer) rpg.getJob()).getBloodMax());
			return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}
