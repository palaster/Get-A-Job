package palaster.gj.core.proxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.gj.GetAJob;
import palaster.gj.api.capabilities.IRPG;
import palaster.gj.api.capabilities.IWorld;
import palaster.gj.api.capabilities.RPGCapability.RPGCapabilityFactory;
import palaster.gj.api.capabilities.RPGCapability.RPGCapabilityStorage;
import palaster.gj.api.capabilities.WorldCapability.WorldCapabilityFactory;
import palaster.gj.api.capabilities.WorldCapability.WorldCapabilityStorage;
import palaster.gj.core.handlers.EventHandler;
import palaster.gj.inventories.ContainerThirdHand;
import palaster.gj.misc.Recipes;

public class CommonProxy implements IGuiHandler {
	
	public static CreativeTabs tabGJ = new CreativeTabs("gj") {
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() { return new ItemStack(Items.APPLE); }
	};
	
    public void preInit() {
    	CapabilityManager.INSTANCE.register(IRPG.class, new RPGCapabilityStorage(), new RPGCapabilityFactory());
    	CapabilityManager.INSTANCE.register(IWorld.class, new WorldCapabilityStorage(), new WorldCapabilityFactory());
    }

    public void init() {
    	MinecraftForge.EVENT_BUS.register(new EventHandler());
    	NetworkRegistry.INSTANCE.registerGuiHandler(GetAJob.instance, this);
    }

    public void postInit() { Recipes.init(); }

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