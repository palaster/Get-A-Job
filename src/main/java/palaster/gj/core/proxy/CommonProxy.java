package palaster.gj.core.proxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.gj.GetAJob;
import palaster.gj.core.handlers.EventHandler;
import palaster.gj.inventories.ContainerThirdHand;

public class CommonProxy implements IGuiHandler {
	
	public static CreativeTabs tabGJ = new CreativeTabs("gj") {
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() { return new ItemStack(Items.APPLE); }
	};
	
    public void preInit() {}

    public void init() {
    	MinecraftForge.EVENT_BUS.register(new EventHandler());
    	NetworkRegistry.INSTANCE.registerGuiHandler(GetAJob.instance, this);
    }

    public void postInit() {}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
			case 0: {
				return new ContainerThirdHand();
			}
			default:
				return null;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) { return null; }
}