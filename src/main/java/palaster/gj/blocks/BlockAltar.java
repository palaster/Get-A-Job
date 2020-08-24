package palaster.gj.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.gj.api.capabilities.IRPG;
import palaster.gj.api.capabilities.RPGCapability.RPGCapabilityProvider;
import palaster.gj.core.proxy.CommonProxy;
import palaster.gj.jobs.JobCleric;
import palaster.gj.libs.LibMod;
import palaster.libpal.blocks.BlockMod;

public class BlockAltar extends BlockMod {

	public BlockAltar(Material materialIn) {
		super(materialIn);
		setRegistryName(LibMod.MODID, "altar");
		setUnlocalizedName("altar");
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			IRPG rpg = RPGCapabilityProvider.get(playerIn);
			if(rpg != null && rpg.getJob() != null && rpg.getJob() instanceof JobCleric)
				((JobCleric) rpg.getJob()).resetSpellSlots(rpg);
			CommonProxy.syncPlayerRPGCapabilitiesToClient(playerIn);
		}
		return true;
	}
}