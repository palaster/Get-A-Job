package palaster.gj.core.proxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import palaster.gj.GetAJob;
import palaster.gj.api.capabilities.IRPG;
import palaster.gj.api.capabilities.IWorld;
import palaster.gj.api.capabilities.RPGCapability.RPGCapabilityFactory;
import palaster.gj.api.capabilities.RPGCapability.RPGCapabilityProvider;
import palaster.gj.api.capabilities.RPGCapability.RPGCapabilityStorage;
import palaster.gj.api.capabilities.WorldCapability.WorldCapabilityFactory;
import palaster.gj.api.capabilities.WorldCapability.WorldCapabilityStorage;
import palaster.gj.client.gui.GuiRPGIntro;
import palaster.gj.client.gui.GuiThirdHand;
import palaster.gj.core.handlers.EventHandler;
import palaster.gj.inventories.ContainerRPGIntro;
import palaster.gj.inventories.ContainerThirdHand;
import palaster.gj.misc.Recipes;
import palaster.gj.network.client.UpdateRPGMessage;
import palaster.libpal.network.PacketHandler;

public class CommonProxy implements IGuiHandler {
	
	public static CreativeTabs tabGJ = new CreativeTabs("gj") {
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() { return new ItemStack(Items.APPLE); }
	};
	
    public void preInit() {
    	PacketHandler.registerMessage(UpdateRPGMessage.class);
    	CapabilityManager.INSTANCE.register(IRPG.class, new RPGCapabilityStorage(), new RPGCapabilityFactory());
    	CapabilityManager.INSTANCE.register(IWorld.class, new WorldCapabilityStorage(), new WorldCapabilityFactory());
    }

    public void init() {
    	MinecraftForge.EVENT_BUS.register(new EventHandler());
    	NetworkRegistry.INSTANCE.registerGuiHandler(GetAJob.instance, this);
    }

    public void postInit() { Recipes.init(); }
    
    public static void syncPlayerRPGCapabilitiesToClient(EntityPlayer player) {
		if(player != null && !player.worldObj.isRemote)
			if(RPGCapabilityProvider.get(player) != null)
				PacketHandler.sendTo(new UpdateRPGMessage(RPGCapabilityProvider.get(player).saveNBT()), (EntityPlayerMP) player);
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
			case 0: {
				return new ContainerThirdHand(player.inventory, z == 0 ? player.getHeldItemMainhand().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null) : player.getHeldItemOffhand().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null));
			}
			case 1: {
				return new ContainerRPGIntro();
			}
		}
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
			case 0: {
				return new GuiThirdHand(player.inventory, z == 0 ? player.getHeldItemMainhand().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null) : player.getHeldItemOffhand().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null));
			}
			case 1: {
				return new GuiRPGIntro(player, z);
			}
		}
		return null;
	}
}