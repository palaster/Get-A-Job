package palaster.gj.blocks;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ObjectHolder;
import palaster.gj.libs.LibMod;

@ObjectHolder(LibMod.MODID)
public class ModBlocks {
	public static final Block ALTAR = new AltarBlock(Properties.of(Material.STONE), new ResourceLocation(LibMod.MODID, "altar"));
}