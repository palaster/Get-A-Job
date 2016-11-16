package palaster.gj.core.handlers;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import palaster.gj.items.ItemThirdHand;

public class StaticEventHandler {

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> e) {
		e.getRegistry().register(new ItemThirdHand());
	}
}
