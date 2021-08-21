package palaster.gj.items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGProvider;
import palaster.gj.api.jobs.IRPGJob;
import palaster.libpal.core.helpers.NBTHelper;
import palaster.libpal.items.SpecialModItem;

public class JobPamphletItem extends SpecialModItem {
	
	public static final String NBT_JOB_CLASS = "gj:job_pamphlet:job_class";

	public JobPamphletItem(Properties properties, ResourceLocation resourceLocation) { super(properties, resourceLocation, 0); }
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		if(!NBTHelper.getStringFromItemStack(stack, NBT_JOB_CLASS).isEmpty())
			try {
				tooltip.add(new StringTextComponent(I18n.get("gj.job.base", I18n.get(((IRPGJob) Class.forName(NBTHelper.getStringFromItemStack(stack, NBT_JOB_CLASS)).newInstance()).getJobName()))));
			} catch(Exception e) {
				e.printStackTrace();
			}
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
		if(!world.isClientSide)
			if(!NBTHelper.getStringFromItemStack(playerEntity.getItemInHand(hand), NBT_JOB_CLASS).isEmpty()) {
				LazyOptional<IRPG> lazy_optional_rpg = playerEntity.getCapability(RPGProvider.RPG_CAPABILITY, null);
				final IRPG rpg = lazy_optional_rpg.orElse(null);
				if(rpg != null && rpg.getJob() == null) {
					try {
						rpg.setJob((IRPGJob) Class.forName(NBTHelper.getStringFromItemStack(playerEntity.getItemInHand(hand), NBT_JOB_CLASS)).newInstance());
					} catch(Exception e) { }
					return ActionResult.success(ItemStack.EMPTY);
				}
			}
		return super.use(world, playerEntity, hand);
	}
}
