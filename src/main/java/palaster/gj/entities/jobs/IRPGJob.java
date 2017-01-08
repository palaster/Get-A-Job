package palaster.gj.entities.jobs;

import javax.annotation.Nullable;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IRPGJob extends INBTSerializable<NBTTagCompound> {
	
	boolean canLeave();
	
	@SideOnly(Side.CLIENT) // TODO: Add starting x and y for drawing.
	void drawExtraInformation(GuiContainer guiContainer, @Nullable EntityPlayer player, FontRenderer fontRendererObj, int mouseX, int mouseY);

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