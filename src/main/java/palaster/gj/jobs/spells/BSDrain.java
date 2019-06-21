package palaster.gj.jobs.spells;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import palaster.gj.api.capabilities.IRPG;
import palaster.gj.api.capabilities.RPGCapability;

public class BSDrain implements IBloodSpell {

    @Override
    public int getBloodCost() { return 50; }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if(!playerIn.world.isRemote)
        {
            IRPG rpg = RPGCapability.RPGCapabilityProvider.get(playerIn);
            if(rpg != null)
            {
                target.attackEntityFrom(DamageSource.MAGIC, rpg.getIntelligence());
                playerIn.heal(rpg.getIntelligence() / 4);
                return true;
            }
        }
        return false;
    }
}
