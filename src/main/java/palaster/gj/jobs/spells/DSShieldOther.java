package palaster.gj.jobs.spells;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;

public class DSShieldOther implements IDomainSpell {

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
		if(target.getActivePotionEffect(MobEffects.ABSORPTION) == null) {
			target.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 1200, 0, true, false));
			return true;
		}
		return false;
	}
}