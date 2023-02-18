package palaster.gj.items;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGProvider;
import palaster.gj.api.jobs.IRPGJob;
import palaster.gj.core.helpers.NBTHelper;

public class JobApplicationItem extends Item {
	
	public static final String NBT_JOB_CLASS = "gj:job_application:job_class";

	public JobApplicationItem(Properties properties) { super(properties); }
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		if(!NBTHelper.getStringFromItemStack(stack, NBT_JOB_CLASS).isEmpty())
			try {
				tooltip.add(Component.literal(I18n.get("gj.job.base", I18n.get(((IRPGJob) Class.forName(NBTHelper.getStringFromItemStack(stack, NBT_JOB_CLASS)).getConstructor().newInstance()).getJobName()))));
			} catch(Exception e) {
				e.printStackTrace();
			}
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		if(!level.isClientSide)
			if(!NBTHelper.getStringFromItemStack(player.getItemInHand(hand), NBT_JOB_CLASS).isEmpty()) {
				LazyOptional<IRPG> lazy_optional_rpg = player.getCapability(RPGProvider.RPG_CAPABILITY, null);
				final IRPG rpg = lazy_optional_rpg.orElse(null);
				if(rpg != null && rpg.getJob() == null) {
					try {
						rpg.setJob((IRPGJob) Class.forName(NBTHelper.getStringFromItemStack(player.getItemInHand(hand), NBT_JOB_CLASS)).getConstructor().newInstance());
					} catch(Exception e) { }
					player.sendSystemMessage(Component.translatable("gj.job.hired"));
					return InteractionResultHolder.success(ItemStack.EMPTY);
				}
			}
		return super.use(level, player, hand);
	}
}
