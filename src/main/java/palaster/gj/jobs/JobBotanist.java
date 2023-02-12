package palaster.gj.jobs;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
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
    public void update(IRPG rpg, Player player) {
        if(rpg == null || player == null)
            return;
        if(Abilities.FLOURISH.isAvailable(rpg)) {
            if(flourishTimer <= 0) {
                for (double x = player.getX() - 6; x < player.getX() + 6; x++)
                    for (double y = player.getY() - 6; y < player.getY() + 6; y++)
                        for (double z = player.getZ() - 6; z < player.getZ() + 6; z++) {
                            BlockState state = player.level.getBlockState(new BlockPos(x, y, z));
                            if (state != null && state.getMaterial() != Material.AIR)
                                if (state.getBlock() instanceof BonemealableBlock) {
                                	BonemealableBlock bonemealableBlock = (BonemealableBlock) state.getBlock();
                                    if (bonemealableBlock != null && player.level instanceof ServerLevel)
                                        if(bonemealableBlock.isValidBonemealTarget(player.level, new BlockPos(x, y, z), state, player.level.isClientSide))
                                            bonemealableBlock.performBonemeal((ServerLevel) player.level, player.level.random, new BlockPos(x, y, z), state);
                                }
                        }
                flourishTimer = 1000;
            } else
                flourishTimer--;
        }
    }
}
