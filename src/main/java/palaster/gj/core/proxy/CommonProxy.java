package palaster.gj.core.proxy;

import javax.annotation.Nullable;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
import palaster.gj.api.capabilities.RPGCapability.RPGCapabilityFactory;
import palaster.gj.api.capabilities.RPGCapability.RPGCapabilityProvider;
import palaster.gj.api.capabilities.RPGCapability.RPGCapabilityStorage;
import palaster.gj.client.gui.GuiRPGIntro;
import palaster.gj.core.handlers.EventHandler;
import palaster.gj.inventories.ContainerRPGIntro;
import palaster.gj.items.GJItems;
import palaster.gj.libs.LibMod;
import palaster.gj.network.client.UpdateRPGMessage;
import palaster.gj.recipes.Recipes;
import palaster.libpal.core.helpers.LibPalHelper;
import palaster.libpal.network.PacketHandler;

public class CommonProxy implements IGuiHandler {

    public void preInit() {
    	LibPalHelper.setCreativeTab(LibMod.MODID, new CreativeTabs("gj") {
    		@Override
    		@SideOnly(Side.CLIENT)
    		public ItemStack getTabIconItem() { return new ItemStack(GJItems.rpgIntro); }
    	});
    	PacketHandler.registerMessage(UpdateRPGMessage.class);
    	CapabilityManager.INSTANCE.register(IRPG.class, new RPGCapabilityStorage(), new RPGCapabilityFactory());
    }

    public void init() {
    	MinecraftForge.EVENT_BUS.register(new EventHandler());
    	NetworkRegistry.INSTANCE.registerGuiHandler(GetAJob.instance, this);
    }

    public void postInit() { Recipes.init(); }

    public static void syncPlayerRPGCapabilitiesToClient(@Nullable EntityPlayer player) {
    	if(player != null && !player.world.isRemote)
    		if(RPGCapabilityProvider.get(player) != null)
    			PacketHandler.sendTo(new UpdateRPGMessage(RPGCapabilityProvider.get(player).saveNBT()), (EntityPlayerMP) player);
    }

    @Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
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
			case 1: {
				return new GuiRPGIntro(player, z);
			}
		}
		return null;
	}
}