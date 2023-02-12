package palaster.gj.containers;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.ObjectHolder;

public class ModMenuTypes {
	@ObjectHolder(registryName = "minecraft:menu", value = "gj:rpg_intro")
	public static final MenuType<RPGIntroMenu> RPG_INTRO_CONTAINER = null;
}
