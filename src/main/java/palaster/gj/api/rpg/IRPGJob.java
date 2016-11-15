package palaster.gj.api.rpg;

import javax.annotation.Nullable;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IRPGJob extends INBTSerializable<NBTTagCompound> {

	void leaveJob(@Nullable EntityPlayer player);
	
	String getCareerName();
	
	@SideOnly(Side.CLIENT) // TODO: Add starting x and y for drawing.
	void drawExtraInformation(GuiContainer guiContainer, @Nullable EntityPlayer player, FontRenderer fontRendererObj, int mouseX, int mouseY);
}
