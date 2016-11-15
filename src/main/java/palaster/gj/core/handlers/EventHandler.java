package palaster.gj.core.handlers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import palaster.gj.api.capabilities.entities.IRPG;
import palaster.gj.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.gj.libs.LibMod;

public class EventHandler {
	
	@SubscribeEvent
	public void attachEntityCapability(AttachCapabilitiesEvent<Entity> e) {
		if(e.getObject() instanceof EntityPlayer)
			if(e.getObject() != null && !e.getObject().hasCapability(RPGCapabilityProvider.RPG_CAP, null))
				e.addCapability(new ResourceLocation(LibMod.MODID, "IRPG"), new RPGCapabilityProvider((EntityPlayer) e.getObject()));
	}
	
	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone e) {
		if(!e.getEntityPlayer().worldObj.isRemote) {
			final IRPG rpgOG = RPGCapabilityProvider.get(e.getOriginal());
			final IRPG rpgNew = RPGCapabilityProvider.get(e.getEntityPlayer());
			if(rpgOG != null && rpgNew != null)
				rpgNew.loadNBT(e.getEntityPlayer(), rpgOG.saveNBT());
		}
	}
}
