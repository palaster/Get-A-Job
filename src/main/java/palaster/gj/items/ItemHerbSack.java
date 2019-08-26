package palaster.gj.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.gj.api.capabilities.IRPG;
import palaster.gj.api.capabilities.RPGCapability;
import palaster.gj.jobs.JobBotanist;
import palaster.gj.jobs.spells.BotanySpells;
import palaster.gj.jobs.spells.IBotanySpell;
import palaster.gj.libs.LibMod;
import palaster.libpal.core.helpers.NBTHelper;
import palaster.libpal.items.ItemModSpecial;

import java.util.List;

public class ItemHerbSack extends ItemModSpecial {

    public static final String TAG_INT_SS = "gj:HerbSackSelectedSpell";

    public ItemHerbSack() {
        super();
        setRegistryName(LibMod.MODID, "herb_sack");
        setUnlocalizedName("herb_sack");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if(NBTHelper.getIntegerFromItemStack(stack, TAG_INT_SS) >= 0)
            tooltip.add("Selected Spell: " + I18n.format("gj.job.botanist.spell." + NBTHelper.getIntegerFromItemStack(stack, TAG_INT_SS)));
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if(!playerIn.world.isRemote) {
            IRPG rpg = RPGCapability.RPGCapabilityProvider.get(playerIn);
            if(rpg != null && rpg.getJob() != null && rpg.getJob() instanceof JobBotanist) {
                IBotanySpell IBS = BotanySpells.BOTANY_SPELLS.get(NBTHelper.getIntegerFromItemStack(stack, TAG_INT_SS));
                if(rpg.getMagick() < IBS.getCost())
                    return false;
                boolean result = IBS.itemInteractionForEntity(stack, playerIn, target, hand);
                if(result)
                    rpg.setMagick(rpg.getMagick() - IBS.getCost());
                return result;
            }
        }
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if(!worldIn.isRemote) {
            if(playerIn.isSneaking()) {
                int currentSelection = NBTHelper.getIntegerFromItemStack(playerIn.getHeldItem(handIn), TAG_INT_SS);
                if(currentSelection >= BotanySpells.BOTANY_SPELLS.size() - 1)
                    return ActionResult.newResult(EnumActionResult.SUCCESS, NBTHelper.setIntegerToItemStack(playerIn.getHeldItem(handIn), TAG_INT_SS, 0));
                else
                    return ActionResult.newResult(EnumActionResult.SUCCESS, NBTHelper.setIntegerToItemStack(playerIn.getHeldItem(handIn), TAG_INT_SS, NBTHelper.getIntegerFromItemStack(playerIn.getHeldItem(handIn), TAG_INT_SS) + 1));
            } else {
                IRPG rpg = RPGCapability.RPGCapabilityProvider.get(playerIn);
                if(rpg != null && rpg.getJob() != null && rpg.getJob() instanceof JobBotanist) {
                    IBotanySpell IBS = BotanySpells.BOTANY_SPELLS.get(NBTHelper.getIntegerFromItemStack(playerIn.getHeldItem(handIn), TAG_INT_SS));
                    if(rpg.getMagick() < IBS.getCost())
                        return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
                    ActionResult<ItemStack> temp = IBS.onItemRightClick(worldIn, playerIn, handIn);
                    if(temp.getType() == EnumActionResult.SUCCESS)
                        rpg.setMagick(rpg.getMagick() - IBS.getCost());
                    return temp;
                }
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            IRPG rpg = RPGCapability.RPGCapabilityProvider.get(player);
            if(rpg != null && rpg.getJob() != null && rpg.getJob() instanceof JobBotanist) {
                IBotanySpell IBS = BotanySpells.BOTANY_SPELLS.get(NBTHelper.getIntegerFromItemStack(player.getHeldItem(hand), TAG_INT_SS));
                if(rpg.getMagick() < IBS.getCost())
                    return EnumActionResult.FAIL;
                EnumActionResult temp = IBS.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
                if(temp == EnumActionResult.SUCCESS)
                    rpg.setMagick(rpg.getMagick() - IBS.getCost());
                return temp;
            }
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
}
