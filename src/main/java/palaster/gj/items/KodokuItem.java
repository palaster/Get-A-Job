package palaster.gj.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGProvider;
import palaster.gj.jobs.JobBloodSorcerer;

public class KodokuItem extends Item {
    
    public KodokuItem(Properties properties) { super(properties); }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) { return UseAnim.EAT; }

    @Override
    public int getUseDuration(ItemStack p_41454_) { return 32; }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        if(!level.isClientSide && livingEntity instanceof Player) {
            LazyOptional<IRPG> lazy_optional_rpg = livingEntity.getCapability(RPGProvider.RPG_CAPABILITY, null);
		    IRPG rpg = lazy_optional_rpg.orElse(null);
		    if(rpg != null && rpg.getJob() instanceof JobBloodSorcerer) {
                int newBloodRegen = ((JobBloodSorcerer) rpg.getJob()).getBloodRegen() + 1;
                ((JobBloodSorcerer) rpg.getJob()).setBloodMax((Player) livingEntity, 2000 * (newBloodRegen + 1));
                ((JobBloodSorcerer) rpg.getJob()).setBloodRegen((Player) livingEntity, newBloodRegen);
                return ItemStack.EMPTY;
            }
        }
        return super.finishUsingItem(itemStack, level, livingEntity);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
    	player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }
}
