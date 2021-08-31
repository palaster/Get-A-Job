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
import palaster.gj.jobs.JobBotanist;
import palaster.gj.jobs.spells.BotanySpells;
import palaster.gj.jobs.spells.IBotanySpell;
import palaster.libpal.core.helpers.NBTHelper;
import palaster.libpal.items.SpecialModItem;

public class HerbSackItem extends SpecialModItem {

    public static final String NBT_SELECTED_SPELL = "gj:herb_sack:selected_spell";

    public HerbSackItem(Properties properties, ResourceLocation resourceLocation) { super(properties, resourceLocation, 0); }
    
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		if(NBTHelper.getIntegerFromItemStack(stack, NBT_SELECTED_SPELL) >= 0)
			tooltip.add(new StringTextComponent("Selected Spell: " + I18n.get("gj.job.botanist.spell." + NBTHelper.getIntegerFromItemStack(stack, NBT_SELECTED_SPELL))));
	}
    
    @Override
    public ActionResultType interactLivingEntity(ItemStack itemStack, PlayerEntity playerEntity, LivingEntity livingEntity, Hand hand) {
    	if(!playerEntity.level.isClientSide) {
    		LazyOptional<IRPG> lazy_optional_rpg = playerEntity.getCapability(RPGProvider.RPG_CAPABILITY, null);
			final IRPG rpg = lazy_optional_rpg.orElse(null);
	        if(rpg != null && rpg.getJob() instanceof JobBotanist) {
	            IBotanySpell IBS = BotanySpells.BOTANY_SPELLS.get(NBTHelper.getIntegerFromItemStack(itemStack, NBT_SELECTED_SPELL));
	            if(rpg.getMagick() < IBS.getCost())
	                return ActionResultType.FAIL;
	            ActionResultType actionResultType = IBS.interactLivingEntity(itemStack, playerEntity, livingEntity, hand);
	            if(actionResultType == ActionResultType.SUCCESS)
	                rpg.setMagick(rpg.getMagick() - IBS.getCost());
	            return actionResultType;
	        }
    	}
    	return super.interactLivingEntity(itemStack, playerEntity, livingEntity, hand);
    }
    
    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
    	if(!world.isClientSide) {
            if(playerEntity.isShiftKeyDown()) {
                int currentSelection = NBTHelper.getIntegerFromItemStack(playerEntity.getItemInHand(hand), NBT_SELECTED_SPELL);
                if(currentSelection >= BotanySpells.BOTANY_SPELLS.size() - 1)
                    return ActionResult.success(NBTHelper.setIntegerToItemStack(playerEntity.getItemInHand(hand), NBT_SELECTED_SPELL, 0));
                else
                    return ActionResult.success(NBTHelper.setIntegerToItemStack(playerEntity.getItemInHand(hand), NBT_SELECTED_SPELL, NBTHelper.getIntegerFromItemStack(playerEntity.getItemInHand(hand), NBT_SELECTED_SPELL) + 1));
            } else {
            	LazyOptional<IRPG> lazy_optional_rpg = playerEntity.getCapability(RPGProvider.RPG_CAPABILITY, null);
    			final IRPG rpg = lazy_optional_rpg.orElse(null);
                if(rpg != null && rpg.getJob() instanceof JobBotanist) {
                    IBotanySpell IBS = BotanySpells.BOTANY_SPELLS.get(NBTHelper.getIntegerFromItemStack(playerEntity.getItemInHand(hand), NBT_SELECTED_SPELL));
                    if(rpg.getMagick() < IBS.getCost())
                        return ActionResult.fail(playerEntity.getItemInHand(hand));
                    ActionResult<ItemStack> actionResult = IBS.use(world, playerEntity, hand);
                    if(actionResult.getResult() == ActionResultType.SUCCESS)
                        rpg.setMagick(rpg.getMagick() - IBS.getCost());
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
            if(rpg != null && rpg.getJob() instanceof JobBotanist) {
                IBotanySpell IBS = BotanySpells.BOTANY_SPELLS.get(NBTHelper.getIntegerFromItemStack(itemUseContext.getItemInHand(), NBT_SELECTED_SPELL));
                if(rpg.getMagick() < IBS.getCost())
                    return ActionResultType.FAIL;
                ActionResultType actionResultType = IBS.useOn(itemUseContext);
                if(actionResultType == ActionResultType.SUCCESS)
                    rpg.setMagick(rpg.getMagick() - IBS.getCost());
                return actionResultType;
            }
        }
    	return super.useOn(itemUseContext);
    }
}
