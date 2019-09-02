package palaster.gj.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import palaster.gj.libs.LibMod;

public class ItemGJSword extends ItemSword {

    public ItemGJSword(Item.ToolMaterial material) {
        super(material);
        setRegistryName(LibMod.MODID, material + "_sword");
        setUnlocalizedName(material.name() + "_sword");
    }
}