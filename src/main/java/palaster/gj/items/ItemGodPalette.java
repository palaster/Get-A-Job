package palaster.gj.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.gj.api.capabilities.IRPG;
import palaster.gj.api.capabilities.RPGCapability;
import palaster.gj.jobs.JobGod;
import palaster.gj.jobs.abilities.GodPowers;
import palaster.gj.libs.LibMod;
import palaster.libpal.core.helpers.NBTHelper;
import palaster.libpal.items.ItemModSpecial;

import java.util.List;

public class ItemGodPalette extends ItemModSpecial {

    public static final String TAG_INT_GP = "gj:GodSelectedPower";

    public ItemGodPalette() {
        super();
        setRegistryName(LibMod.MODID, "god_palette");
        setUnlocalizedName("god_palette");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if(NBTHelper.getIntegerFromItemStack(stack, TAG_INT_GP) >= 0)
            tooltip.add("Selected Power: " + I18n.format("gj.job.god.power." + NBTHelper.getIntegerFromItemStack(stack, TAG_INT_GP)));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if(!worldIn.isRemote) {
            if(playerIn.isSneaking()) {
                int currentSelection = NBTHelper.getIntegerFromItemStack(playerIn.getHeldItem(handIn), TAG_INT_GP);
                if(currentSelection >= GodPowers.GOD_POWERS.size() - 1)
                    return ActionResult.newResult(EnumActionResult.SUCCESS, NBTHelper.setIntegerToItemStack(playerIn.getHeldItem(handIn), TAG_INT_GP, 0));
                else
                    return ActionResult.newResult(EnumActionResult.SUCCESS, NBTHelper.setIntegerToItemStack(playerIn.getHeldItem(handIn), TAG_INT_GP, NBTHelper.getIntegerFromItemStack(playerIn.getHeldItem(handIn), TAG_INT_GP) + 1));
            } else {
                IRPG rpg = RPGCapability.RPGCapabilityProvider.get(playerIn);
                if(rpg != null && rpg.getJob() != null && rpg.getJob() instanceof JobGod) {
                    int currentSelection = NBTHelper.getIntegerFromItemStack(playerIn.getHeldItem(handIn), TAG_INT_GP);
                    ((JobGod) rpg.getJob()).setPower(GodPowers.GOD_POWERS.get(currentSelection), !((JobGod) rpg.getJob()).isPowerActive(GodPowers.GOD_POWERS.get(currentSelection)));
                    return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
                }
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
