package palaster.gj.core;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import palaster.gj.libs.LibMod;

@EventBusSubscriber(modid = LibMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class GJConfig {

    public static final CommonConfig COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;

    static {
        final Pair<CommonConfig, ForgeConfigSpec> commonPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        COMMON = commonPair.getLeft();
        COMMON_SPEC = commonPair.getRight();
    }

    public static class CommonConfig {

        public final DoubleValue constitutionHealthRate;
        public final DoubleValue strengthDamageRate;
        public final DoubleValue dexteritySpeedRate;

        public CommonConfig(ForgeConfigSpec.Builder builder) {
            constitutionHealthRate = builder.comment("Value is multipled by Constitution to increase Max Health")
                                    .translation(LibMod.MODID + ".config.constitution_health_rate")
                                    .defineInRange("constitution_health_rate", .4, Double.MIN_VALUE, Double.MAX_VALUE);
            strengthDamageRate = builder.comment("Value is multipled by Strength to increase Attack Damage")
                                    .translation(LibMod.MODID + ".config.strength_damage_rate")
                                    .defineInRange("strength_damage_rate", 0.5, Double.MIN_VALUE, Double.MAX_VALUE);
            dexteritySpeedRate = builder.comment("Value is multipled by Dexterity to increase Speed")
                                    .translation(LibMod.MODID + ".config.dexterity_speed_rate")
                                    .defineInRange("dexterity_speed_rate", .008, Double.MIN_VALUE, Double.MAX_VALUE);
        }
    }
}
