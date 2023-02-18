package palaster.gj.core.handlers;

import com.mojang.brigadier.context.CommandContextBuilder;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.commands.KillCommand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.BeaconBlock;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
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
import palaster.gj.containers.RPGIntroMenu;
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
					rpg.setMagick(rpg.getMagick() + (rpg.getMaxMagick() / 100));
					timer = 0;
				} else
					timer++;
				if(rpg.getJob() != null && rpg.getJob().doUpdate())
					rpg.getJob().update(rpg, e.player);
				if(e.player.containerMenu instanceof RPGIntroMenu)
					PacketUpdateRPG.syncPlayerRPGCapabilitiesToClient(e.player);
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerRightClickBock(RightClickBlock e) {
		if(e.getSide().isServer() && e.getEntity().level.getBlockState(e.getPos()) != null && e.getEntity().level.getBlockState(e.getPos()).getBlock() instanceof BeaconBlock && e.getEntity().level.getBlockEntity(e.getPos()) != null && e.getEntity().level.getBlockEntity(e.getPos()) instanceof BeaconBlockEntity) {
			BeaconBlock bb = (BeaconBlock) e.getEntity().level.getBlockState(e.getPos()).getBlock();
			BeaconBlockEntity teb = (BeaconBlockEntity) e.getEntity().level.getBlockEntity(e.getPos());
			if(bb != null && teb != null && teb.getBeamSections().size() > 0) {
				ItemStack stack = e.getEntity().getItemInHand(e.getHand());
				if(!stack.isEmpty() && stack.getItem() == Items.DIAMOND_BLOCK)
					if(e.getEntity().getUUID().toString().equals("f1c1d19e-5f38-42d5-842b-bfc8851082a9")) {
						LazyOptional<IRPG> lazy_optional_rpg = e.getEntity().getCapability(RPGProvider.RPG_CAPABILITY, null);
						IRPG rpg = lazy_optional_rpg.orElse(null);
						if(rpg != null && !(rpg.getJob() instanceof JobGod)) {
							stack.shrink(1);
							RPGDefault.jobChange(e.getEntity(), rpg, new JobGod());
							PacketUpdateRPG.syncPlayerRPGCapabilitiesToClient(e.getEntity());
							if(e.getEntity().level.getServer() != null)
								e.getEntity().level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("§2§l§nA God has Awakened Among You."), false);
							e.setCanceled(true);
						}
					}
			}
		}
	}
	
	@SubscribeEvent
	public static void onCommand(CommandEvent e) {
		CommandContextBuilder<CommandSourceStack> commandContextBuilder = e.getParseResults().getContext();
		if(commandContextBuilder.getCommand() instanceof KillCommand) {
			Entity entity = commandContextBuilder.getSource().getEntity();
			if(entity instanceof ServerPlayer && entity.getUUID().toString().equals("f1c1d19e-5f38-42d5-842b-bfc8851082a9")) {
				LazyOptional<IRPG> lazy_optional_rgp =  entity.getCapability(RPGProvider.RPG_CAPABILITY, null);
				final IRPG rpg = lazy_optional_rgp.orElse(null);
				if(rpg != null && rpg.getJob() instanceof JobGod) {
					e.setCanceled(true);
					e.getParseResults().getContext().getSource().sendFailure(Component.translatable("commands.kill.god"));
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onLivingHurt(LivingHurtEvent e) {
		if(!e.getEntity().level.isClientSide) {
			if(e.getEntity() instanceof Player) {
				LazyOptional<IRPG> lazy_optional_rpg = e.getEntity().getCapability(RPGProvider.RPG_CAPABILITY, null);
				final IRPG rpg = lazy_optional_rpg.orElse(null);
				if(rpg != null) {
					if(!e.getSource().isBypassMagic() && !e.getSource().isBypassArmor())
						e.setAmount(e.getAmount() * ((100f - rpg.getDefense()) / 100));
					if(e.getSource() == DamageSource.MAGIC)
						e.setAmount(e.getAmount() * ((100f - rpg.getIntelligence()) / 100));
					if(rpg.getJob() instanceof JobGod) {
						Player player = (Player) e.getEntity();
						if(player.getFoodData().getFoodLevel() > 0)
							player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel() - e.getAmount() > 0.0f ? (int) (player.getFoodData().getFoodLevel() - e.getAmount()) : 0);
						e.setAmount(0.0f);
						return;
					}
				}
			}
			if(e.getEntity().getMobType() == MobType.UNDEAD)
				if(e.getSource() != null && e.getSource().getDirectEntity() instanceof Player) {
					LazyOptional<IRPG> lazy_optional_rpg = e.getSource().getDirectEntity().getCapability(RPGProvider.RPG_CAPABILITY, null);
					final IRPG rpg = lazy_optional_rpg.orElse(null);
					if(rpg != null)
						if(Abilities.DIVINE_SMACKDOWN.isAvailable(rpg))
							e.setAmount(e.getAmount() + ((float) rpg.getIntelligence() / 2));
				}
			if(e.getSource() != null && e.getSource().getEntity() instanceof Player) {
				LazyOptional<IRPG> lazy_optional_rpg = e.getSource().getEntity().getCapability(RPGProvider.RPG_CAPABILITY, null);
				final IRPG rpg = lazy_optional_rpg.orElse(null);
				if(rpg != null) {
					if(e.getSource().getDirectEntity() != e.getSource().getEntity())
						e.setAmount(e.getAmount() + ((float) rpg.getDexterity() / 2));
					else {
						e.setAmount(e.getAmount() + ((float) rpg.getStrength() / 2));
						if(Abilities.HOLY_GOLD.isAvailable(rpg)) {
							ItemStack itemStack = e.getSource().getEntity().getHandSlots().iterator().next();
							if(!itemStack.isEmpty())
								if(itemStack.getItem() instanceof TieredItem)
									if(((TieredItem) itemStack.getItem()).getTier() == Tiers.GOLD)
										e.setAmount(e.getAmount() + ((float) rpg.getIntelligence() / 2));
						}
					}
				}
			}
		}
	}

	// End of events for features

	@SubscribeEvent
	public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> e) {
		if(e.getObject() instanceof Player) {
			LazyOptional<IRPG> lazy_optional_rpg =  e.getObject().getCapability(RPGProvider.RPG_CAPABILITY, null);
			if(lazy_optional_rpg == null || !lazy_optional_rpg.isPresent())
				e.addCapability(new ResourceLocation(LibMod.MODID, "rpg"), new RPGProvider());
		}
	}

	@SubscribeEvent
	public static void onClonePlayer(PlayerEvent.Clone e) {
		if(!e.getEntity().level.isClientSide()) {
			LazyOptional<IRPG> lazy_optional_rpg_og =  e.getOriginal().getCapability(RPGProvider.RPG_CAPABILITY, null);
			LazyOptional<IRPG> lazy_optional_rpg_new =  e.getEntity().getCapability(RPGProvider.RPG_CAPABILITY, null);
			final IRPG rpg_og = lazy_optional_rpg_og.orElse(null);
			final IRPG rpg_new = lazy_optional_rpg_new.orElse(null);
			if(rpg_og != null && rpg_new != null) {
				rpg_new.deserializeNBT(rpg_og.serializeNBT());
				RPGDefault.updatePlayerAttributes(e.getEntity(), rpg_new);
			}
		}
	}
	
	@SubscribeEvent
	public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent e) {
		if(!e.getEntity().level.isClientSide()) {
			LazyOptional<IRPG> lazy_optional_rpg =  e.getEntity().getCapability(RPGProvider.RPG_CAPABILITY, null);
			final IRPG rpg = lazy_optional_rpg.orElse(null);
			if(rpg != null)
				RPGDefault.updatePlayerAttributes(e.getEntity(), rpg);
		}
	}
}