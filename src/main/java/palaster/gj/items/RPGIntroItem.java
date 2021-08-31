package palaster.gj.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGDefault;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGProvider;
import palaster.gj.containers.RPGIntroContainer;
import palaster.gj.network.client.PacketUpdateRPG;
import palaster.libpal.api.IReceiveButton;
import palaster.libpal.items.SpecialModItem;

public class RPGIntroItem extends SpecialModItem implements IReceiveButton {
	public RPGIntroItem(Properties properties, ResourceLocation resourceLocation) { super(properties, resourceLocation, 0); }
	
	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
		if(!world.isClientSide) {
			LazyOptional<IRPG> lazy_optional_rpg = playerEntity.getCapability(RPGProvider.RPG_CAPABILITY, null);
			final IRPG rpg = lazy_optional_rpg.orElse(null);
			if(rpg != null) {
				PacketUpdateRPG.syncPlayerRPGCapabilitiesToClient(playerEntity);
				INamedContainerProvider container = new SimpleNamedContainerProvider((containerId, playerInventory, player) -> new RPGIntroContainer(containerId, hand), playerEntity.getItemInHand(hand).getDisplayName());
				NetworkHooks.openGui((ServerPlayerEntity) playerEntity, container, buf -> {
					buf.writeBoolean(hand == Hand.MAIN_HAND);
				});
				return ActionResult.success(playerEntity.getItemInHand(hand));
			}
		}
		return super.use(world, playerEntity, hand);
	}

	@Override
	public void receiveButtonEvent(int buttonId, PlayerEntity player) {
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
