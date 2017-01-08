package palaster.gj.entities.jobs;

import javax.annotation.Nonnull;
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
	public boolean canLeave() { return true; }

	@Override
	@SideOnly(Side.CLIENT)
	public void drawExtraInformation(GuiContainer guiContainer, @Nullable EntityPlayer player, FontRenderer fontRendererObj, int mouseX, int mouseY) { fontRendererObj.drawString(I18n.format("gj.career.additionalInfo") + ":", 6, 90, 4210752); }

	@Override
	public void leaveJob(EntityPlayer player) {}

	@Override
	public boolean replaceMagick() { return false; }

	@Override
	public boolean overrideConstitution() { return false; }
	@Override
	public boolean overrideStrength() { return false; }
	@Override
	public boolean overrideDefense() { return false; }
	@Override
	public boolean overrideDexterity() { return false; }
	@Override
	public boolean overrideIntelligence() { return false; }

	@Override
	public int getOverrideConstitution() { return 0; }
	@Override
	public int getOverrideStrength() { return 0; }
	@Override
	public int getOverrideDefense() { return 0; }
	@Override
	public int getOverrideDexterity() { return 0; }
	@Override
	public int getOverrideIntelligence() { return 0; }

	@Override
	public String toString() { return getCareerName(); }

	@Override
	@Nonnull
	public NBTTagCompound serializeNBT() { return new NBTTagCompound(); }

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {}
}
