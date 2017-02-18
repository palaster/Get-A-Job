package palaster.gj.entities.jobs;

import javax.annotation.Nullable;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IRPGJob extends INBTSerializable<NBTTagCompound> {
	
	boolean canLeave();

	@SideOnly(Side.CLIENT)
	void drawExtraInformationBase(@Nullable EntityPlayer player, FontRenderer fontRendererObj, int suggestedX, int suggestedY, int mouseX, int mouseY);

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
	void drawExtraInformation(@Nullable EntityPlayer player, FontRenderer fontRendererObj, int suggestedX, int suggestedY, int mouseX, int mouseY);

	String getCareerName();

	void leaveJob(@Nullable EntityPlayer player);
	
	boolean replaceMagick();
	
	boolean overrideConstitution();
	boolean overrideStrength();
	boolean overrideDefense();
	boolean overrideDexterity();
	boolean overrideIntelligence();
	
	int getOverrideConstitution();
	int getOverrideStrength();
	int getOverrideDefense();
	int getOverrideDexterity();
	int getOverrideIntelligence();
}