package palaster.gj.misc;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import palaster.gj.entities.jobs.JobBloodSorcerer;
import palaster.gj.entities.jobs.JobCleric;
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
		GameRegistry.addShapelessRecipe(new ItemStack(GJItems.rpgIntro), Items.WRITABLE_BOOK, Items.IRON_SWORD);
		GameRegistry.addShapelessRecipe(NBTHelper.setStringToItemStack(new ItemStack(GJItems.jobPamphlet), ItemJobPamphlet.TAG_STRING_JOB_CLASS, ""), Items.WRITABLE_BOOK, GJItems.pinkSlip);
		GameRegistry.addShapelessRecipe(NBTHelper.setStringToItemStack(new ItemStack(GJItems.jobPamphlet), ItemJobPamphlet.TAG_STRING_JOB_CLASS, new JobBloodSorcerer().getClass().getName()), GJItems.jobPamphlet);
		GameRegistry.addShapelessRecipe(NBTHelper.setStringToItemStack(new ItemStack(GJItems.jobPamphlet), ItemJobPamphlet.TAG_STRING_JOB_CLASS, new JobCleric().getClass().getName()), GJItems.jobPamphlet);
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GJItems.pinkSlip), Items.PAPER, "dyePink", Items.FLINT_AND_STEEL));
	}
}
