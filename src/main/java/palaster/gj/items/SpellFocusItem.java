package palaster.gj.items;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGProvider;
import palaster.gj.core.helpers.NBTHelper;
import palaster.gj.jobs.spells.ISpell;
import palaster.gj.jobs.spells.Spells;

public class SpellFocusItem extends Item {

    private final Spells<?> spells;

    public SpellFocusItem(Spells<?> spells, Properties properties) {
        super(properties);
        this.spells = spells;
    }

    @Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        LazyOptional<IRPG> lazy_optional_rpg = Minecraft.getInstance().player.getCapability(RPGProvider.RPG_CAPABILITY, null);
		IRPG rpg = lazy_optional_rpg.orElse(null);
		if(rpg != null && spells.getJobClass().isInstance(rpg.getJob()))
		    if(NBTHelper.getIntegerFromItemStack(stack, spells.getSelectedSpellNBTString()) >= 0) {
                int selectedSpell = NBTHelper.getIntegerFromItemStack(stack, spells.getSelectedSpellNBTString());
                tooltip.add(Component.translatable("item.gj.spell_focus_item.selected_spell", spells.getSpells().get(selectedSpell).getSpellName().getString()));
                int cost = spells.getSpells().get(selectedSpell).getCost();
                if(cost > 0)
                    tooltip.add(Component.translatable("item.gj.spell_focus_item.cost", cost));
            }
	}
    
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity livingEntity, InteractionHand hand) {
    	if(!player.level.isClientSide) {
            if(NBTHelper.getIntegerFromItemStack(stack, spells.getSelectedSpellNBTString()) < 0)
                return super.interactLivingEntity(stack, player, livingEntity, hand);
            ISpell spell = spells.getSpells().get(NBTHelper.getIntegerFromItemStack(stack, spells.getSelectedSpellNBTString()));
            if(!spell.canCast(player))
                return InteractionResult.FAIL;
            InteractionResult interactionResult = spell.interactLivingEntity(stack, player, livingEntity, hand);
            if(interactionResult == InteractionResult.SUCCESS)
                spell.applyCost(player);
            return interactionResult;
    	}
    	return super.interactLivingEntity(stack, player, livingEntity, hand);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemStack) { return UseAnim.BOW; }

    @Override
    public int getUseDuration(ItemStack stack) {
        if(NBTHelper.getIntegerFromItemStack(stack, spells.getSelectedSpellNBTString()) >= 0) {
            ISpell spell = spells.getSpells().get(NBTHelper.getIntegerFromItemStack(stack, spells.getSelectedSpellNBTString()));
            return spell.getUseDuration(stack);
        }
        return 0;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if(NBTHelper.getIntegerFromItemStack(stack, spells.getSelectedSpellNBTString()) >= 0) {
            ISpell spell = spells.getSpells().get(NBTHelper.getIntegerFromItemStack(stack, spells.getSelectedSpellNBTString()));
            return spell.finishUsingItem(stack, level, livingEntity);
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }
    
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
    	if(!level.isClientSide)
            if(player.isShiftKeyDown()) {
                LazyOptional<IRPG> lazy_optional_rpg = player.getCapability(RPGProvider.RPG_CAPABILITY, null);
                final IRPG rpg = lazy_optional_rpg.orElse(null);
                if(rpg != null && spells.getJobClass().isInstance(rpg.getJob())) {
                    int currentSelection = NBTHelper.getIntegerFromItemStack(player.getItemInHand(hand), spells.getSelectedSpellNBTString());
                    if(currentSelection >= spells.getSpells().size() - 1)
                        return InteractionResultHolder.success(NBTHelper.setIntegerToItemStack(player.getItemInHand(hand), spells.getSelectedSpellNBTString(), 0));
                    else
                        return InteractionResultHolder.success(NBTHelper.setIntegerToItemStack(player.getItemInHand(hand), spells.getSelectedSpellNBTString(), NBTHelper.getIntegerFromItemStack(player.getItemInHand(hand), spells.getSelectedSpellNBTString()) + 1));
                }
            } else {
                if(NBTHelper.getIntegerFromItemStack(player.getItemInHand(hand), spells.getSelectedSpellNBTString()) < 0)
                    return super.use(level, player, hand);
                ISpell spell = spells.getSpells().get(NBTHelper.getIntegerFromItemStack(player.getItemInHand(hand), spells.getSelectedSpellNBTString()));
                if(!spell.canCast(player))
                    return InteractionResultHolder.fail(player.getItemInHand(hand));
                InteractionResultHolder<ItemStack> actionResult = spell.use(level, player, hand);
                if(actionResult.getResult() == InteractionResult.SUCCESS)
                    spell.applyCost(player);
                return actionResult;
            }
    	return super.use(level, player, hand);
    }
    
    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
    	if(!useOnContext.getLevel().isClientSide) {
            if(NBTHelper.getIntegerFromItemStack(useOnContext.getItemInHand(), spells.getSelectedSpellNBTString()) < 0)
                return super.useOn(useOnContext);
            ISpell spell = spells.getSpells().get(NBTHelper.getIntegerFromItemStack(useOnContext.getItemInHand(), spells.getSelectedSpellNBTString()));
            if(!spell.canCast(useOnContext.getPlayer()))
                return InteractionResult.FAIL;
            InteractionResult interationResult = spell.useOn(useOnContext);
            if(interationResult == InteractionResult.SUCCESS)
                spell.applyCost(useOnContext.getPlayer());
            return interationResult;
        }
    	return super.useOn(useOnContext);
    }
}
