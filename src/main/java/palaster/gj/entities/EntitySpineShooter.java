package palaster.gj.entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class EntitySpineShooter extends EntityLiving {

    int lifeTimer = 6000, attackTimer = 20;

    public EntitySpineShooter(World worldIn) {
        super(worldIn);
        setSize(0.8f, 0.8f);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(3, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.0D);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(lifeTimer == 0)
            setDead();
        else
            lifeTimer--;
        if(attackTimer == 0) {
            List<EntityMob> foundMobs = world.getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(posX - 8, posY - 8, posZ - 8, posX + 8, posY + 8, posZ + 8));
            for(EntityMob foundMob : foundMobs) {
                foundMob.attackEntityFrom(DamageSource.CACTUS, 4f);
                break;
            }
        } else
            attackTimer--;

    }
}
