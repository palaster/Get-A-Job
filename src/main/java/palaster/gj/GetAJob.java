package palaster.gj;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import palaster.gj.core.proxy.CommonProxy;
import palaster.gj.libs.LibMod;

@Mod(modid = LibMod.MODID, name = LibMod.NAME, version = LibMod.VERSION, dependencies = LibMod.DEPENDENCIES, guiFactory = LibMod.GUI_FACTORY, updateJSON = LibMod.UPDATE_JSON)
public class GetAJob {

    @Mod.Instance(LibMod.MODID)
    public static GetAJob instance;

    @SidedProxy(clientSide = LibMod.CLIENT, serverSide = LibMod.SERVER)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) { proxy.preInit(); }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) { proxy.init(); }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) { proxy.postInit(); }
}
