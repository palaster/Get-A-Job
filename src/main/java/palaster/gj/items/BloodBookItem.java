package palaster.gj.items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
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
import palaster.gj.jobs.JobBloodSorcerer;
import palaster.gj.jobs.spells.BloodSpells;
import palaster.gj.jobs.spells.IBloodSpell;
import palaster.libpal.core.helpers.NBTHelper;
import palaster.libpal.items.SpecialModItem;

public class BloodBookItem extends SpecialModItem {

	public static final String TAG_INT_SS = "gj:BloodBookSelectedSpell";

	public BloodBookItem(Properties properties, ResourceLocation resourceLocation) { super(properties, resourceLocation, 0); }
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		if(NBTHelper.getIntegerFromItemStack(stack, TAG_INT_SS) >= 0)
			tooltip.add(new StringTextComponent("Selected Spell: " + I18n.get("gj.job.bloodSorcerer.spell." + NBTHelper.getIntegerFromItemStack(stack, TAG_INT_SS))));
	}
	
	@Override
    public ActionResultType interactLivingEntity(ItemStack itemStack, PlayerEntity playerEntity, LivingEntity livingEntity, Hand hand) {
    	if(!playerEntity.level.isClientSide) {
    		LazyOptional<IRPG> lazy_optional_rpg = playerEntity.getCapability(RPGProvider.RPG_CAPABILITY, null);
			final IRPG rpg = lazy_optional_rpg.orElse(null);
	        if(rpg != null && rpg.getJob() != null && rpg.getJob() instanceof JobBloodSorcerer) {
	            IBloodSpell IBS = BloodSpells.BLOOD_SPELLS.get(NBTHelper.getIntegerFromItemStack(itemStack, TAG_INT_SS));
	            ActionResultType actionResultType = IBS.interactLivingEntity(itemStack, playerEntity, livingEntity, hand);
	            if(actionResultType == ActionResultType.SUCCESS)
	            	((JobBloodSorcerer) rpg.getJob()).setBloodCurrent(playerEntity, ((JobBloodSorcerer) rpg.getJob()).getBloodCurrent() - IBS.getBloodCost());
	            return actionResultType;
	        }
    	}
    	return super.interactLivingEntity(itemStack, playerEntity, livingEntity, hand);
    }
    
    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
    	if(!world.isClientSide) {
            if(playerEntity.isShiftKeyDown()) {
                int currentSelection = NBTHelper.getIntegerFromItemStack(playerEntity.getItemInHand(hand), TAG_INT_SS);
                if(currentSelection >= BloodSpells.BLOOD_SPELLS.size() - 1)
                    return ActionResult.success(NBTHelper.setIntegerToItemStack(playerEntity.getItemInHand(hand), TAG_INT_SS, 0));
                else
                    return ActionResult.success(NBTHelper.setIntegerToItemStack(playerEntity.getItemInHand(hand), TAG_INT_SS, NBTHelper.getIntegerFromItemStack(playerEntity.getItemInHand(hand), TAG_INT_SS) + 1));
            } else {
            	LazyOptional<IRPG> lazy_optional_rpg = playerEntity.getCapability(RPGProvider.RPG_CAPABILITY, null);
    			final IRPG rpg = lazy_optional_rpg.orElse(null);
                if(rpg != null && rpg.getJob() != null && rpg.getJob() instanceof JobBloodSorcerer) {
                    IBloodSpell IBS = BloodSpells.BLOOD_SPELLS.get(NBTHelper.getIntegerFromItemStack(playerEntity.getItemInHand(hand), TAG_INT_SS));
                    ActionResult<ItemStack> actionResult = IBS.use(world, playerEntity, hand);
                    if(actionResult.getResult() == ActionResultType.SUCCESS)
                    	((JobBloodSorcerer) rpg.getJob()).setBloodCurrent(playerEntity, ((JobBloodSorcerer) rpg.getJob()).getBloodCurrent() - IBS.getBloodCost());
                    return actionResult;
                }
            }
        }
    	return super.use(world, playerEntity, hand);
    }
    
    @Override
    public ActionResultType useOn(ItemUseContext itemUseContext) {
    	if(!itemUseContext.getLevel().isClientSide) {
    		LazyOptional<IRPG> lazy_optional_rpg = itemUseContext.getPlayer().getCapability(RPGProvider.RPG_CAPABILITY, null);
			final IRPG rpg = lazy_optional_rpg.orElse(null);
            if(rpg != null && rpg.getJob() != null && rpg.getJob() instanceof JobBloodSorcerer) {
                IBloodSpell IBS = BloodSpells.BLOOD_SPELLS.get(NBTHelper.getIntegerFromItemStack(itemUseContext.getItemInHand(), TAG_INT_SS));
                ActionResultType actionResultType = IBS.useOn(itemUseContext);
                if(actionResultType == ActionResultType.SUCCESS)
                	((JobBloodSorcerer) rpg.getJob()).setBloodCurrent(itemUseContext.getPlayer(), ((JobBloodSorcerer) rpg.getJob()).getBloodCurrent() - IBS.getBloodCost());
                return actionResultType;
            }
        }
    	return super.useOn(itemUseContext);
    }
}
