package palaster.gj.api.jobs;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Font;
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
	
	default boolean shouldDrawExtraInformation() { return true; }

	@OnlyIn(Dist.CLIENT)
	default void drawExtraInformationBase(PoseStack ps, Font font, int mouseX, int mouseY, @Nullable Player player, int suggestedX, int suggestedY) {
		if(shouldDrawExtraInformation()) {
			font.draw(ps, Component.translatable("gj.job.additionalInfo"), suggestedX, suggestedY, 4210752);
			drawExtraInformation(ps, font, mouseX, mouseY, player, suggestedX, suggestedY + 12);
		}
	}

	/**
	 * Allows drawing in extra space in the RPG Intro.
	 * 
	 * @param ps PoseStack from the gui.
	 * @param font Font from the gui.
	 * @param mouseX X value of the location of the mouse.
	 * @param mouseY Y value of the location of the mouse.
	 * @param player The player whose status is being shown.
	 * @param suggestedX Where you should start drawing, up to the end of the gui.
	 * @param suggestedY Where you should start drawing, up to the end of the gui.
	 */
	@OnlyIn(Dist.CLIENT)
	default void drawExtraInformation(PoseStack ps, Font font, int mouseX, int mouseY, @Nullable Player player, int suggestedX, int suggestedY) {}

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