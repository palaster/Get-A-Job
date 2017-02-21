package palaster.gj.items;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import palaster.gj.libs.LibMod;

@ObjectHolder(LibMod.MODID)
public class GJItems {

	public static final Item rpgIntro = new ItemRPGIntro(),
			jobPamphlet = new ItemJobPamphlet(),
			pinkSlip = new ItemPinkSlip(),
			gjMaterial = new ItemGJMaterial(),
			clericStaff = new ItemClericStaff(),
			hand = new ItemHand();
}
