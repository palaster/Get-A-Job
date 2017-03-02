package palaster.gj.api.jobs;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IRPGJob extends INBTSerializable<NBTTagCompound> {

	String getJobName();

	default boolean canLeave() { return true; }

	default void leaveJob(@Nullable EntityPlayer player) {}

	@SideOnly(Side.CLIENT)
	default void drawExtraInformationBase(@Nullable EntityPlayer player, FontRenderer fontRendererObj, int suggestedX, int suggestedY, int mouseX, int mouseY) {
		fontRendererObj.drawString(I18n.format("gj.job.additionalInfo") + ":", suggestedX, suggestedY - 10, 4210752);
		drawExtraInformation(player, fontRendererObj, suggestedX, suggestedY, mouseX, mouseY);
	}

	/**
	 * Allows drawing in extra space in the RPG Intro.
	 * 
	 * @param player The player whose status is being shown.
	 * @param fontRendererObj FontRenderer from the gui.
	 * @param suggestedX Where you should start drawing, up to the end of the gui.
	 * @param suggestedY Where you should start drawing, up to the end of the gui.
	 * @param mouseX X value of the location of the mouse.
	 * @param mouseY Y value of the location of the mouse.
	 */
	@SideOnly(Side.CLIENT)
	default void drawExtraInformation(@Nullable EntityPlayer player, FontRenderer fontRendererObj, int suggestedX, int suggestedY, int mouseX, int mouseY) {}

	default void updatePlayerAttributes(EntityPlayer player) {}

	default boolean replaceMagick() { return false; }

	default boolean overrideConstitution() { return false; }
	default boolean overrideStrength() { return false; }
	default boolean overrideDefense() { return false; }
	default boolean overrideDexterity() { return false; }
	default boolean overrideIntelligence() { return false; }

	default int getOverrideConstitution() { return 0; }
	default int getOverrideStrength() { return 0; }
	default int getOverrideDefense() { return 0; }
	default int getOverrideDexterity() { return 0; }
	default int getOverrideIntelligence() { return 0; }
	
	default boolean doUpdate() { return false; }
	
	default void update(EntityPlayer player) {}
	
	@Override
	@Nonnull
	default public NBTTagCompound serializeNBT() { return new NBTTagCompound(); }

	@Override
	default public void deserializeNBT(NBTTagCompound nbt) {}
}