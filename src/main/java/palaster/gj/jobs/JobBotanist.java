package palaster.gj.jobs;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import palaster.gj.api.jobs.IRPGJob;

public class JobBotanist implements IRPGJob {

    int timer = 1000;

    @Override
    public String getJobName() { return "gj.job.botanist"; }

    @Override
    public boolean doUpdate() { return true; }

    @Override
    public void update(EntityPlayer player) {
        if(timer <= 0) {
            for (int x = player.getPosition().getX() - 6; x < player.getPosition().getX() + 6; x++)
                for (int y = player.getPosition().getY() - 6; y < player.getPosition().getY() + 6; y++)
                    for (int z = player.getPosition().getZ() - 6; z < player.getPosition().getZ() + 6; z++) {
                        IBlockState IBS = player.world.getBlockState(new BlockPos(x, y, z));
                        if (IBS != null && IBS.getMaterial() != Material.AIR)
                            if (IBS.getBlock() instanceof BlockCrops) {
                                BlockCrops Crops = (BlockCrops) IBS.getBlock();
                                if (Crops != null)
                                    if(Crops.canUseBonemeal(player.world, player.world.rand, new BlockPos(x, y, z), IBS))
                                        Crops.grow(player.world, player.world.rand, new BlockPos(x, y, z), IBS);
                            }
                    }
            timer = 1000;
        } else
            timer--;
    }
}
