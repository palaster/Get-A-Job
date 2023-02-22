package palaster.gj.jobs.spells.blood;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import palaster.gj.GetAJob;

public class BlsMassDecay implements IBloodSpell {

    private static final int DECAY_RADIUS = 6;
    private static final float DAMAGE = 12.0f;

    @Override
    public int getBloodCost() { return 500; }

    @Override
    public int getUseDuration(ItemStack itemStack) { return 32; }
    
    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        BlockPos blockPos = livingEntity.getOnPos();
        List<LivingEntity> foundLivingEntities = level.getEntitiesOfClass(LivingEntity.class, new AABB(blockPos.offset(DECAY_RADIUS, DECAY_RADIUS, DECAY_RADIUS), blockPos.offset(-DECAY_RADIUS, -DECAY_RADIUS, -DECAY_RADIUS)));
        for(LivingEntity foundLivingEntity: foundLivingEntities) {
            if(foundLivingEntity == livingEntity || foundLivingEntity.getMobType() == MobType.UNDEAD)
                continue;
            foundLivingEntity.addEffect(new MobEffectInstance(GetAJob.DECAY.get(), 100));
            foundLivingEntity.hurt(DamageSource.WITHER, DAMAGE);
        }
        applyCost((Player) livingEntity);
        return itemStack;
    }
}
