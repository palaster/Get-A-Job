package palaster.gj.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import palaster.gj.libs.LibMod;
import palaster.libpal.api.IModObject;

public class ItemGJSword extends ItemSword implements IModObject {

    public ItemGJSword(Item.ToolMaterial material) {
        super(material);
        setRegistryName(LibMod.MODID, material.name() + "_sword");
        setUnlocalizedName(material.name() + "_sword");
    }
}