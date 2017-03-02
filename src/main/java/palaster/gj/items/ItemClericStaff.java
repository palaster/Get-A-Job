package palaster.gj.items;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.gj.api.capabilities.IRPG;
import palaster.gj.api.capabilities.RPGCapability.RPGCapabilityProvider;
import palaster.gj.blocks.BlockAltar;
import palaster.gj.jobs.JobCleric;
import palaster.gj.jobs.spells.DomainSpells;
import palaster.gj.libs.LibMod;
import palaster.libpal.items.ItemModSpecial;

public class ItemClericStaff extends ItemModSpecial {

	public ItemClericStaff() {
		super();
		setRegistryName(LibMod.MODID, "clericStaff");
		setUnlocalizedName("clericStaff");
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(!worldIn.isRemote) {
			IRPG rpg = RPGCapabilityProvider.get(playerIn);
			if(rpg != null && rpg.getJob() != null && rpg.getJob() instanceof JobCleric)
				if(((JobCleric) rpg.getJob()).getAltar() != null) {
					IBlockState bs = worldIn.getBlockState(((JobCleric) rpg.getJob()).getAltar());
					if(bs != null && bs.getBlock() instanceof BlockAltar)
						if(bs.getValue(BlockAltar.DOMAIN_TYPE) != null)
							for(int i = 0; i < DomainSpells.DOMAIN_SPELLS.size(); i++)
								if(DomainSpells.DOMAIN_SPELLS.get(i) != null && DomainSpells.DOMAIN_SPELLS.get(i).getDomain() == bs.getValue(BlockAltar.DOMAIN_TYPE))
									return DomainSpells.DOMAIN_SPELLS.get(i).onItemRightClick(worldIn, playerIn, handIn);
				}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			IRPG rpg = RPGCapabilityProvider.get(player);
			if(rpg != null && rpg.getJob() != null && rpg.getJob() instanceof JobCleric)
				if(((JobCleric) rpg.getJob()).getAltar() != null) {
					IBlockState bs = worldIn.getBlockState(((JobCleric) rpg.getJob()).getAltar());
					if(bs != null && bs.getBlock() instanceof BlockAltar)
						if(bs.getValue(BlockAltar.DOMAIN_TYPE) != null)
							for(int i = 0; i < DomainSpells.DOMAIN_SPELLS.size(); i++)
								if(DomainSpells.DOMAIN_SPELLS.get(i) != null && DomainSpells.DOMAIN_SPELLS.get(i).getDomain() == bs.getValue(BlockAltar.DOMAIN_TYPE))
									return DomainSpells.DOMAIN_SPELLS.get(i).onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
				}
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
		if(!playerIn.world.isRemote) {
			IRPG rpg = RPGCapabilityProvider.get(playerIn);
			if(((JobCleric) rpg.getJob()).getAltar() != null) {
				IBlockState bs = playerIn.world.getBlockState(((JobCleric) rpg.getJob()).getAltar());
				if(bs != null && bs.getBlock() instanceof BlockAltar)
					if(bs.getValue(BlockAltar.DOMAIN_TYPE) != null)
						for(int i = 0; i < DomainSpells.DOMAIN_SPELLS.size(); i++)
							if(DomainSpells.DOMAIN_SPELLS.get(i) != null && DomainSpells.DOMAIN_SPELLS.get(i).getDomain() == bs.getValue(BlockAltar.DOMAIN_TYPE))
								return DomainSpells.DOMAIN_SPELLS.get(i).itemInteractionForEntity(stack, playerIn, target, hand);
			}
		}
		return super.itemInteractionForEntity(stack, playerIn, target, hand);
	}
}