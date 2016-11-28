package palaster.gj.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraftforge.items.IItemHandler;

public class ContainerThirdHand extends Container {
	
	public ContainerThirdHand(InventoryPlayer invPlayer, IItemHandler ih) {}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) { return true; }
}
