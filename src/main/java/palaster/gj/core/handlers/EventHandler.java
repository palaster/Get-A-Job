package palaster.gj.core.handlers;

import java.lang.reflect.Field;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBeacon;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandKill;
import net.minecraft.command.EntityNotFoundException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
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
import palaster.gj.core.proxy.CommonProxy;
import palaster.gj.entities.EntitySpineShooter;
import palaster.gj.items.GJItems;
import palaster.gj.jobs.JobGod;
import palaster.gj.jobs.abilities.Abilities;
import palaster.gj.libs.LibMod;
import palaster.libpal.api.ISpecialItemBlock;

@Mod.EventBusSubscriber(modid = LibMod.MODID)
public class EventHandler {

	int timer = 0;

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
	public void onPlayerRightClickBock(RightClickBlock e) {
		if(!e.getWorld().isRemote && e.getWorld().getBlockState(e.getPos()) != null && e.getWorld().getBlockState(e.getPos()).getBlock() instanceof BlockBeacon && e.getWorld().getTileEntity(e.getPos()) != null && e.getWorld().getTileEntity(e.getPos()) instanceof TileEntityBeacon) {
			BlockBeacon bb = (BlockBeacon) e.getWorld().getBlockState(e.getPos()).getBlock();
			TileEntityBeacon teb = (TileEntityBeacon) e.getWorld().getTileEntity(e.getPos());
			if(bb != null && teb != null && teb.getLevels() > 0) {
				ItemStack stack = e.getEntityPlayer().getHeldItem(e.getHand());
				if(!stack.isEmpty() && stack.getItem() == Item.getItemFromBlock(Blocks.DIAMOND_BLOCK))
					if(e.getEntityPlayer().getUniqueID().toString().equals("f1c1d19e-5f38-42d5-842b-bfc8851082a9")) {
						IRPG rpg = RPGCapabilityProvider.get(e.getEntityPlayer());
						if(rpg != null && !(rpg.getJob() instanceof JobGod)) {
							stack.shrink(1);
							rpg.setJob(new JobGod());
							CommonProxy.syncPlayerRPGCapabilitiesToClient(e.getEntityPlayer());
							FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendMessage(new TextComponentString("§2§l§nA God has Awakened Among You."));
							e.setCanceled(true);
						}
					}
			}
		}
	}
	
	@SubscribeEvent
	public void onCommand(CommandEvent e) {
		if(e.getCommand() instanceof CommandKill) {
			if(e.getSender() != null && e.getSender().getServer() != null) {
				try {
					Entity entity = CommandBase.getEntity(e.getSender().getServer(), e.getSender(), e.getParameters()[0]);
					if(entity != null && entity instanceof EntityPlayer && entity.getUniqueID().toString().equals("f1c1d19e-5f38-42d5-842b-bfc8851082a9")) {
						IRPG rpg = RPGCapabilityProvider.get((EntityPlayer) entity);
						if(rpg != null && rpg.getJob() != null && rpg.getJob() instanceof JobGod)
						{
							e.setCanceled(true);
							CommandBase.notifyCommandListener(e.getSender(), e.getCommand(), "commands.kill.god", new Object[] {});
						}
					}
				} catch (EntityNotFoundException entityNotFoundException) {
				} catch (CommandException commandException) {
				}
			}
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
					if(rpg.getJob() != null && rpg.getJob() instanceof JobGod) {
						EntityPlayer player = (EntityPlayer) e.getEntityLiving();
						if(player.getFoodStats().getFoodLevel() > 0)
							player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - e.getAmount() > 0.0f ? (int) (player.getFoodStats().getFoodLevel() - e.getAmount()) : 0);
						e.setAmount(0.0f);
					}
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
		if(e.getHarvester() != null && e.getWorld() != null && e.getPos() != null && e.getState() != null) {
			IRPG rpg = RPGCapabilityProvider.get(e.getHarvester());
			if (rpg != null)
				if (e.getState().getBlock() instanceof IPlantable)
					for(int i = 0; i < e.getDrops().size(); i++)
						if (Abilities.BOUNTIFUL_HARVEST.isAvailable(rpg))
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
				rpgNew.loadNBT(rpgOG.saveNBT());
		}
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> e) {
		e.getRegistry().registerAll(GJBlocks.ALTAR);
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
				GJItems.GOSPEL,
				GJItems.BLOOD_BOOK,
				GJItems.HAND,
				GJItems.HERB_SACK,
				GJItems.TEST);
	}

	@SubscribeEvent
	public static void registerEntitites(RegistryEvent.Register<EntityEntry> e) {
		e.getRegistry().registerAll(EntityEntryBuilder.create().entity(EntitySpineShooter.class).id(new ResourceLocation(LibMod.MODID), 0).name("spine_shooter").egg(0x00FF00, 0x0000FF).tracker(64, 20, false).build());
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerRenderers(ModelRegistryEvent e) {
		RenderingRegistry.registerEntityRenderingHandler(EntitySpineShooter.class, RenderSpineShooter::new);
	}
}