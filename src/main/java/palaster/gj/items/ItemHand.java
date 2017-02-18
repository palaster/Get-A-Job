package palaster.gj.items;

import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import palaster.gj.api.capabilities.IRPG;
import palaster.gj.api.capabilities.RPGCapability.RPGCapabilityProvider;
import palaster.gj.core.proxy.CommonProxy;
import palaster.gj.entities.jobs.JobBloodSorcerer;
import palaster.gj.libs.LibMod;
import palaster.libpal.items.ItemModSpecial;

public class ItemHand extends ItemModSpecial {

	public ItemHand() {
		super();
		setCreativeTab(CommonProxy.tabGJ);
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
							e.getEntityPlayer().attackEntityFrom(DamageSource.MAGIC, 1F);
							((JobBloodSorcerer) rpg.getJob()).addBlood(10);
						}
					}
	}
}
