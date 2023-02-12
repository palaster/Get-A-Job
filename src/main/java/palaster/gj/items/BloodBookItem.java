package palaster.gj.items;

import java.util.List;

import javax.annotation.Nullable;

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
import palaster.gj.jobs.JobBloodSorcerer;
import palaster.gj.jobs.spells.BloodSpells;
import palaster.gj.jobs.spells.IBloodSpell;

public class BloodBookItem extends Item {

	public static final String NBT_SELECTED_SPELL = "gj:blood_book:selected_spell";

	public BloodBookItem(Properties properties) { super(properties); }
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		if(NBTHelper.getIntegerFromItemStack(stack, NBT_SELECTED_SPELL) >= 0)
			tooltip.add(Component.literal("Selected Spell: " + I18n.get("gj.job.bloodSorcerer.spell." + NBTHelper.getIntegerFromItemStack(stack, NBT_SELECTED_SPELL))));
	}
	
	@Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand hand) {
    	if(!player.level.isClientSide) {
    		LazyOptional<IRPG> lazy_optional_rpg = player.getCapability(RPGProvider.RPG_CAPABILITY, null);
			final IRPG rpg = lazy_optional_rpg.orElse(null);
	        if(rpg != null && rpg.getJob() instanceof JobBloodSorcerer) {
	            IBloodSpell IBS = BloodSpells.BLOOD_SPELLS.get(NBTHelper.getIntegerFromItemStack(itemStack, NBT_SELECTED_SPELL));
	            InteractionResult interactionResult = IBS.interactLivingEntity(itemStack, player, livingEntity, hand);
	            if(interactionResult == InteractionResult.SUCCESS)
	            	((JobBloodSorcerer) rpg.getJob()).setBloodCurrent(player, ((JobBloodSorcerer) rpg.getJob()).getBloodCurrent() - IBS.getBloodCost());
	            return interactionResult;
	        }
    	}
    	return super.interactLivingEntity(itemStack, player, livingEntity, hand);
    }
    
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
    	if(!level.isClientSide) {
            if(player.isShiftKeyDown()) {
                int currentSelection = NBTHelper.getIntegerFromItemStack(player.getItemInHand(hand), NBT_SELECTED_SPELL);
                if(currentSelection >= BloodSpells.BLOOD_SPELLS.size() - 1)
                    return InteractionResultHolder.success(NBTHelper.setIntegerToItemStack(player.getItemInHand(hand), NBT_SELECTED_SPELL, 0));
                else
                    return InteractionResultHolder.success(NBTHelper.setIntegerToItemStack(player.getItemInHand(hand), NBT_SELECTED_SPELL, NBTHelper.getIntegerFromItemStack(player.getItemInHand(hand), NBT_SELECTED_SPELL) + 1));
            } else {
            	LazyOptional<IRPG> lazy_optional_rpg = player.getCapability(RPGProvider.RPG_CAPABILITY, null);
    			final IRPG rpg = lazy_optional_rpg.orElse(null);
                if(rpg != null && rpg.getJob() instanceof JobBloodSorcerer) {
                    IBloodSpell IBS = BloodSpells.BLOOD_SPELLS.get(NBTHelper.getIntegerFromItemStack(player.getItemInHand(hand), NBT_SELECTED_SPELL));
                    InteractionResultHolder<ItemStack> interactionResultHolder = IBS.use(level, player, hand);
                    if(interactionResultHolder.getResult() == InteractionResult.SUCCESS)
                    	((JobBloodSorcerer) rpg.getJob()).setBloodCurrent(player, ((JobBloodSorcerer) rpg.getJob()).getBloodCurrent() - IBS.getBloodCost());
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
            if(rpg != null && rpg.getJob() instanceof JobBloodSorcerer) {
                IBloodSpell IBS = BloodSpells.BLOOD_SPELLS.get(NBTHelper.getIntegerFromItemStack(useOnContext.getItemInHand(), NBT_SELECTED_SPELL));
                InteractionResult interactionResult = IBS.useOn(useOnContext);
                if(interactionResult == InteractionResult.SUCCESS)
                	((JobBloodSorcerer) rpg.getJob()).setBloodCurrent(useOnContext.getPlayer(), ((JobBloodSorcerer) rpg.getJob()).getBloodCurrent() - IBS.getBloodCost());
                return interactionResult;
            }
        }
    	return super.useOn(useOnContext);
    }
}
