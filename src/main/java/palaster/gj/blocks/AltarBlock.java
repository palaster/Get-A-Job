package palaster.gj.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.util.LazyOptional;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGProvider;
import palaster.gj.jobs.JobCleric;
import palaster.gj.network.client.PacketUpdateRPG;

public class AltarBlock extends Block {
	
	public AltarBlock(Properties properties) { super(properties); }

	@Override
	public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
		if(!level.isClientSide) {
			LazyOptional<IRPG> lazy_optional_rpg = player.getCapability(RPGProvider.RPG_CAPABILITY, null);
	    	IRPG rpg = lazy_optional_rpg.orElse(null);
			if(rpg != null && rpg.getJob() instanceof JobCleric) {
				((JobCleric) rpg.getJob()).resetSpellSlots(rpg);
				player.sendSystemMessage(Component.translatable("gj.job.cleric.spell_slots_reset"));
				PacketUpdateRPG.syncPlayerRPGCapabilitiesToClient(player);
			}
		}
		return InteractionResult.SUCCESS;
	}
}