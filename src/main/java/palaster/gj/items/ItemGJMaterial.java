package palaster.gj.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.gj.libs.LibMod;
import palaster.libpal.api.ISubType;
import palaster.libpal.items.ItemModSpecial;

public class ItemGJMaterial extends ItemModSpecial implements ISubType {

	private static final String[] SUB_TYPES = {"congealed_blood"};

	public ItemGJMaterial() {
		super(0, 64);
		setHasSubtypes(true);
		setRegistryName(LibMod.MODID, "gj_material");
		setUnlocalizedName("gj_material");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		for(int i = 0; i < SUB_TYPES.length; i++)
			subItems.add(new ItemStack(this, 1, i));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) { return super.getUnlocalizedName(stack) + "." + SUB_TYPES[stack.getItemDamage()]; }

	@Override
	public String[] getTypes() { return SUB_TYPES; }
}
