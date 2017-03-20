package palaster.gj.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.gj.libs.LibMod;
import palaster.libpal.api.ISubType;
import palaster.libpal.items.ItemModSpecial;

public class ItemGJMaterial extends ItemModSpecial implements ISubType {

	private static final String[] SUB_TYPES = {"congealedBlood"};

	public ItemGJMaterial() {
		super(0, 64);
		setHasSubtypes(true);
		setRegistryName(LibMod.MODID, "gjMaterial");
		setUnlocalizedName("gjMaterial");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		for(int i = 0; i < SUB_TYPES.length; i++)
			subItems.add(new ItemStack(itemIn, 1, i));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) { return super.getUnlocalizedName(stack) + "_" + SUB_TYPES[stack.getItemDamage()]; }

	@Override
	public String[] getTypes() { return SUB_TYPES; }
}
