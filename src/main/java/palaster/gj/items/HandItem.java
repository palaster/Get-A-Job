package palaster.gj.items;

import net.minecraft.item.SwordItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGProvider;
import palaster.gj.jobs.JobBloodSorcerer;
import palaster.libpal.items.SpecialModItem;

public class HandItem extends SpecialModItem {

	public HandItem(Properties properties, ResourceLocation resourceLocation) {
		super(properties, resourceLocation, 0);
		MinecraftForge.EVENT_BUS.register(this);
	}	

	@SubscribeEvent
	public void onPlayerRightClickItem(RightClickItem e) {
		if(!e.getPlayer().level.isClientSide)
			if(e.getHand() == Hand.MAIN_HAND)
				if(!e.getPlayer().getItemInHand(e.getHand()).isEmpty() && e.getPlayer().getItemInHand(e.getHand()).getItem() instanceof SwordItem)
					if(!e.getPlayer().getItemInHand(Hand.OFF_HAND).isEmpty() && e.getPlayer().getItemInHand(Hand.OFF_HAND).getItem() == this) {
						LazyOptional<IRPG> lazy_optional_rpg = e.getPlayer().getCapability(RPGProvider.RPG_CAPABILITY, null);
						final IRPG rpg = lazy_optional_rpg.orElse(null);
						if(rpg != null && rpg.getJob() != null && rpg.getJob() instanceof JobBloodSorcerer) {
							e.getPlayer().hurt(DamageSource.MAGIC, 1F);
							((JobBloodSorcerer) rpg.getJob()).addBlood(e.getPlayer(), 10);
						}
					}
	}
}
