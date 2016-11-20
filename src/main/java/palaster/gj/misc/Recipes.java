package palaster.gj.misc;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import palaster.gj.items.GJItems;
import palaster.gj.items.ItemJobPamphlet;
import palaster.libpal.core.helpers.NBTHelper;

public class Recipes {

	public static void init() {
		registerCraftingRecipes();
		registerSmeltingRecipes();
	}
	
	private static void registerSmeltingRecipes() {}
	
	private static void registerCraftingRecipes() {
		GameRegistry.addShapelessRecipe(NBTHelper.setStringToItemStack(new ItemStack(GJItems.jobPamphlet), ItemJobPamphlet.TAG_STRING_JOB_CLASS, ""), Items.WRITABLE_BOOK, GJItems.pinkSlip);
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GJItems.pinkSlip), Items.PAPER, "dyePink", Items.FLINT_AND_STEEL));
	}
}
