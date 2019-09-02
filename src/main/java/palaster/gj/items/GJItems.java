package palaster.gj.items;

import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import palaster.gj.libs.LibMod;

import static palaster.gj.items.GJToolMaterials.MATH_MATERIAL;

@ObjectHolder(LibMod.MODID)
public class GJItems {

	public static final Item RPG_INTRO = new ItemRPGIntro(),
			JOB_PAMPHLET = new ItemJobPamphlet(),
			PINK_SLIP = new ItemPinkSlip(),
			GJ_MATERIAL = new ItemGJMaterial(),
			CLERIC_STAFF = new ItemClericStaff(),
			BLOOD_BOOK = new ItemBloodBook(),
			HAND = new ItemHand(),
			HERB_SACK = new ItemHerbSack(),
			GOD_PALETTE = new ItemGodPalette(),
			MATH_SWORD = new ItemGJSword(MATH_MATERIAL),
			TEST = new ItemTest();
}