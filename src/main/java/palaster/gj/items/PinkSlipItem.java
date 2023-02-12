package palaster.gj.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGDefault;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGProvider;
import palaster.gj.network.client.PacketUpdateRPG;

public class PinkSlipItem extends Item {
	public PinkSlipItem(Properties properties) { super(properties); }
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		if(!level.isClientSide) {
			LazyOptional<IRPG> lazy_optional_rpg = player.getCapability(RPGProvider.RPG_CAPABILITY, null);
			final IRPG rpg = lazy_optional_rpg.orElse(null);
			if(rpg != null)
				if(rpg.getJob() != null && rpg.getJob().canLeave()) {
					RPGDefault.jobChange(player, rpg, null);
					player.sendSystemMessage(Component.translatable("gj.job.fired"));
					PacketUpdateRPG.syncPlayerRPGCapabilitiesToClient(player);
					return InteractionResultHolder.success(ItemStack.EMPTY);
				}
		}
		return super.use(level, player, hand);
	}
}
