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
import palaster.gj.jobs.BotanistJob;
import palaster.gj.jobs.spells.IBotanySpell;
import palaster.gj.jobs.spells.botany.BotanySpells;

public class HerbSackItem extends Item {

    public static final String NBT_SELECTED_SPELL = "gj:herb_sack:selected_spell";

    public HerbSackItem(Properties properties) { super(properties); }
    
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        LazyOptional<IRPG> lazy_optional_rpg = Minecraft.getInstance().player.getCapability(RPGProvider.RPG_CAPABILITY, null);
		IRPG rpg = lazy_optional_rpg.orElse(null);
		if(rpg != null && rpg.getJob() instanceof BotanistJob)
		    if(NBTHelper.getIntegerFromItemStack(stack, NBT_SELECTED_SPELL) >= 0)
			    tooltip.add(Component.literal("Selected Spell: " + I18n.get("gj.job.botanist.spell." + NBTHelper.getIntegerFromItemStack(stack, NBT_SELECTED_SPELL))));
	}
    
    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand hand) {
    	if(!player.level.isClientSide) {
    		LazyOptional<IRPG> lazy_optional_rpg = player.getCapability(RPGProvider.RPG_CAPABILITY, null);
			final IRPG rpg = lazy_optional_rpg.orElse(null);
	        if(rpg != null && rpg.getJob() instanceof BotanistJob) {
	            IBotanySpell IBS = BotanySpells.BOTANY_SPELLS.get(NBTHelper.getIntegerFromItemStack(itemStack, NBT_SELECTED_SPELL));
	            if(rpg.getMagick() < IBS.getCost())
	                return InteractionResult.FAIL;
                    InteractionResult interactionResult = IBS.interactLivingEntity(itemStack, player, livingEntity, hand);
	            if(interactionResult == InteractionResult.SUCCESS)
	                rpg.setMagick(rpg.getMagick() - IBS.getCost());
	            return interactionResult;
	        }
    	}
    	return super.interactLivingEntity(itemStack, player, livingEntity, hand);
    }
    
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
    	if(!level.isClientSide) {
            LazyOptional<IRPG> lazy_optional_rpg = player.getCapability(RPGProvider.RPG_CAPABILITY, null);
            final IRPG rpg = lazy_optional_rpg.orElse(null);
            if(rpg != null && rpg.getJob() instanceof BotanistJob) {
                if(player.isShiftKeyDown()) {
                    int currentSelection = NBTHelper.getIntegerFromItemStack(player.getItemInHand(hand), NBT_SELECTED_SPELL);
                    if(currentSelection >= BotanySpells.BOTANY_SPELLS.size() - 1)
                        return InteractionResultHolder.success(NBTHelper.setIntegerToItemStack(player.getItemInHand(hand), NBT_SELECTED_SPELL, 0));
                    else
                        return InteractionResultHolder.success(NBTHelper.setIntegerToItemStack(player.getItemInHand(hand), NBT_SELECTED_SPELL, NBTHelper.getIntegerFromItemStack(player.getItemInHand(hand), NBT_SELECTED_SPELL) + 1));
                } else {
                    IBotanySpell IBS = BotanySpells.BOTANY_SPELLS.get(NBTHelper.getIntegerFromItemStack(player.getItemInHand(hand), NBT_SELECTED_SPELL));
                    if(rpg.getMagick() < IBS.getCost())
                        return InteractionResultHolder.fail(player.getItemInHand(hand));
                        InteractionResultHolder<ItemStack> actionResult = IBS.use(level, player, hand);
                    if(actionResult.getResult() == InteractionResult.SUCCESS)
                        rpg.setMagick(rpg.getMagick() - IBS.getCost());
                    return actionResult;
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
            if(rpg != null && rpg.getJob() instanceof BotanistJob) {
                IBotanySpell IBS = BotanySpells.BOTANY_SPELLS.get(NBTHelper.getIntegerFromItemStack(useOnContext.getItemInHand(), NBT_SELECTED_SPELL));
                if(rpg.getMagick() < IBS.getCost())
                    return InteractionResult.FAIL;
                    InteractionResult interationResult = IBS.useOn(useOnContext);
                if(interationResult == InteractionResult.SUCCESS)
                    rpg.setMagick(rpg.getMagick() - IBS.getCost());
                return interationResult;
            }
        }
    	return super.useOn(useOnContext);
    }
}
