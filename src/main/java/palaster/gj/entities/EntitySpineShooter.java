package palaster.gj.entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntitySpineShooter extends EntityLiving {

    int timer = 6000;

    public EntitySpineShooter(World worldIn) {
        super(worldIn);
        setSize(0.8f, 0.8f);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.0D);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(timer == 0)
            setDead();
        else
            timer--;
    }
}
