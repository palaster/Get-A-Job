package palaster.gj.items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.gj.api.capabilities.IRPG;
import palaster.gj.api.capabilities.RPGCapability.RPGCapabilityProvider;
import palaster.gj.core.proxy.CommonProxy;
import palaster.gj.entities.jobs.RPGJobBase;
import palaster.gj.libs.LibMod;
import palaster.libpal.core.helpers.NBTHelper;
import palaster.libpal.items.ItemModSpecial;

public class ItemJobPamphlet extends ItemModSpecial {
	
	public static final String TAG_STRING_JOB_CLASS = "JobPamphletJobClass";

	public ItemJobPamphlet() {
		super();
		setCreativeTab(CommonProxy.tabGJ);
		setRegistryName(LibMod.MODID, "jobPamphlet");
		setUnlocalizedName("jobPamphlet");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if(!NBTHelper.getStringFromItemStack(stack, TAG_STRING_JOB_CLASS).isEmpty())
			try {
				tooltip.add(I18n.format("gj.job.base") + ": " + I18n.format(((RPGJobBase) Class.forName(NBTHelper.getStringFromItemStack(stack, TAG_STRING_JOB_CLASS)).newInstance()).toString()));
			} catch(Exception e) {
				e.printStackTrace();
			}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if(!worldIn.isRemote)
			if(!playerIn.getHeldItem(hand).isEmpty())
				if(!NBTHelper.getStringFromItemStack(playerIn.getHeldItem(hand), TAG_STRING_JOB_CLASS).isEmpty()) {
					IRPG rpg = RPGCapabilityProvider.get(playerIn);
					if(rpg != null && rpg.getJob() == null) {
						try {
							rpg.setJob(playerIn, (RPGJobBase) Class.forName(NBTHelper.getStringFromItemStack(playerIn.getHeldItem(hand), TAG_STRING_JOB_CLASS)).newInstance());
						} catch(Exception e) {
							e.printStackTrace();
						}
						return ActionResult.newResult(EnumActionResult.SUCCESS, ItemStack.EMPTY);
					}
				}
		return super.onItemRightClick(worldIn, playerIn, hand);
	}
}
