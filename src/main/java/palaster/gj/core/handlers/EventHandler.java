package palaster.gj.core.handlers;

import java.lang.reflect.Field;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import palaster.gj.api.capabilities.IRPG;
import palaster.gj.api.capabilities.RPGCapability.RPGCapabilityProvider;
import palaster.gj.blocks.GJBlocks;
import palaster.gj.items.GJItems;
import palaster.gj.libs.LibMod;

@Mod.EventBusSubscriber(modid = LibMod.MODID)
public class EventHandler {

	@SubscribeEvent
	public void attachEntityCapability(AttachCapabilitiesEvent<Entity> e) {
		if(e.getObject() instanceof EntityPlayer && e.getObject() != null && !e.getObject().hasCapability(RPGCapabilityProvider.RPG_CAP, null))
			e.addCapability(new ResourceLocation(LibMod.MODID, "IRPG"), new RPGCapabilityProvider((EntityPlayer) e.getObject()));
	}

	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone e) {
		if(!e.getEntityPlayer().world.isRemote) {
			final IRPG rpgOG = RPGCapabilityProvider.get(e.getOriginal());
			final IRPG rpgNew = RPGCapabilityProvider.get(e.getEntityPlayer());
			if(rpgOG != null && rpgNew != null)
				rpgNew.loadNBT(e.getEntityPlayer(), rpgOG.saveNBT());
		}
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> e) {}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> e) throws Exception {
		for(Field f : GJBlocks.class.getDeclaredFields()) {
			Block block = (Block) f.get(null);
			e.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
		}
		e.getRegistry().registerAll(GJItems.rpgIntro,
				GJItems.jobPamphlet,
				GJItems.pinkSlip,
				GJItems.gjMaterial,
				GJItems.clericStaff,
				GJItems.hand);
	}
}