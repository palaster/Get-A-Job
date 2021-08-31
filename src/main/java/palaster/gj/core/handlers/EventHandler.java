package palaster.gj.core.handlers;

import com.mojang.brigadier.context.CommandContextBuilder;

import net.minecraft.block.BeaconBlock;
import net.minecraft.command.CommandSource;
import net.minecraft.command.impl.KillCommand;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.BeaconTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGDefault;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGProvider;
import palaster.gj.containers.RPGIntroContainer;
import palaster.gj.jobs.JobGod;
import palaster.gj.jobs.abilities.Abilities;
import palaster.gj.libs.LibMod;
import palaster.gj.network.client.PacketUpdateRPG;

public class EventHandler {

	static int timer = 0;

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent e) {
		if(e.side.isServer()) {
			LazyOptional<IRPG> lazy_optional_rpg = e.player.getCapability(RPGProvider.RPG_CAPABILITY, null);
			IRPG rpg = lazy_optional_rpg.orElse(null);
			if(rpg != null) {
				if(timer >= 60) {
					rpg.setMagick(rpg.getMagick() + 1);
					timer = 0;
				} else
					timer++;
				if(rpg.getJob() != null && rpg.getJob().doUpdate())
					rpg.getJob().update(rpg, e.player);
				if(e.player.containerMenu instanceof RPGIntroContainer)
					PacketUpdateRPG.syncPlayerRPGCapabilitiesToClient(e.player);
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerRightClickBock(RightClickBlock e) {
		if(e.getSide().isServer() && e.getWorld().getBlockState(e.getPos()) != null && e.getWorld().getBlockState(e.getPos()).getBlock() instanceof BeaconBlock && e.getWorld().getBlockEntity(e.getPos()) != null && e.getWorld().getBlockEntity(e.getPos()) instanceof BeaconTileEntity) {
			BeaconBlock bb = (BeaconBlock) e.getWorld().getBlockState(e.getPos()).getBlock();
			BeaconTileEntity teb = (BeaconTileEntity) e.getWorld().getBlockEntity(e.getPos());
			if(bb != null && teb != null && teb.getLevels() > 0) {
				ItemStack stack = e.getPlayer().getItemInHand(e.getHand());
				if(!stack.isEmpty() && stack.getItem() == Items.DIAMOND_BLOCK)
					if(e.getPlayer().getUUID().toString().equals("f1c1d19e-5f38-42d5-842b-bfc8851082a9")) {
						LazyOptional<IRPG> lazy_optional_rpg = e.getPlayer().getCapability(RPGProvider.RPG_CAPABILITY, null);
						IRPG rpg = lazy_optional_rpg.orElse(null);
						if(rpg != null && !(rpg.getJob() instanceof JobGod)) {
							stack.shrink(1);
							RPGDefault.jobChange(e.getPlayer(), rpg, new JobGod());
							PacketUpdateRPG.syncPlayerRPGCapabilitiesToClient(e.getPlayer());
							if(e.getWorld().getServer() != null)
								e.getWorld().getServer().getPlayerList().broadcastMessage(new StringTextComponent("§2§l§nA God has Awakened Among You."), ChatType.CHAT, e.getPlayer().getUUID());
							e.setCanceled(true);
						}
					}
			}
		}
	}
	
	@SubscribeEvent
	public static void onCommand(CommandEvent e) {
		CommandContextBuilder<CommandSource> commandContextBuilder = e.getParseResults().getContext();
		if(commandContextBuilder.getCommand() instanceof KillCommand) {
			Entity entity = commandContextBuilder.getSource().getEntity();
			if(entity instanceof ServerPlayerEntity && entity.getUUID().toString().equals("f1c1d19e-5f38-42d5-842b-bfc8851082a9")) {
				LazyOptional<IRPG> lazy_optional_rgp =  entity.getCapability(RPGProvider.RPG_CAPABILITY, null);
				final IRPG rpg = lazy_optional_rgp.orElse(null);
				if(rpg != null && rpg.getJob() instanceof JobGod) {
					e.setCanceled(true);
					e.getParseResults().getContext().getSource().sendFailure(new TranslationTextComponent("commands.kill.god"));
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onLivingHurt(LivingHurtEvent e) {
		if(!e.getEntityLiving().level.isClientSide) {
			if(e.getEntityLiving() instanceof PlayerEntity) {
				LazyOptional<IRPG> lazy_optional_rpg = e.getEntityLiving().getCapability(RPGProvider.RPG_CAPABILITY, null);
				final IRPG rpg = lazy_optional_rpg.orElse(null);
				if(rpg != null) {
					if(!e.getSource().isBypassMagic() && !e.getSource().isBypassArmor())
						e.setAmount(e.getAmount() * ((100f - rpg.getDefense()) / 100));
					if(rpg.getJob() instanceof JobGod) {
						PlayerEntity player = (PlayerEntity) e.getEntityLiving();
						if(player.getFoodData().getFoodLevel() > 0)
							player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel() - e.getAmount() > 0.0f ? (int) (player.getFoodData().getFoodLevel() - e.getAmount()) : 0);
						e.setAmount(0.0f);
					}
				}
			}
			if(e.getEntityLiving().getMobType() == CreatureAttribute.UNDEAD)
				if(e.getSource().getDirectEntity() != null && e.getSource().getDirectEntity() instanceof PlayerEntity) {
					LazyOptional<IRPG> lazy_optional_rpg = e.getSource().getDirectEntity().getCapability(RPGProvider.RPG_CAPABILITY, null);
					final IRPG rpg = lazy_optional_rpg.orElse(null);
					if(rpg != null)
						if(Abilities.DIVINE_SMACKDOWN.isAvailable(rpg))
							e.setAmount(e.getAmount() + ((float) rpg.getIntelligence() / 2));
				}
			if(e.getSource().getEntity() instanceof PlayerEntity) {
				LazyOptional<IRPG> lazy_optional_rpg = e.getSource().getEntity().getCapability(RPGProvider.RPG_CAPABILITY, null);
				final IRPG rpg = lazy_optional_rpg.orElse(null);
				if(rpg != null) {
					if(e.getSource().getDirectEntity() != e.getSource().getEntity())
						e.setAmount(e.getAmount() + ((float) rpg.getDexterity() / 2));
					else
						e.setAmount(e.getAmount() + ((float) rpg.getStrength() / 2));
				}
			}
		}
	}

	/* TODO: Fix onHarvestDrops  https://github.com/MinecraftForge/MinecraftForge/pull/6401
	@SubscribeEvent
	public static void onHarvestDrops(HarvestDropsEvent e) {
		if(e.getHarvester() != null && e.getWorld() != null && e.getPos() != null && e.getState() != null) {
			IRPG rpg = RPGProvider.get(e.getHarvester());
			if (rpg != null)
				if (e.getState().getBlock() instanceof IPlantable)
					for(int i = 0; i < e.getDrops().size(); i++)
						if (Abilities.BOUNTIFUL_HARVEST.isAvailable(rpg))
							if(e.getDrops().get(i).getMaxStackSize() > e.getDrops().get(i).getCount() * 2)
								e.getDrops().get(i).setCount(e.getDrops().get(i).getCount() * 2);
		}
	}
	*/

	// End of events for features

	@SubscribeEvent
	public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> e) {
		if(e.getObject() instanceof PlayerEntity) {
			LazyOptional<IRPG> lazy_optional_rpg =  e.getObject().getCapability(RPGProvider.RPG_CAPABILITY, null);
			if(lazy_optional_rpg == null || !lazy_optional_rpg.isPresent())
				e.addCapability(new ResourceLocation(LibMod.MODID, "rpg"), new RPGProvider());
		}
	}

	@SubscribeEvent
	public static void onClonePlayer(PlayerEvent.Clone e) {
		if(!e.getPlayer().level.isClientSide()) {
			LazyOptional<IRPG> lazy_optional_rpg_og =  e.getOriginal().getCapability(RPGProvider.RPG_CAPABILITY, null);
			LazyOptional<IRPG> lazy_optional_rpg_new =  e.getPlayer().getCapability(RPGProvider.RPG_CAPABILITY, null);
			final IRPG rpg_og = lazy_optional_rpg_og.orElse(null);
			final IRPG rpg_new = lazy_optional_rpg_new.orElse(null);
			if(rpg_og != null && rpg_new != null) {
				rpg_new.deserializeNBT(rpg_og.serializeNBT());
				RPGDefault.updatePlayerAttributes(e.getPlayer(), rpg_new);
			}
		}
	}
	
	@SubscribeEvent
	public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent e) {
		if(!e.getPlayer().level.isClientSide()) {
			LazyOptional<IRPG> lazy_optional_rpg =  e.getPlayer().getCapability(RPGProvider.RPG_CAPABILITY, null);
			final IRPG rpg = lazy_optional_rpg.orElse(null);
			if(rpg != null)
				RPGDefault.updatePlayerAttributes(e.getPlayer(), rpg);
		}
	}

	/* TODO: Old Entity/Renderer registry
	@SubscribeEvent
	public static void registerEntitites(RegistryEvent.Register<EntityEntry> e) {
		e.getRegistry().registerAll(EntityEntryBuilder.create().entity(EntitySpineShooter.class).id(new ResourceLocation(LibMod.MODID), 0).name("spine_shooter").egg(0x00FF00, 0x0000FF).tracker(64, 20, false).build());
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerRenderers(ModelRegistryEvent e) {
		RenderingRegistry.registerEntityRenderingHandler(EntitySpineShooter.class, RenderSpineShooter::new);
	}
	*/
}