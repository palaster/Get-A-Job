package palaster.gj.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGProvider;
import palaster.gj.jobs.JobBloodSorcerer;
import palaster.libpal.items.SpecialModItem;

public class HandItem extends SpecialModItem {

	public HandItem(Properties properties, ResourceLocation resourceLocation) { super(properties, resourceLocation, 0); }	
	
	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
		if(!world.isClientSide)
			if(hand == Hand.OFF_HAND) {
				ItemStack mainHandItemStack = playerEntity.getItemInHand(Hand.MAIN_HAND);
				if(!mainHandItemStack.isEmpty() && mainHandItemStack.getItem() instanceof SwordItem) {
					LazyOptional<IRPG> lazy_optional_rpg = playerEntity.getCapability(RPGProvider.RPG_CAPABILITY, null);
					final IRPG rpg = lazy_optional_rpg.orElse(null);
					if(rpg != null && rpg.getJob() != null && rpg.getJob() instanceof JobBloodSorcerer) {
						playerEntity.hurt(DamageSource.MAGIC, 1F);
						((JobBloodSorcerer) rpg.getJob()).addBlood(playerEntity, 10);
						return ActionResult.success(playerEntity.getItemInHand(hand));
					}
				}
			}
		return super.use(world, playerEntity, hand);
	}
}
