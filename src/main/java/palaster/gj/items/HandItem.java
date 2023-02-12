package palaster.gj.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGProvider;
import palaster.gj.jobs.JobBloodSorcerer;

public class HandItem extends Item {

	public HandItem(Properties properties) { super(properties); }
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		if(!level.isClientSide)
			if(hand == InteractionHand.OFF_HAND) {
				ItemStack mainHandItemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
				if(!mainHandItemStack.isEmpty() && mainHandItemStack.getItem() instanceof SwordItem) {
					LazyOptional<IRPG> lazy_optional_rpg = player.getCapability(RPGProvider.RPG_CAPABILITY, null);
					final IRPG rpg = lazy_optional_rpg.orElse(null);
					if(rpg != null && rpg.getJob() != null && rpg.getJob() instanceof JobBloodSorcerer) {
						player.hurt(DamageSource.MAGIC, 1F);
						((JobBloodSorcerer) rpg.getJob()).addBlood(player, 10);
						return InteractionResultHolder.success(player.getItemInHand(hand));
					}
				}
			}
		return super.use(level, player, hand);
	}
}
