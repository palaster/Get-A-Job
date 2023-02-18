package palaster.gj;

import com.mojang.serialization.Codec;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.blocks.AltarBlock;
import palaster.gj.client.screens.RPGIntroScreen;
import palaster.gj.containers.RPGIntroMenu;
import palaster.gj.core.handlers.EventHandler;
import palaster.gj.global_loot.HarvesterLootModifier;
import palaster.gj.global_loot.ModGlobalLootModifierProvider;
import palaster.gj.items.BloodBookItem;
import palaster.gj.items.GospelItem;
import palaster.gj.items.HandItem;
import palaster.gj.items.HerbSackItem;
import palaster.gj.items.JobApplicationItem;
import palaster.gj.items.KodokuItem;
import palaster.gj.items.PinkSlipItem;
import palaster.gj.items.RPGIntroItem;
import palaster.gj.items.TestItem;
import palaster.gj.libs.LibMod;
import palaster.gj.network.PacketHandler;

@Mod(LibMod.MODID)
public class GetAJob {

	private static final Item.Properties DEFAULT_PROPERTIES = new Item.Properties();
	private static final Item.Properties SPECIAL_PROPERTIES = DEFAULT_PROPERTIES.defaultDurability(0);

	// BLOCKS
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.Keys.BLOCKS, LibMod.MODID);

	public static final RegistryObject<Block> ALTAR_BLOCK = BLOCKS.register("altar", () -> new AltarBlock(BlockBehaviour.Properties.of(Material.STONE)));

	// ITEMS
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.Keys.ITEMS, LibMod.MODID);

	public static final RegistryObject<Item> ALTAR_ITEM = ITEMS.register("altar", () -> new BlockItem(ALTAR_BLOCK.get(), DEFAULT_PROPERTIES));

	public static final RegistryObject<Item> CONGEALED_BLOOD = ITEMS.register("congealed_blood", () -> new Item(DEFAULT_PROPERTIES));

	public static final RegistryObject<Item> RPG_INTRO_ITEM = ITEMS.register("rpg_intro", () -> new RPGIntroItem(SPECIAL_PROPERTIES));
	public static final RegistryObject<Item> JOB_APPLICATION = ITEMS.register("job_application", () -> new JobApplicationItem(SPECIAL_PROPERTIES));
	public static final RegistryObject<Item> PINK_SLIP = ITEMS.register("pink_slip", () -> new PinkSlipItem(SPECIAL_PROPERTIES));
	public static final RegistryObject<Item> GOSPEL = ITEMS.register("gospel", () -> new GospelItem(SPECIAL_PROPERTIES));
	public static final RegistryObject<Item> BLOOD_BOOK = ITEMS.register("blood_book", () -> new BloodBookItem(SPECIAL_PROPERTIES));
	public static final RegistryObject<Item> HAND = ITEMS.register("hand", () -> new HandItem(SPECIAL_PROPERTIES));
	public static final RegistryObject<Item> HERB_SACK = ITEMS.register("herb_sack", () -> new HerbSackItem(SPECIAL_PROPERTIES));
	public static final RegistryObject<Item> KODOKU = ITEMS.register("kodoku", () -> new KodokuItem(SPECIAL_PROPERTIES));
	public static final RegistryObject<Item> TEST = ITEMS.register("test", () -> new TestItem(SPECIAL_PROPERTIES));

	// MENU TYPES
	private static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.Keys.MENU_TYPES, LibMod.MODID);

	public static final RegistryObject<MenuType<RPGIntroMenu>> RPG_INTRO_MENU_TYPE = MENU_TYPES.register("rpg_intro", () -> new MenuType<>(new RPGIntroMenu.Factory()));

	// GLOBAL LOOT MODIFIER SERIALIZERS
	private static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIER = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, LibMod.MODID);

	public static final RegistryObject<Codec<HarvesterLootModifier>> HARVESTER = GLOBAL_LOOT_MODIFIER.register("harvester", HarvesterLootModifier.CODEC);
	
	public GetAJob() {
		final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

		BLOCKS.register(eventBus);
		ITEMS.register(eventBus);
		MENU_TYPES.register(eventBus);
		GLOBAL_LOOT_MODIFIER.register(eventBus);
		
		// Register the onCommonSetup method for modloading
        eventBus.addListener(this::onCommonSetup);
        // Register the onClientSetup method for modloading
        eventBus.addListener(this::onClientSetup);
        eventBus.addListener(this::onRegisterCapabilities);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(EventHandler.class);
	}
	
	private void onCommonSetup(final FMLCommonSetupEvent event) {
		PacketHandler.init();
	}

    private void onClientSetup(final FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(RPG_INTRO_MENU_TYPE.get(), RPGIntroScreen::new);
		});
	}

    private void onRegisterCapabilities(final RegisterCapabilitiesEvent event) {
		event.register(IRPG.class);
	}

    @EventBusSubscriber(modid = LibMod.MODID, bus = EventBusSubscriber.Bus.MOD)
    public static class ModEventHandler {

		@SubscribeEvent
		public static void registerCreativeModeTab(CreativeModeTabEvent.Register event) {
			event.registerCreativeModeTab(new ResourceLocation(LibMod.MODID, "creative_mode_tab"), (builder) -> 
				builder.title(Component.translatable("item_group.gj"))
				.icon(() -> new ItemStack(RPG_INTRO_ITEM.get()))
				.displayItems((featureFlagSet, output, operator) -> {
					output.accept(ALTAR_BLOCK.get());

					output.accept(CONGEALED_BLOOD.get());

					output.accept(RPG_INTRO_ITEM.get());
					output.accept(JOB_APPLICATION.get());
					output.accept(PINK_SLIP.get());
					output.accept(GOSPEL.get());
					output.accept(BLOOD_BOOK.get());
					output.accept(HAND.get());
					output.accept(HERB_SACK.get());
					output.accept(KODOKU.get());
					output.accept(TEST.get());
				})
			);
		}

		@SubscribeEvent
		public static void gatherData(GatherDataEvent event) {
			event.getGenerator().addProvider(event.includeServer(), new ModGlobalLootModifierProvider(event.getGenerator().getPackOutput(), LibMod.MODID));
		}
    }
}
