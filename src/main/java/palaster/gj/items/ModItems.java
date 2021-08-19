package palaster.gj.items;

import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ObjectHolder;
import palaster.gj.GetAJob;
import palaster.gj.blocks.ModBlocks;
import palaster.gj.libs.LibMod;
import palaster.libpal.items.ModBlockItem;

@ObjectHolder(LibMod.MODID)
public class ModItems {
	private static final Properties DEFAULT_PROPERTIES = new Properties().tab(GetAJob.GET_A_JOB);
	
	public static final Item ALTAR = new ModBlockItem(ModBlocks.ALTAR, DEFAULT_PROPERTIES),
			RPG_INTRO = new RPGIntroItem(DEFAULT_PROPERTIES, new ResourceLocation(LibMod.MODID, "rpg_intro")),
			JOB_PAMPHLET = new JobPamphletItem(DEFAULT_PROPERTIES, new ResourceLocation(LibMod.MODID, "job_pamphlet")),
			PINK_SLIP = new PinkSlipItem(DEFAULT_PROPERTIES, new ResourceLocation(LibMod.MODID, "pink_slip")),
			CONGEALED_BLOOD = new MaterialItemMod(DEFAULT_PROPERTIES, new ResourceLocation(LibMod.MODID, "congealed_blood")),
			GOSPEL = new GospelItem(DEFAULT_PROPERTIES, new ResourceLocation(LibMod.MODID, "gospel")),
			BLOOD_BOOK = new BloodBookItem(DEFAULT_PROPERTIES, new ResourceLocation(LibMod.MODID, "blood_book")),
			HAND = new HandItem(DEFAULT_PROPERTIES, new ResourceLocation(LibMod.MODID, "hand")),
			HERB_SACK = new HerbSackItem(DEFAULT_PROPERTIES, new ResourceLocation(LibMod.MODID, "herb_sack")),
			TEST = new TestItem(DEFAULT_PROPERTIES, new ResourceLocation(LibMod.MODID, "test"));
}