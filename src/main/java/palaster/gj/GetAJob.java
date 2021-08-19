package palaster.gj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGFactory;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGStorage;
import palaster.gj.blocks.ModBlocks;
import palaster.gj.client.gui.RPGIntroScreen;
import palaster.gj.containers.ModContainerTypes;
import palaster.gj.core.handlers.EventHandler;
import palaster.gj.items.ModItems;
import palaster.gj.libs.LibMod;
import palaster.gj.network.client.PacketUpdateRPG;
import palaster.libpal.network.PacketHandler;

@Mod(LibMod.MODID)
public class GetAJob {
	
	public static final ItemGroup GET_A_JOB = new ItemGroup("getAJob") {
		@Override
		public ItemStack makeIcon() { return new ItemStack(ModItems.RPG_INTRO); }
	};
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	public GetAJob() {
		final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		// Register the onCommonSetup method for modloading
        eventBus.addListener(this::onCommonSetup);
        // Register the onClientSetup method for modloading
        eventBus.addListener(this::onClientSetup);
        // Register the onLoadComplete method for modloading
        eventBus.addListener(this::onLoadComplete);
        // Register the onEnqueueIMC method for modloading
        eventBus.addListener(this::onEnqueueIMC);
        // Register the onProcessIMC method for modloading
        eventBus.addListener(this::onProcessIMC);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(EventHandler.class);
	}
	
	private void onCommonSetup(final FMLCommonSetupEvent event) {
		CapabilityManager.INSTANCE.register(IRPG.class, new RPGStorage(), new RPGFactory());
		
		event.enqueueWork(() -> {
			PacketHandler.getInstance().registerMessage(PacketHandler.packet_id++, PacketUpdateRPG.class, PacketUpdateRPG::encode, PacketUpdateRPG::decode, PacketUpdateRPG::handle);
		});
	}

    private void onClientSetup(final FMLClientSetupEvent event) { }
    
    private void onLoadComplete(final FMLLoadCompleteEvent event) { }

    private void onEnqueueIMC(final InterModEnqueueEvent event) { }

    private void onProcessIMC(final InterModProcessEvent event) { }
    
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
    	@SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
    		blockRegistryEvent.getRegistry().registerAll(ModBlocks.ALTAR);
        }
    	
    	@SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
        	itemRegistryEvent.getRegistry().registerAll(ModItems.ALTAR,
        			ModItems.RPG_INTRO,
        			ModItems.JOB_PAMPHLET,
        			ModItems.PINK_SLIP,
        			ModItems.CONGEALED_BLOOD,
        			ModItems.GOSPEL,
        			ModItems.BLOOD_BOOK,
        			ModItems.HAND,
        			ModItems.HERB_SACK,
        			ModItems.TEST);
        }
    	
    	@SubscribeEvent
        public static void onContainerTypesRegistry(final RegistryEvent.Register<ContainerType<?>> containerTypesRegistryEvent) {
        	containerTypesRegistryEvent.getRegistry().registerAll(ModContainerTypes.RPG_INTRO_CONTAINER.setRegistryName(LibMod.MODID, "rpg_intro"));
        	DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
        		ScreenManager.register(ModContainerTypes.RPG_INTRO_CONTAINER, RPGIntroScreen::new);
        	});
        }
    }
}
