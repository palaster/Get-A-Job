/* TODO: fix me
package palaster.gj.entities;

import java.util.List;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class SpineShooterEntity extends MobEntity {

    int lifeTimer = 6000, attackTimer = 20;
    
    public SpineShooterEntity(EntityType<? extends SpineShooterEntity> entityType, World world) {
        super(entityType, world);
        //setSize(0.8f, 0.8f);
    }
    
    @Override
    protected void registerGoals() {
    	super.registerGoals();
    	goalSelector.addGoal(0, new SwimGoal(this));
    	goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 6.0f));
    	goalSelector.addGoal(1, new LookRandomlyGoal(this));
    }
    
    public static AttributeModifierMap.MutableAttribute createLivingAttributes() {
    	return LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH, 1.0D);
    }

    @Override
    public void tick() {
        super.tick();
        if(lifeTimer == 0)
            kill();
        else
            lifeTimer--;
        if(attackTimer == 0) {
            List<MobEntity> foundMobs = level.getLoadedEntitiesOfClass(MobEntity.class, new AxisAlignedBB(xo - 8, yo - 8, zo - 8, xo + 8, yo + 8, zo + 8));
            for(MobEntity foundMob : foundMobs) {
                foundMob.hurt(DamageSource.CACTUS, 4f);
                break;
            }
        } else
            attackTimer--;
    }
}
*/