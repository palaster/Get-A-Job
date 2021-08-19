package palaster.gj.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGProvider;
import palaster.gj.network.client.PacketUpdateRPG;
import palaster.libpal.core.helpers.PlayerHelper;
import palaster.libpal.items.SpecialModItem;

public class PinkSlipItem extends SpecialModItem {
	public PinkSlipItem(Properties properties, ResourceLocation resourceLocation) { super(properties, resourceLocation, 0); }
	
	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
		if(!world.isClientSide) {
			LazyOptional<IRPG> lazy_optional_rpg = playerEntity.getCapability(RPGProvider.RPG_CAPABILITY, null);
			final IRPG rpg = lazy_optional_rpg.orElse(null);
			if(rpg != null)
				if(rpg.getJob() != null && rpg.getJob().canLeave()) {
					rpg.setJob(null);
					PlayerHelper.sendChatMessageToPlayer(playerEntity, I18n.get("gj.job.fired"));
					PacketUpdateRPG.syncPlayerRPGCapabilitiesToClient(playerEntity);
					return ActionResult.success(ItemStack.EMPTY);
				}
		}
		return super.use(world, playerEntity, hand);
	}
}
