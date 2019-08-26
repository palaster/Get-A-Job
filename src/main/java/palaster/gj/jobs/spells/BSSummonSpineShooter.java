package palaster.gj.jobs.spells;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.gj.entities.EntitySpineShooter;

public class BSSummonSpineShooter implements IBotanySpell {

    @Override
    public int getCost() { return 250; }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        BlockPos spawnPos = new BlockPos(pos);
        EntitySpineShooter ss = new EntitySpineShooter(worldIn);
        switch(facing)
        {
            case UP:
            {
                spawnPos = spawnPos.up();
                break;
            }
            case DOWN: {
                spawnPos = spawnPos.down();
                break;
            }
            case EAST:
            {
                spawnPos = spawnPos.east();
                break;
            }
            case WEST:
            {
                spawnPos = spawnPos.west();
                break;
            }
            case NORTH:
            {
                spawnPos = spawnPos.north();
                break;
            }
            case SOUTH:
            {
                spawnPos = spawnPos.south();
                break;
            }
            default: break;
        }
        ss.setPosition(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
        return worldIn.spawnEntity(ss) ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
    }
}
