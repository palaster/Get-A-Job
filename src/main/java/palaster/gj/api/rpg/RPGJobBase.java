package palaster.gj.api.rpg;

import javax.annotation.Nullable;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class RPGJobBase implements IRPGJob {

	@Override
	public NBTTagCompound serializeNBT() { return new NBTTagCompound(); }

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {}

	@Override
	@SideOnly(Side.CLIENT)
	public void drawExtraInformation(GuiContainer guiContainer, @Nullable EntityPlayer player, FontRenderer fontRendererObj, int mouseX, int mouseY) { fontRendererObj.drawString(I18n.format("gj.career.additionalInfo") + ":", 6, 90, 4210752); }
}
