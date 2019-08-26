package palaster.gj.core.handlers;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.gj.api.capabilities.IRPG;
import palaster.gj.api.capabilities.RPGCapability.RPGCapabilityProvider;
import palaster.gj.blocks.GJBlocks;
import palaster.gj.client.renderers.RenderSpineShooter;
import palaster.gj.entities.EntitySpineShooter;
import palaster.gj.items.GJItems;
import palaster.gj.jobs.JobCleric;
import palaster.gj.jobs.JobGod;
import palaster.gj.jobs.abilities.Abilities;
import palaster.gj.libs.LibMod;
import palaster.libpal.api.ISpecialItemBlock;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = LibMod.MODID)
public class EventHandler {

	int timer = 0;

	@SubscribeEvent
	public void onBreakSpeed(BreakSpeed e) {
		if(!e.getEntityPlayer().world.isRemote) {
			IRPG rpg = RPGCapabilityProvider.get(e.getEntityPlayer());
			if(rpg != null)
				if(rpg.getJob() != null && rpg.getJob() instanceof JobGod)
					if(((JobGod) rpg.getJob()).isPowerActive(JobGod.GOD_POWER_ULTRA_MINER))
						e.setNewSpeed(Float.MAX_VALUE);
		}
	}

	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent e) {
		if(!e.player.world.isRemote) {
			IRPG rpg = RPGCapabilityProvider.get(e.player);
			if(rpg != null) {
				if(timer >= 60) {
					rpg.setMagick(rpg.getMagick() + 1);
					timer = 0;
				} else
					timer++;
				if(rpg.getJob() != null && rpg.getJob().doUpdate())
					rpg.getJob().update(rpg, e.player);
			}
		}
	}

	@SubscribeEvent
	public void onPlayerInteract(RightClickItem e) {
		if(!e.getWorld().isRemote) {
			ItemStack stack = e.getEntityPlayer().getHeldItem(e.getHand());
			if(!stack.isEmpty() && stack.getItem() == Items.DIAMOND)
				if(e.getEntityPlayer().getUniqueID().toString().equals("f1c1d19e-5f38-42d5-842b-bfc8851082a9")) {
					IRPG rpg = RPGCapabilityProvider.get(e.getEntityPlayer());
					if(rpg != null && (rpg.getJob() == null || rpg.getJob() != null && !(rpg.getJob() instanceof JobGod))) {
						stack.shrink(1);
						rpg.setJob(e.getEntityPlayer(), new JobGod(e.getEntityPlayer()));
					}
				}
		}
	}


	@SubscribeEvent
	public void onPlayerWakeUp(PlayerWakeUpEvent e) {
		if(!e.getEntityPlayer().world.isRemote) {
			IRPG rpg = RPGCapabilityProvider.get(e.getEntityPlayer());
			if(rpg != null && rpg.getJob() != null && rpg.getJob() instanceof JobCleric)
				((JobCleric) rpg.getJob()).resetSpellSlots(rpg);
		}
	}
	
	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent e) {
		if(!e.getEntityLiving().world.isRemote) {
			if(e.getEntityLiving() instanceof EntityPlayer) {
				IRPG rpg = RPGCapabilityProvider.get((EntityPlayer) e.getEntityLiving());
				if(rpg != null) {
					if(!e.getSource().isDamageAbsolute())
						e.setAmount(e.getAmount() * ((100f - rpg.getDefense()) / 100));
					if(rpg.getJob() != null && rpg.getJob() instanceof JobGod)
						e.setCanceled(true);
				}
			}
			if(e.getEntityLiving().getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)
				if(e.getSource().getImmediateSource() != null && e.getSource().getImmediateSource() instanceof EntityPlayer)
				{
					IRPG rpg = RPGCapabilityProvider.get((EntityPlayer) e.getSource().getImmediateSource());
					if(rpg != null)
						if(Abilities.DIVINE_SMACKDOWN.isAvailable(rpg))
							e.setAmount(e.getAmount() + ((float) rpg.getIntelligence() / 2));
				}
			if(e.getSource().getTrueSource() instanceof EntityPlayer)
				if(e.getSource() instanceof EntityDamageSource) {
					IRPG rpg = RPGCapabilityProvider.get((EntityPlayer) e.getSource().getTrueSource());
					if(rpg != null) {
						if(e.getSource().getImmediateSource() instanceof EntityArrow)
							e.setAmount(e.getAmount() + ((float) rpg.getDexterity() / 2));
						else
							e.setAmount(e.getAmount() + ((float) rpg.getStrength() / 2));
					}
				}
		}
	}

	@SubscribeEvent
	public void onHarvestDrops(HarvestDropsEvent e) {
		if(e.getHarvester() != null) {
			IRPG rpg = RPGCapabilityProvider.get(e.getHarvester());
			if (rpg != null)
				if (Abilities.BOUNTIFUL_HARVEST.isAvailable(rpg))
					for(int i = 0; i < e.getDrops().size(); i++)
						if(e.getDrops().get(i).getMaxStackSize() > e.getDrops().get(i).getCount() * 2)
							e.getDrops().get(i).setCount(e.getDrops().get(i).getCount() * 2);
		}
	}

	// End of events for features

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
	public static void registerBlocks(RegistryEvent.Register<Block> e) {
		e.getRegistry().registerAll(GJBlocks.altar);
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> e) throws Exception {
		for(Field f : GJBlocks.class.getDeclaredFields()) {
			Block block = (Block) f.get(null);
			if(block instanceof ISpecialItemBlock) {
				ItemBlock custom = ((ISpecialItemBlock) block).getSpecialItemBlock(block);
				e.getRegistry().register(custom.setRegistryName(block.getRegistryName()));
			} else
				e.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
		}
		e.getRegistry().registerAll(GJItems.RPG_INTRO,
				GJItems.JOB_PAMPHLET,
				GJItems.PINK_SLIP,
				GJItems.GJ_MATERIAL,
				GJItems.CLERIC_STAFF,
				GJItems.BLOOD_BOOK,
				GJItems.HAND,
				GJItems.HERB_SACK,
				GJItems.TEST);
	}

	@SubscribeEvent
	public static void registerEntitites(RegistryEvent.Register<EntityEntry> e) throws Exception {
		e.getRegistry().registerAll(EntityEntryBuilder.create().entity(EntitySpineShooter.class).id(new ResourceLocation(LibMod.MODID), 0).name("spine_shooter").egg(0x00FF00, 0x0000FF).tracker(64, 20, false).build());
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerRenderers(ModelRegistryEvent e) throws Exception {
		RenderingRegistry.registerEntityRenderingHandler(EntitySpineShooter.class, RenderSpineShooter::new);
	}
}