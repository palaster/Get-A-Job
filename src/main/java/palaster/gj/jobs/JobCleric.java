package palaster.gj.jobs;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.gj.api.EnumDomain;
import palaster.gj.api.capabilities.IRPG;
import palaster.gj.api.jobs.IRPGJob;
import palaster.gj.blocks.BlockAltar;

public class JobCleric implements IRPGJob {

	public static final String TAG_STRING_DOMAIN = "gj:ClericDomain",
			TAG_LONG_BLOCKPOS = "gj:ClericAltarBlockPos",
			TAG_INT_SS = "gj:ClericSpellSlots";

	private EnumDomain domain = null;

	private BlockPos altar = null;
	
	private int spellSlots = 0;

	public void setDomain(EnumDomain domain) { this.domain = domain; }

	@Nullable
	public EnumDomain getDomain() { return domain; }

	public void setAltar(BlockPos pos) {
		if(pos != null)
			this.altar = pos;
	}

	@Nullable
	public BlockPos getAltar() { return altar; }
	
	public boolean canCastSpell() { return spellSlots > 0; }
	
	public int getSpellSlots() { return spellSlots; }
	
	public void castSpell() { spellSlots--; }
	
	public void resetSpellSlots(IRPG rpg) { spellSlots = rpg.getConstitution() > 0 ? (rpg.getIntelligence() + 10) / 5 : 1; }

	@Override
	public String getJobName() { return "gj.job.cleric"; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void drawExtraInformation(EntityPlayer player, FontRenderer fontRendererObj, int suggestedX, int suggestedY, int mouseX, int mouseY) {
		if(altar != null) {
			fontRendererObj.drawString("Position: " + altar.getX() + ", " + altar.getY() + ", " + altar.getZ(), suggestedX, suggestedY, 4210752);
			IBlockState bs = player.world.getBlockState(altar);
			if (bs != null && bs.getBlock() instanceof BlockAltar && bs.getValue(BlockAltar.DOMAIN_TYPE) != null)
				fontRendererObj.drawString(I18n.format("gj.job.cleric.domain") + ": " + bs.getValue(BlockAltar.DOMAIN_TYPE), suggestedX, suggestedY + 10, 4210752);
			else
				fontRendererObj.drawString(I18n.format("gj.job.cleric.domain") + ": ERROR", suggestedX, suggestedY + 10, 4210752);
			fontRendererObj.drawString(I18n.format("gj.job.spellSlots") + ": " + spellSlots, suggestedX, suggestedY + 20, 4210752);
		} else
			fontRendererObj.drawString(I18n.format("gj.job.spellSlots") + ": " + spellSlots, suggestedX, suggestedY, 4210752);	
	}

	@Override
	public boolean replaceMagick() { return true; }

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		if(domain != null)
			nbt.setString(TAG_STRING_DOMAIN, domain.toString());
		if(altar != null)
			nbt.setLong(TAG_LONG_BLOCKPOS, altar.toLong());
		nbt.setInteger(TAG_INT_SS, spellSlots);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		if(nbt.hasKey(TAG_STRING_DOMAIN))
			domain = EnumDomain.valueOf(nbt.getString(TAG_STRING_DOMAIN));
		if(nbt.hasKey(TAG_LONG_BLOCKPOS))
			altar = BlockPos.fromLong(nbt.getLong(TAG_LONG_BLOCKPOS));
		spellSlots = nbt.getInteger(TAG_INT_SS);
	}
}
