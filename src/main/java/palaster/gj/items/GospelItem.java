package palaster.gj.items;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGProvider;
import palaster.gj.core.helpers.NBTHelper;
import palaster.gj.jobs.JobCleric;
import palaster.gj.jobs.spells.DomainSpells;

public class GospelItem extends Item {

	public static final String NBT_SELECTED_SPELL = "gj:gospel:selected_spell";

	public GospelItem(Properties properties) { super(properties); }
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		Minecraft minecraft = Minecraft.getInstance();
		if(minecraft == null || minecraft.player == null)
			return;
		if(NBTHelper.getIntegerFromItemStack(stack, NBT_SELECTED_SPELL) >= 0) {
			LazyOptional<IRPG> lazy_optional_rpg = minecraft.player.getCapability(RPGProvider.RPG_CAPABILITY, null);
			final IRPG rpg = lazy_optional_rpg.orElse(null);
			if(rpg != null && rpg.getJob() instanceof JobCleric)
				if(((JobCleric) rpg.getJob()).canCastSpell())
					tooltip.add(Component.literal("Selected Spell: " + I18n.get("gj.job.cleric.spell." + NBTHelper.getIntegerFromItemStack(stack, NBT_SELECTED_SPELL))));
		}
	}

	@Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player playerEntity, LivingEntity livingEntity, InteractionHand hand) {
    	if(!playerEntity.level.isClientSide) {
    		LazyOptional<IRPG> lazy_optional_rpg = playerEntity.getCapability(RPGProvider.RPG_CAPABILITY, null);
			final IRPG rpg = lazy_optional_rpg.orElse(null);
	        if(rpg != null && rpg.getJob() instanceof JobCleric)
	        	if(((JobCleric) rpg.getJob()).canCastSpell())
	        		if(DomainSpells.DOMAIN_SPELLS.get(NBTHelper.getIntegerFromItemStack(itemStack, NBT_SELECTED_SPELL)) != null) {
	        			InteractionResult interactionResult = DomainSpells.DOMAIN_SPELLS.get(NBTHelper.getIntegerFromItemStack(itemStack, NBT_SELECTED_SPELL)).interactLivingEntity(itemStack, playerEntity, livingEntity, hand);
			            if(interactionResult == InteractionResult.SUCCESS)
			            	((JobCleric) rpg.getJob()).castSpell();
			            return interactionResult;
	        		}
    	}
    	return super.interactLivingEntity(itemStack, playerEntity, livingEntity, hand);
    }
    
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
    	if(!level.isClientSide) {
            if(player.isShiftKeyDown()) {
                int currentSelection = NBTHelper.getIntegerFromItemStack(player.getItemInHand(hand), NBT_SELECTED_SPELL);
                if(currentSelection >= DomainSpells.DOMAIN_SPELLS.size() - 1)
                    return InteractionResultHolder.success(NBTHelper.setIntegerToItemStack(player.getItemInHand(hand), NBT_SELECTED_SPELL, 0));
                else
                    return InteractionResultHolder.success(NBTHelper.setIntegerToItemStack(player.getItemInHand(hand), NBT_SELECTED_SPELL, NBTHelper.getIntegerFromItemStack(player.getItemInHand(hand), NBT_SELECTED_SPELL) + 1));
            } else {
            	LazyOptional<IRPG> lazy_optional_rpg = player.getCapability(RPGProvider.RPG_CAPABILITY, null);
    			final IRPG rpg = lazy_optional_rpg.orElse(null);
                if(rpg != null && rpg.getJob() instanceof JobCleric)
                	if(((JobCleric) rpg.getJob()).canCastSpell())
                		if(DomainSpells.DOMAIN_SPELLS.get(NBTHelper.getIntegerFromItemStack(player.getItemInHand(hand), NBT_SELECTED_SPELL)) != null) {
                			InteractionResultHolder<ItemStack> interactionResultHolder = DomainSpells.DOMAIN_SPELLS.get(NBTHelper.getIntegerFromItemStack(player.getItemInHand(hand), NBT_SELECTED_SPELL)).use(level, player, hand);
    						if(interactionResultHolder.getResult() == InteractionResult.SUCCESS)
    							((JobCleric) rpg.getJob()).castSpell();
    						return interactionResultHolder;
                		}
            }
        }
    	return super.use(level, player, hand);
    }
    
    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
    	if(!useOnContext.getLevel().isClientSide) {
    		LazyOptional<IRPG> lazy_optional_rpg = useOnContext.getPlayer().getCapability(RPGProvider.RPG_CAPABILITY, null);
			final IRPG rpg = lazy_optional_rpg.orElse(null);
            if(rpg != null && rpg.getJob() instanceof JobCleric)
            	if(((JobCleric) rpg.getJob()).canCastSpell())
            		if(DomainSpells.DOMAIN_SPELLS.get(NBTHelper.getIntegerFromItemStack(useOnContext.getItemInHand(), NBT_SELECTED_SPELL)) != null) {
            			InteractionResult interactionResult = DomainSpells.DOMAIN_SPELLS.get(NBTHelper.getIntegerFromItemStack(useOnContext.getItemInHand(), NBT_SELECTED_SPELL)).useOn(useOnContext);
						if(interactionResult == InteractionResult.SUCCESS)
							((JobCleric) rpg.getJob()).castSpell();
						return interactionResult;
		            }
        }
    	return super.useOn(useOnContext);
    }
}