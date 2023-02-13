package palaster.gj.global_loot;

import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.common.util.LazyOptional;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGProvider;
import palaster.gj.jobs.abilities.Abilities;

public class HarvesterLootModifier extends LootModifier {

    public static final Supplier<Codec<HarvesterLootModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, HarvesterLootModifier::new)));

    public HarvesterLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() { return CODEC.get(); }

    @NotNull
    @Override
    public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        Player player = (Player) context.getParamOrNull(LootContextParams.THIS_ENTITY);
        if(player == null)
            return generatedLoot;
        BlockState blockState = context.getParamOrNull(LootContextParams.BLOCK_STATE);
        if(blockState == null || !(blockState.getBlock() instanceof IPlantable))
            return generatedLoot;
        LazyOptional<IRPG> lazy_optional_rpg = player.getCapability(RPGProvider.RPG_CAPABILITY, null);
        final IRPG rpg = lazy_optional_rpg.orElse(null);
		if(rpg != null) {
            if(Abilities.BOUNTIFUL_HARVEST.isAvailable(rpg)) {
                return generatedLoot.stream()
                    .map(ItemStack::copy)
                    .peek(stack -> stack.setCount(Math.min(stack.getMaxStackSize(), stack.getCount() * 2)))
                    .collect(Collectors.toCollection(ObjectArrayList::new));
            }
        }
        return generatedLoot;
    }
}
