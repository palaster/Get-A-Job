package palaster.gj.jobs;

import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.jobs.IRPGJob;
import palaster.gj.jobs.abilities.Abilities;

public class JobBotanist implements IRPGJob {

    int flourishTimer = 1000;

    @Override
    public String getJobName() { return "gj.job.botanist"; }

    @Override
    public boolean shouldDrawExtraInformation() { return false; }
    
    @Override
    public boolean doUpdate() { return true; }

    @Override
    public void update(IRPG rpg, PlayerEntity player) {
        if(Abilities.FLOURISH.isAvailable(rpg)) {
            if(flourishTimer <= 0) {
                for (double x = player.getX() - 6; x < player.getX() + 6; x++)
                    for (double y = player.getY() - 6; y < player.getY() + 6; y++)
                        for (double z = player.getZ() - 6; z < player.getZ() + 6; z++) {
                            BlockState state = player.level.getBlockState(new BlockPos(x, y, z));
                            if (state != null && state.getMaterial() != Material.AIR)
                                if (state.getBlock() instanceof IGrowable) {
                                	IGrowable growable = (IGrowable) state.getBlock();
                                    if (growable != null && player.level instanceof ServerWorld)
                                        if(growable.isValidBonemealTarget(player.level, new BlockPos(x, y, z), state, player.level.isClientSide))
                                            growable.performBonemeal((ServerWorld) player.level, player.level.random, new BlockPos(x, y, z), state);
                                }
                        }
                flourishTimer = 1000;
            } else
                flourishTimer--;
        }
    }
}
