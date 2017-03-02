package palaster.gj.api.jobs;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class RPGJobBase implements IRPGJob {

	@Override
	@SideOnly(Side.CLIENT)
	public void drawExtraInformation(EntityPlayer player, FontRenderer fontRendererObj, int suggestedX, int suggestedY, int mouseX, int mouseY) {}

	@Override
	public String toString() { return getJobName(); }
}
