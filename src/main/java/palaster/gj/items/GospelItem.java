package palaster.gj.items;

import java.util.List;

import net.minecraft.client.Minecraft;
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
import palaster.gj.jobs.JobCleric;
import palaster.gj.jobs.spells.DomainSpells;
import palaster.libpal.core.helpers.NBTHelper;
import palaster.libpal.items.SpecialModItem;

public class GospelItem extends SpecialModItem {

	public static final String NBT_SELECTED_SPELL = "gj:gospel:selected_spell";

	public GospelItem(Properties properties, ResourceLocation resourceLocation) { super(properties, resourceLocation, 0); }
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		Minecraft minecraft = Minecraft.getInstance();
		if(minecraft == null || minecraft.player == null)
			return;
		if(NBTHelper.getIntegerFromItemStack(stack, NBT_SELECTED_SPELL) >= 0) {
			LazyOptional<IRPG> lazy_optional_rpg = minecraft.player.getCapability(RPGProvider.RPG_CAPABILITY, null);
			final IRPG rpg = lazy_optional_rpg.orElse(null);
			if(rpg != null && rpg.getJob() instanceof JobCleric)
				if(((JobCleric) rpg.getJob()).canCastSpell())
					tooltip.add(new StringTextComponent("Selected Spell: " + I18n.get("gj.job.cleric.spell." + NBTHelper.getIntegerFromItemStack(stack, NBT_SELECTED_SPELL))));
		}
	}

	@Override
    public ActionResultType interactLivingEntity(ItemStack itemStack, PlayerEntity playerEntity, LivingEntity livingEntity, Hand hand) {
    	if(!playerEntity.level.isClientSide) {
    		LazyOptional<IRPG> lazy_optional_rpg = playerEntity.getCapability(RPGProvider.RPG_CAPABILITY, null);
			final IRPG rpg = lazy_optional_rpg.orElse(null);
	        if(rpg != null && rpg.getJob() instanceof JobCleric)
	        	if(((JobCleric) rpg.getJob()).canCastSpell())
	        		if(DomainSpells.DOMAIN_SPELLS.get(NBTHelper.getIntegerFromItemStack(itemStack, NBT_SELECTED_SPELL)) != null) {
	        			ActionResultType actionResultType = DomainSpells.DOMAIN_SPELLS.get(NBTHelper.getIntegerFromItemStack(itemStack, NBT_SELECTED_SPELL)).interactLivingEntity(itemStack, playerEntity, livingEntity, hand);
			            if(actionResultType == ActionResultType.SUCCESS)
			            	((JobCleric) rpg.getJob()).castSpell();
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
                if(currentSelection >= DomainSpells.DOMAIN_SPELLS.size() - 1)
                    return ActionResult.success(NBTHelper.setIntegerToItemStack(playerEntity.getItemInHand(hand), NBT_SELECTED_SPELL, 0));
                else
                    return ActionResult.success(NBTHelper.setIntegerToItemStack(playerEntity.getItemInHand(hand), NBT_SELECTED_SPELL, NBTHelper.getIntegerFromItemStack(playerEntity.getItemInHand(hand), NBT_SELECTED_SPELL) + 1));
            } else {
            	LazyOptional<IRPG> lazy_optional_rpg = playerEntity.getCapability(RPGProvider.RPG_CAPABILITY, null);
    			final IRPG rpg = lazy_optional_rpg.orElse(null);
                if(rpg != null && rpg.getJob() instanceof JobCleric)
                	if(((JobCleric) rpg.getJob()).canCastSpell())
                		if(DomainSpells.DOMAIN_SPELLS.get(NBTHelper.getIntegerFromItemStack(playerEntity.getItemInHand(hand), NBT_SELECTED_SPELL)) != null) {
                			ActionResult<ItemStack> actionResult = DomainSpells.DOMAIN_SPELLS.get(NBTHelper.getIntegerFromItemStack(playerEntity.getItemInHand(hand), NBT_SELECTED_SPELL)).use(world, playerEntity, hand);
    						if(actionResult.getResult() == ActionResultType.SUCCESS)
    							((JobCleric) rpg.getJob()).castSpell();
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
            if(rpg != null && rpg.getJob() instanceof JobCleric)
            	if(((JobCleric) rpg.getJob()).canCastSpell())
            		if(DomainSpells.DOMAIN_SPELLS.get(NBTHelper.getIntegerFromItemStack(itemUseContext.getItemInHand(), NBT_SELECTED_SPELL)) != null) {
            			ActionResultType actionResultType = DomainSpells.DOMAIN_SPELLS.get(NBTHelper.getIntegerFromItemStack(itemUseContext.getItemInHand(), NBT_SELECTED_SPELL)).useOn(itemUseContext);
						if(actionResultType == ActionResultType.SUCCESS)
							((JobCleric) rpg.getJob()).castSpell();
						return actionResultType;
		            }
        }
    	return super.useOn(itemUseContext);
    }
}