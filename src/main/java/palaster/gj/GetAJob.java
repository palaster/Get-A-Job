package palaster.gj;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.blocks.AltarBlock;
import palaster.gj.blocks.ModBlocks;
import palaster.gj.client.screens.RPGIntroScreen;
import palaster.gj.containers.ModMenuTypes;
import palaster.gj.containers.RPGIntroMenu;
import palaster.gj.core.handlers.EventHandler;
import palaster.gj.items.BloodBookItem;
import palaster.gj.items.GospelItem;
import palaster.gj.items.HandItem;
import palaster.gj.items.HerbSackItem;
import palaster.gj.items.JobPamphletItem;
import palaster.gj.items.ModItems;
import palaster.gj.items.PinkSlipItem;
import palaster.gj.items.RPGIntroItem;
import palaster.gj.items.TestItem;
import palaster.gj.libs.LibMod;
import palaster.gj.network.PacketHandler;

@Mod(LibMod.MODID)
public class GetAJob {
	
	public static final CreativeModeTab GET_A_JOB = new CreativeModeTab("getAJob") {
		@Override
		public ItemStack makeIcon() { return new ItemStack(ModItems.RPG_INTRO); }
	};
	
	public GetAJob() {
		final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		// Register the onCommonSetup method for modloading
        eventBus.addListener(this::onCommonSetup);
        // Register the onClientSetup method for modloading
        eventBus.addListener(this::onClientSetup);
        // Register the onLoadComplete method for modloading
        eventBus.addListener(this::onRegisterCapabilities);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(EventHandler.class);
	}
	
	private void onCommonSetup(final FMLCommonSetupEvent event) {
		PacketHandler.init();
	}

    private void onClientSetup(final FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(ModMenuTypes.RPG_INTRO_CONTAINER, RPGIntroScreen::new);
		});
	}

    private void onRegisterCapabilities(final RegisterCapabilitiesEvent event) {
		event.register(IRPG.class);
	}
    
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

		@SubscribeEvent
        public static void onRegister(RegisterEvent event) {
    		event.register(ForgeRegistries.Keys.BLOCKS, helper -> {
				helper.register(new ResourceLocation(LibMod.MODID, "altar"), new AltarBlock(BlockBehaviour.Properties.of(Material.STONE)));
			});
			final Item.Properties DEFAULT_PROPERTIES = new Item.Properties().tab(GetAJob.GET_A_JOB);
			final Item.Properties SPECIAL_PROPERTIES = DEFAULT_PROPERTIES.defaultDurability(0);
			event.register(ForgeRegistries.Keys.ITEMS, helper -> {
				helper.register(new ResourceLocation(LibMod.MODID, "altar"), new BlockItem(ModBlocks.ALTAR, DEFAULT_PROPERTIES));

				helper.register(new ResourceLocation(LibMod.MODID, "congealed_blood"), new Item(DEFAULT_PROPERTIES));

				helper.register(new ResourceLocation(LibMod.MODID, "rpg_intro"), new RPGIntroItem(SPECIAL_PROPERTIES));
				helper.register(new ResourceLocation(LibMod.MODID, "job_pamphlet"), new JobPamphletItem(SPECIAL_PROPERTIES));
				helper.register(new ResourceLocation(LibMod.MODID, "pink_slip"), new PinkSlipItem(SPECIAL_PROPERTIES));
				helper.register(new ResourceLocation(LibMod.MODID, "gospel"), new GospelItem(SPECIAL_PROPERTIES));
				helper.register(new ResourceLocation(LibMod.MODID, "blood_book"), new BloodBookItem(SPECIAL_PROPERTIES));
				helper.register(new ResourceLocation(LibMod.MODID, "hand"), new HandItem(SPECIAL_PROPERTIES));
				helper.register(new ResourceLocation(LibMod.MODID, "herb_sack"), new HerbSackItem(SPECIAL_PROPERTIES));
				helper.register(new ResourceLocation(LibMod.MODID, "test"), new TestItem(SPECIAL_PROPERTIES));
			});
			event.register(ForgeRegistries.Keys.MENU_TYPES, helper -> {
				helper.register(new ResourceLocation(LibMod.MODID, "rpg_intro"), new MenuType<>(new RPGIntroMenu.Factory()));
			});
        }
    }
}
