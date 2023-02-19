package palaster.gj.api.jobs;

import java.util.ArrayList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.INBTSerializable;
import palaster.gj.api.capabilities.rpg.IRPG;

public interface IRPGJob extends INBTSerializable<Tag> {

	String getJobName();

	default boolean canLeave() { return true; }

	default void leaveJob(@Nullable Player player) {}
	
	@OnlyIn(Dist.CLIENT)
	default boolean shouldDrawExtraInformation() { return true; }

	@OnlyIn(Dist.CLIENT)
	default void addExtraInformation(final ArrayList<Component> components) {}

	default void updatePlayerAttributes(Player player) {}

	default boolean replaceMagick() { return false; }

	default boolean overrideConstitution() { return false; }
	default boolean overrideStrength() { return false; }
	default boolean overrideDefense() { return false; }
	default boolean overrideDexterity() { return false; }
	default boolean overrideIntelligence() { return false; }

	default int getOverrideConstitution(int originalStat) { return originalStat; }
	default int getOverrideStrength(int originalStat) { return originalStat; }
	default int getOverrideDefense(int originalStat) { return originalStat; }
	default int getOverrideDexterity(int originalStat) { return originalStat; }
	default int getOverrideIntelligence(int originalStat) { return originalStat; }
	
	default boolean doUpdate() { return false; }
	
	default void update(IRPG rpg, Player player) {}
	
	@Override
	@Nonnull
	default Tag serializeNBT() { return new CompoundTag(); }

	@Override
	default void deserializeNBT(Tag nbt) {}
}