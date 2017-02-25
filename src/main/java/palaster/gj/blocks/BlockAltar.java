package palaster.gj.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.gj.jobs.JobCleric.EnumDomain;
import palaster.gj.libs.LibMod;
import palaster.libpal.api.ICustomItemBlock;
import palaster.libpal.blocks.BlockMod;

public class BlockAltar extends BlockMod implements ICustomItemBlock {

	public static final PropertyEnum<EnumDomain> DOMAIN_TYPE = PropertyEnum.create("domain_type", EnumDomain.class);

	public BlockAltar(Material materialIn) {
		super(materialIn);
		setDefaultState(blockState.getBaseState().withProperty(DOMAIN_TYPE, EnumDomain.NONE));
		setRegistryName(LibMod.MODID, "altar");
		setUnlocalizedName("altar");
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) { return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(world.getBlockState(pos))); }
	
	@Override
	protected BlockStateContainer createBlockState() { return new BlockStateContainer(this, new IProperty[] { DOMAIN_TYPE }); }
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumDomain domain = null;
		switch(meta) {
			case 0: {
				domain = EnumDomain.NONE;
				break;
			}
			case 1: {
				domain = EnumDomain.CREATION;
				break;
			}
			case 2: {
				domain = EnumDomain.COMMUNITY;
				break;
			}
		}
		return getDefaultState().withProperty(DOMAIN_TYPE, domain);
	}

	@Override
	public int getMetaFromState(IBlockState state) { return state.getValue(DOMAIN_TYPE).getID(); }

	@Override
	public int damageDropped(IBlockState state) { return getMetaFromState(state); }

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list) {
		for(int i = 0; i < 3; i++)
			list.add(new ItemStack(itemIn, 1, i));
	}

	@Override
	public Class<? extends ItemBlock> getCustomItemBlock() {
		return AltarItemBlock.class;
	}

	public static class AltarItemBlock extends ItemBlock {

		public AltarItemBlock(Block block) {
			super(block);
			setHasSubtypes(true);
			setMaxDamage(0);
		}
		
		@Override
		public int getMetadata(int damage) { return damage; }
		
		@Override
		public String getUnlocalizedName(ItemStack stack) {
			EnumDomain domain = null;
			switch(stack.getItemDamage()) {
				case 0: {
					domain = EnumDomain.NONE;
					break;
				}
				case 1: {
					domain = EnumDomain.CREATION;
					break;
				}
				case 2: {
					domain = EnumDomain.COMMUNITY;
					break;
				}
			}
			return super.getUnlocalizedName(stack) + "." + domain.getName();
		}
	}
}