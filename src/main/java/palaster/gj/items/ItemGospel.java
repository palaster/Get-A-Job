package palaster.gj.items;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.gj.api.capabilities.IRPG;
import palaster.gj.api.capabilities.RPGCapability.RPGCapabilityProvider;
import palaster.gj.jobs.JobCleric;
import palaster.gj.jobs.spells.DomainSpells;
import palaster.gj.libs.LibMod;
import palaster.libpal.core.helpers.NBTHelper;
import palaster.libpal.items.ItemModSpecial;

public class ItemGospel extends ItemModSpecial {

	public static final String TAG_INT_SS = "gj:GospelSelectedSpell";

	public ItemGospel() {
		super();
		setRegistryName(LibMod.MODID, "gospel");
		setUnlocalizedName("gospel");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(Minecraft.getMinecraft() == null || Minecraft.getMinecraft().player == null)
			return;
		if(NBTHelper.getIntegerFromItemStack(stack, TAG_INT_SS) >= 0) {
			IRPG rpg = RPGCapabilityProvider.get(Minecraft.getMinecraft().player);
			if(rpg != null && rpg.getJob() != null && rpg.getJob() instanceof JobCleric)
				if(((JobCleric) rpg.getJob()).canCastSpell())
					tooltip.add("Selected Spell: " + I18n.format("gj.job.cleric.spell." + NBTHelper.getIntegerFromItemStack(stack, TAG_INT_SS)));
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(!worldIn.isRemote) {
			IRPG rpg = RPGCapabilityProvider.get(playerIn);
			if(rpg != null && rpg.getJob() != null && rpg.getJob() instanceof JobCleric) {
				if(playerIn.isSneaking()) {
					int currentSelection = NBTHelper.getIntegerFromItemStack(playerIn.getHeldItem(handIn), TAG_INT_SS);
					if(currentSelection >= DomainSpells.DOMAIN_SPELLS.size() - 1)
						return ActionResult.newResult(EnumActionResult.SUCCESS, NBTHelper.setIntegerToItemStack(playerIn.getHeldItem(handIn), TAG_INT_SS, 0));
					else
						return ActionResult.newResult(EnumActionResult.SUCCESS, NBTHelper.setIntegerToItemStack(playerIn.getHeldItem(handIn), TAG_INT_SS, NBTHelper.getIntegerFromItemStack(playerIn.getHeldItem(handIn), TAG_INT_SS) + 1));
				} else if(((JobCleric) rpg.getJob()).canCastSpell()) {
					if(DomainSpells.DOMAIN_SPELLS.get(NBTHelper.getIntegerFromItemStack(playerIn.getHeldItem(handIn), TAG_INT_SS)) != null) {
						ActionResult<ItemStack> temp = DomainSpells.DOMAIN_SPELLS.get(NBTHelper.getIntegerFromItemStack(playerIn.getHeldItem(handIn), TAG_INT_SS)).onItemRightClick(worldIn, playerIn, handIn);
						if(temp.getType() == EnumActionResult.SUCCESS)
							((JobCleric) rpg.getJob()).castSpell();
						return temp;
					}
				}
			}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			IRPG rpg = RPGCapabilityProvider.get(player);
			if(rpg != null && rpg.getJob() != null && rpg.getJob() instanceof JobCleric)
				if(((JobCleric) rpg.getJob()).canCastSpell()) {
					if(DomainSpells.DOMAIN_SPELLS.get(NBTHelper.getIntegerFromItemStack(player.getHeldItem(hand), TAG_INT_SS)) != null) {
						EnumActionResult temp = DomainSpells.DOMAIN_SPELLS.get(NBTHelper.getIntegerFromItemStack(player.getHeldItem(hand), TAG_INT_SS)).onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
						if(temp == EnumActionResult.SUCCESS)
							((JobCleric) rpg.getJob()).castSpell();
						return temp;
					}
				}
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
		if(!playerIn.world.isRemote) {
			IRPG rpg = RPGCapabilityProvider.get(playerIn);
			if(rpg != null && rpg.getJob() != null && rpg.getJob() instanceof JobCleric)
				if(((JobCleric) rpg.getJob()).canCastSpell()) {
					if(DomainSpells.DOMAIN_SPELLS.get(NBTHelper.getIntegerFromItemStack(playerIn.getHeldItem(hand), TAG_INT_SS)) != null) {
						boolean temp = DomainSpells.DOMAIN_SPELLS.get(NBTHelper.getIntegerFromItemStack(stack, TAG_INT_SS)).itemInteractionForEntity(stack, playerIn, target, hand);
						if(temp)
							((JobCleric) rpg.getJob()).castSpell();
						return temp;
					}
				}
		}
		return super.itemInteractionForEntity(stack, playerIn, target, hand);
	}
}