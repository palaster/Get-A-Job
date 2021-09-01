package palaster.gj.api.jobs;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.INBTSerializable;
import palaster.gj.api.capabilities.rpg.IRPG;

public interface IRPGJob extends INBTSerializable<INBT> {

	String getJobName();

	default boolean canLeave() { return true; }

	default void leaveJob(@Nullable PlayerEntity player) {}
	
	default boolean shouldDrawExtraInformation() { return true; }

	@OnlyIn(Dist.CLIENT)
	default void drawExtraInformationBase(MatrixStack ms, FontRenderer font, int mouseX, int mouseY, @Nullable PlayerEntity player, int suggestedX, int suggestedY) {
		if(shouldDrawExtraInformation()) {
			font.draw(ms, I18n.get("gj.job.additionalInfo"), suggestedX, suggestedY, 4210752);
			drawExtraInformation(ms, font, mouseX, mouseY, player, suggestedX, suggestedY + 12);
		}
	}

	/**
	 * Allows drawing in extra space in the RPG Intro.
	 * 
	 * @param ms MatrixStack from the gui.
	 * @param font FontRenderer from the gui.
	 * @param mouseX X value of the location of the mouse.
	 * @param mouseY Y value of the location of the mouse.
	 * @param player The player whose status is being shown.
	 * @param suggestedX Where you should start drawing, up to the end of the gui.
	 * @param suggestedY Where you should start drawing, up to the end of the gui.
	 */
	@OnlyIn(Dist.CLIENT)
	default void drawExtraInformation(MatrixStack ms, FontRenderer font, int mouseX, int mouseY, @Nullable PlayerEntity player, int suggestedX, int suggestedY) {}

	default void updatePlayerAttributes(PlayerEntity player) {}

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
	
	default void update(IRPG rpg, PlayerEntity player) {}
	
	@Override
	@Nonnull
	default INBT serializeNBT() { return new CompoundNBT(); }

	@Override
	default void deserializeNBT(INBT nbt) {}
}