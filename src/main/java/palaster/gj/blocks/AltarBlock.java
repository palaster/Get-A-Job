package palaster.gj.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGProvider;
import palaster.gj.jobs.JobCleric;
import palaster.gj.network.client.PacketUpdateRPG;
import palaster.libpal.blocks.ModBlock;

public class AltarBlock extends ModBlock {
	
	public AltarBlock(Properties properties, ResourceLocation resourceLocation) { super(properties, resourceLocation); }

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult blockRayTraceResult) {
		if(!world.isClientSide) {
			LazyOptional<IRPG> lazy_optional_rpg = player.getCapability(RPGProvider.RPG_CAPABILITY, null);
	    	IRPG rpg = lazy_optional_rpg.orElse(null);
			if(rpg != null && rpg.getJob() != null && rpg.getJob() instanceof JobCleric)
				((JobCleric) rpg.getJob()).resetSpellSlots(rpg);
			PacketUpdateRPG.syncPlayerRPGCapabilitiesToClient(player);
		}
		return ActionResultType.SUCCESS;
	}
}