package palaster.gj.items;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.NetworkHooks;
import palaster.gj.api.IReceiveButton;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGDefault;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGProvider;
import palaster.gj.containers.RPGIntroMenu;
import palaster.gj.network.client.PacketUpdateRPG;

public class RPGIntroItem extends Item implements IReceiveButton {

	public RPGIntroItem(Properties properties) { super(properties); }
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		if(!level.isClientSide) {
			LazyOptional<IRPG> lazy_optional_rpg = player.getCapability(RPGProvider.RPG_CAPABILITY, null);
			final IRPG rpg = lazy_optional_rpg.orElse(null);
			if(rpg != null) {
				PacketUpdateRPG.syncPlayerRPGCapabilitiesToClient(player);
				MenuProvider container = new SimpleMenuProvider((containerId, playerInventory, playerEntity) -> new RPGIntroMenu(containerId, hand), player.getItemInHand(hand).getDisplayName());
				NetworkHooks.openScreen((ServerPlayer) player, container, buf -> {
					buf.writeBoolean(hand == InteractionHand.MAIN_HAND);
				});
				return InteractionResultHolder.success(player.getItemInHand(hand));
			}
		}
		return super.use(level, player, hand);
	}

	@Override
	public void receiveButtonEvent(Player player, int buttonId) {
		LazyOptional<IRPG> lazy_optional_rpg = player.getCapability(RPGProvider.RPG_CAPABILITY, null);
		final IRPG rpg = lazy_optional_rpg.orElse(null);
		if(rpg != null) {
			switch(buttonId) {
				case 0: {
					if(player.experienceLevel >= RPGDefault.getExperienceCostForNextLevel(player) && rpg.getConstitution() < RPGDefault.MAX_LEVEL) {
						if(player.experienceLevel - RPGDefault.getExperienceCostForNextLevel(player) <= 0)
							player.giveExperienceLevels(-player.experienceLevel);
						else if(RPGDefault.getExperienceCostForNextLevel(player) > 0)
							player.giveExperienceLevels(-RPGDefault.getExperienceCostForNextLevel(player));
						rpg.setConstitution(rpg.getConstitution(true) + 1);
						rpg.setExperienceSaved(0);
						RPGDefault.updatePlayerAttributes(player, rpg);
						PacketUpdateRPG.syncPlayerRPGCapabilitiesToClient(player);
					}
					break;
				}
				case 1: {
					if(player.experienceLevel >= RPGDefault.getExperienceCostForNextLevel(player) && rpg.getStrength() < RPGDefault.MAX_LEVEL) {
						if(player.experienceLevel - RPGDefault.getExperienceCostForNextLevel(player) <= 0)
							player.giveExperienceLevels(-player.experienceLevel);
						else if(RPGDefault.getExperienceCostForNextLevel(player) > 0)
							player.giveExperienceLevels(-RPGDefault.getExperienceCostForNextLevel(player));
						rpg.setStrength(rpg.getStrength(true) + 1);
						rpg.setExperienceSaved(0);
						RPGDefault.updatePlayerAttributes(player, rpg);
						PacketUpdateRPG.syncPlayerRPGCapabilitiesToClient(player);
					}
					break;
				}
				case 2: {
					if(player.experienceLevel >= RPGDefault.getExperienceCostForNextLevel(player) && rpg.getDefense() < RPGDefault.MAX_LEVEL) {
						if(player.experienceLevel - RPGDefault.getExperienceCostForNextLevel(player) <= 0)
							player.giveExperienceLevels(-player.experienceLevel);
						else if(RPGDefault.getExperienceCostForNextLevel(player) > 0)
							player.giveExperienceLevels(-RPGDefault.getExperienceCostForNextLevel(player));
						rpg.setDefense(rpg.getDefense(true) + 1);
						rpg.setExperienceSaved(0);
						RPGDefault.updatePlayerAttributes(player, rpg);
						PacketUpdateRPG.syncPlayerRPGCapabilitiesToClient(player);
					}
					break;
				}
				case 3: {
					if(player.experienceLevel >= RPGDefault.getExperienceCostForNextLevel(player) && rpg.getDexterity() < RPGDefault.MAX_LEVEL) {
						if(player.experienceLevel - RPGDefault.getExperienceCostForNextLevel(player) <= 0)
							player.giveExperienceLevels(-player.experienceLevel);
						else if(RPGDefault.getExperienceCostForNextLevel(player) > 0)
							player.giveExperienceLevels(-RPGDefault.getExperienceCostForNextLevel(player));
						rpg.setDexterity(rpg.getDexterity(true) + 1);
						rpg.setExperienceSaved(0);
						RPGDefault.updatePlayerAttributes(player, rpg);
						PacketUpdateRPG.syncPlayerRPGCapabilitiesToClient(player);
					}
					break;
				}
				case 4: {
					if(player.experienceLevel >= RPGDefault.getExperienceCostForNextLevel(player) && rpg.getIntelligence() < RPGDefault.MAX_LEVEL) {
						if(player.experienceLevel - RPGDefault.getExperienceCostForNextLevel(player) <= 0)
							player.giveExperienceLevels(-player.experienceLevel);
						else if(RPGDefault.getExperienceCostForNextLevel(player) > 0)
							player.giveExperienceLevels(-RPGDefault.getExperienceCostForNextLevel(player));
						rpg.setIntelligence(rpg.getIntelligence(true) + 1);
						rpg.setExperienceSaved(0);
						RPGDefault.updatePlayerAttributes(player, rpg);
						PacketUpdateRPG.syncPlayerRPGCapabilitiesToClient(player);
					}
					break;
				}
				case 5: {
					if(player.experienceLevel >= 1 && RPGDefault.getExperienceCostForNextLevel(player) > 1) {
						player.giveExperienceLevels(-1);
						rpg.setExperienceSaved(rpg.getExperienceSaved() + 1);
						PacketUpdateRPG.syncPlayerRPGCapabilitiesToClient(player);
					}
					break;
				}
			}
		}
	}
}
