package palaster.gj.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import palaster.gj.GetAJob;
import palaster.gj.api.capabilities.IRPG;
import palaster.gj.api.capabilities.RPGCapability.RPGCapabilityDefault;
import palaster.gj.api.capabilities.RPGCapability.RPGCapabilityProvider;
import palaster.gj.core.proxy.CommonProxy;
import palaster.gj.libs.LibMod;
import palaster.libpal.api.IReceiveButton;
import palaster.libpal.items.ItemModSpecial;

public class ItemRPGIntro extends ItemModSpecial implements IReceiveButton {

	public ItemRPGIntro() {
		super();
		setRegistryName(LibMod.MODID, "rpg_intro");
		setUnlocalizedName("rpg_intro");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if(!worldIn.isRemote && !playerIn.getHeldItem(hand).isEmpty()) {
			final IRPG rpg = RPGCapabilityProvider.get(playerIn);
			if(rpg != null) {
				CommonProxy.syncPlayerRPGCapabilitiesToClient(playerIn);
				playerIn.openGui(GetAJob.instance, 1, worldIn, 0, 0, hand == EnumHand.MAIN_HAND ? 0 : 1);
				return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
			}
		}
		return super.onItemRightClick(worldIn, playerIn, hand);
	}
	
	@Override
	public void receiveButtonEvent(int buttonId, EntityPlayer player) {
		final IRPG rpg = RPGCapabilityProvider.get(player);
		if(rpg != null) {
			switch(buttonId) {
				case 0: {
					if(player.experienceLevel >= RPGCapabilityDefault.getExperienceCostForNextLevel(player) && rpg.getConstitution() < RPGCapabilityDefault.MAX_LEVEL) {
						if(player.experienceLevel - RPGCapabilityDefault.getExperienceCostForNextLevel(player) <= 0)
							player.addExperienceLevel(-player.experienceLevel);
						else if(RPGCapabilityDefault.getExperienceCostForNextLevel(player) > 0)
							player.addExperienceLevel(-RPGCapabilityDefault.getExperienceCostForNextLevel(player));
						rpg.setConstitution(player, rpg.getConstitution() + 1);
						CommonProxy.syncPlayerRPGCapabilitiesToClient(player);
					}
					break;
				}
				case 1: {
					if(player.experienceLevel >= RPGCapabilityDefault.getExperienceCostForNextLevel(player) && rpg.getStrength() < RPGCapabilityDefault.MAX_LEVEL) {
						if(player.experienceLevel - RPGCapabilityDefault.getExperienceCostForNextLevel(player) <= 0)
							player.addExperienceLevel(-player.experienceLevel);
						else if(RPGCapabilityDefault.getExperienceCostForNextLevel(player) > 0)
							player.addExperienceLevel(-RPGCapabilityDefault.getExperienceCostForNextLevel(player));
						rpg.setStrength(rpg.getStrength() + 1);
						CommonProxy.syncPlayerRPGCapabilitiesToClient(player);
					}
					break;
				}
				case 2: {
					if(player.experienceLevel >= RPGCapabilityDefault.getExperienceCostForNextLevel(player) && rpg.getDefense() < RPGCapabilityDefault.MAX_LEVEL) {
						if(player.experienceLevel - RPGCapabilityDefault.getExperienceCostForNextLevel(player) <= 0)
							player.addExperienceLevel(-player.experienceLevel);
						else if(RPGCapabilityDefault.getExperienceCostForNextLevel(player) > 0)
							player.addExperienceLevel(-RPGCapabilityDefault.getExperienceCostForNextLevel(player));
						rpg.setDefense(rpg.getDefense() + 1);
						CommonProxy.syncPlayerRPGCapabilitiesToClient(player);
					}
					break;
				}
				case 3: {
					if(player.experienceLevel >= RPGCapabilityDefault.getExperienceCostForNextLevel(player) && rpg.getDexterity() < RPGCapabilityDefault.MAX_LEVEL) {
						if(player.experienceLevel - RPGCapabilityDefault.getExperienceCostForNextLevel(player) <= 0)
							player.addExperienceLevel(-player.experienceLevel);
						else if(RPGCapabilityDefault.getExperienceCostForNextLevel(player) > 0)
							player.addExperienceLevel(-RPGCapabilityDefault.getExperienceCostForNextLevel(player));
						rpg.setDexterity(player, rpg.getDexterity() + 1);
						CommonProxy.syncPlayerRPGCapabilitiesToClient(player);
					}
					break;
				}
				case 4: {
					if(player.experienceLevel >= RPGCapabilityDefault.getExperienceCostForNextLevel(player) && rpg.getIntelligence() < RPGCapabilityDefault.MAX_LEVEL) {
						if(player.experienceLevel - RPGCapabilityDefault.getExperienceCostForNextLevel(player) <= 0)
							player.addExperienceLevel(-player.experienceLevel);
						else if(RPGCapabilityDefault.getExperienceCostForNextLevel(player) > 0)
							player.addExperienceLevel(-RPGCapabilityDefault.getExperienceCostForNextLevel(player));
						rpg.setIntelligence(rpg.getIntelligence() + 1);
						CommonProxy.syncPlayerRPGCapabilitiesToClient(player);
					}
					break;
				}
			}
		}
	}
}
