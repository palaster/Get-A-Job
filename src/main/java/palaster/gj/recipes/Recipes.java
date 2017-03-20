package palaster.gj.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import palaster.gj.blocks.GJBlocks;
import palaster.gj.items.GJItems;
import palaster.gj.items.ItemJobPamphlet;
import palaster.gj.jobs.JobBloodSorcerer;
import palaster.gj.jobs.JobCleric;
import palaster.libpal.core.helpers.NBTHelper;

public class Recipes {

	public static void init() {
		registerCraftingRecipes();
		registerSmeltingRecipes();
	}

	private static void registerCraftingRecipes() {
		GameRegistry.addShapedRecipe(new ItemStack(GJBlocks.altar, 1, 0), "xyx", "yxy", "xyx", 'x', Blocks.STONE, 'y', Items.GOLD_INGOT);
		GameRegistry.addShapelessRecipe(new ItemStack(GJBlocks.altar, 1, 1), new ItemStack(GJBlocks.altar), new ItemStack(Blocks.CRAFTING_TABLE));
		GameRegistry.addShapelessRecipe(new ItemStack(GJBlocks.altar, 1, 2), new ItemStack(GJBlocks.altar), new ItemStack(Items.SIGN));
		GameRegistry.addShapelessRecipe(new ItemStack(GJBlocks.altar, 1, 3), new ItemStack(GJBlocks.altar), new ItemStack(Items.DYE, 1, 15));

		GameRegistry.addShapelessRecipe(new ItemStack(GJItems.rpgIntro), Items.WRITABLE_BOOK, Items.IRON_SWORD);
		GameRegistry.addShapelessRecipe(NBTHelper.setStringToItemStack(new ItemStack(GJItems.jobPamphlet), ItemJobPamphlet.TAG_STRING_JOB_CLASS, ""), Items.WRITABLE_BOOK, GJItems.pinkSlip);
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GJItems.pinkSlip), Items.PAPER, "dyePink", Items.FLINT_AND_STEEL));

		GameRegistry.addShapedRecipe(new ItemStack(GJItems.gjMaterial, 1, 0), " x ", "xyx", " x ", 'x', Items.ROTTEN_FLESH, 'y', Items.SLIME_BALL);
		GameRegistry.addShapelessRecipe(new ItemStack(GJItems.hand), new ItemStack(GJItems.gjMaterial, 1, 0), Items.BONE, Items.ROTTEN_FLESH);
		GameRegistry.addShapedRecipe(new ItemStack(GJItems.clericStaff), "  x", " y ", "y  ", 'x', Items.ENDER_PEARL, 'y', Items.STICK);

		GameRegistry.addShapelessRecipe(NBTHelper.setStringToItemStack(new ItemStack(GJItems.jobPamphlet), ItemJobPamphlet.TAG_STRING_JOB_CLASS, new JobBloodSorcerer().getClass().getName()), GJItems.jobPamphlet, new ItemStack(GJItems.gjMaterial, 1, 0), Items.ROTTEN_FLESH);
		GameRegistry.addShapelessRecipe(NBTHelper.setStringToItemStack(new ItemStack(GJItems.jobPamphlet), ItemJobPamphlet.TAG_STRING_JOB_CLASS, new JobCleric().getClass().getName()), GJItems.jobPamphlet, GJItems.clericStaff, Items.BOOK);		
	}

	private static void registerSmeltingRecipes() {}
}
