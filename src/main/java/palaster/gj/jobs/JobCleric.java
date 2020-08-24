package palaster.gj.jobs;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.gj.api.capabilities.IRPG;
import palaster.gj.api.jobs.IRPGJob;

public class JobCleric implements IRPGJob {

	public static final String TAG_INT_SS = "gj:ClericSpellSlots";
	
	private int spellSlots = 0;
	
	public boolean canCastSpell() { return spellSlots > 0; }
	
	public int getSpellSlots() { return spellSlots; }
	
	public void castSpell() { spellSlots--; }
	
	public void resetSpellSlots(IRPG rpg) { spellSlots = rpg.getConstitution() > 0 ? (rpg.getIntelligence() + 10) / 5 : 1; }

	@Override
	public String getJobName() { return "gj.job.cleric"; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void drawExtraInformation(EntityPlayer player, FontRenderer fontRendererObj, int suggestedX, int suggestedY, int mouseX, int mouseY) {
		fontRendererObj.drawString(I18n.format("gj.job.spellSlots") + ": " + spellSlots, suggestedX, suggestedY, 4210752);	
	}

	@Override
	public boolean replaceMagick() { return true; }

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = IRPGJob.super.serializeNBT();
		nbt.setInteger(TAG_INT_SS, spellSlots);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		spellSlots = nbt.getInteger(TAG_INT_SS);
	}
}
