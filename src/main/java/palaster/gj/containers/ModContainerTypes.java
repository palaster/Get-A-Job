package palaster.gj.containers;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.registries.ObjectHolder;
import palaster.gj.libs.LibMod;

@ObjectHolder(LibMod.MODID)
public class ModContainerTypes {
	public static final ContainerType<RPGIntroContainer> RPG_INTRO_CONTAINER = IForgeContainerType.create(RPGIntroContainer::fromNetwork);
}
