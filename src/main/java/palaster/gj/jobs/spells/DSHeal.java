package palaster.gj.jobs.spells;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import palaster.gj.api.capabilities.IRPG;
import palaster.gj.api.capabilities.RPGCapability;

public class DSHeal implements IDomainSpell {

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        IRPG rpg = RPGCapability.RPGCapabilityProvider.get(playerIn);
        if(rpg != null) {
            target.heal(rpg.getIntelligence());
            return true;
        }
        return false;
    }
}
