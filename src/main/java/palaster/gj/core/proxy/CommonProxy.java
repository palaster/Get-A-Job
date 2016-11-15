package palaster.gj.core.proxy;

import net.minecraftforge.common.MinecraftForge;
import palaster.gj.core.handlers.EventHandler;

public class CommonProxy {

    public void preInit() {}

    public void init() { MinecraftForge.EVENT_BUS.register(new EventHandler()); }

    public void postInit() {}
}