package palaster.gj.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import palaster.gj.api.capabilities.IRPG;
import palaster.gj.api.capabilities.IWorld;
import palaster.gj.api.capabilities.RPGCapability.RPGCapabilityProvider;
import palaster.gj.api.capabilities.WorldCapability.WorldCapabilityProvider;
import palaster.gj.blocks.tile.TileEntityWorkshopSeal;
import palaster.gj.core.proxy.CommonProxy;
import palaster.gj.entities.jobs.JobWorkshopWitch;
import palaster.gj.libs.LibMod;
import palaster.gj.misc.WorkshopChunk;
import palaster.libpal.blocks.BlockModContainer;

public class BlockWorkshopSeal extends BlockModContainer {

	public BlockWorkshopSeal(Material materialIn) {
		super(materialIn);
		setBlockUnbreakable();
		setCreativeTab(CommonProxy.tabGJ);
		setRegistryName(LibMod.MODID, "workshopSeal");
		setUnlocalizedName("workshopSeal");
	}
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		if(!worldIn.isRemote && placer instanceof EntityPlayer) {
			IRPG rpg = RPGCapabilityProvider.get((EntityPlayer) placer);
			IWorld world = WorldCapabilityProvider.get(worldIn);
			if(rpg != null && world != null)
				if(rpg.getJob() != null && rpg.getJob() instanceof JobWorkshopWitch) {
					Chunk chunk = worldIn.getChunkFromBlockCoords(pos);
					if(chunk != null) {
						for(int x = chunk.xPosition - 1; x < (chunk.xPosition + 1); x++)
							for(int z = chunk.zPosition - 1; z < (chunk.zPosition + 1); z++) {
								WorkshopChunk wc = world.getWorkshopChunkFromCoord(x, z);
								if(wc != null)
									return Blocks.AIR.getDefaultState();
							}
						for(int x = chunk.xPosition - 1; x < (chunk.xPosition + 1); x++)
							for(int z = chunk.zPosition - 1; z < (chunk.zPosition + 1); z++)
								world.addWorkshopChunk(new WorkshopChunk(placer.getUniqueID(), x, z));
						return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
					}
				}
		}
		return Blocks.AIR.getDefaultState();
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if(!worldIn.isRemote && placer instanceof EntityPlayer) {
			TileEntityWorkshopSeal ws = (TileEntityWorkshopSeal) worldIn.getTileEntity(pos);
			if(ws != null)
				if(ws.getOwner() == null)
					ws.setOwner(placer.getUniqueID());
		}
	}

	@Override
	public TileEntity createModTileEntity(World world, IBlockState state) { return new TileEntityWorkshopSeal(); }
}
