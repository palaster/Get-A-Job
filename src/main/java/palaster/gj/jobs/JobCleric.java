package palaster.gj.jobs;

import javax.annotation.Nullable;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.gj.api.jobs.RPGJobBase;

public class JobCleric extends RPGJobBase {

	public static final String TAG_STRING_DOMAIN = "gj:ClericDomain",
			TAG_LONG_BLOCKPOS = "gj:ClericAltarBlockPos";

	private EnumDomain domain = null;

	private BlockPos altar = null;

	public void setDomain(EnumDomain domain) { this.domain = domain; }

	@Nullable
	public EnumDomain getDomain() { return domain; }

	public void setAltar(BlockPos pos) {
		if(pos != null)
			this.altar = pos;
	}

	@Nullable
	public BlockPos getAltar() { return altar; }

	@Override
	public String getJobName() { return "gj.job.cleric"; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void drawExtraInformation(EntityPlayer player, FontRenderer fontRendererObj, int suggestedX, int suggestedY, int mouseX, int mouseY) {
		if(altar != null)
			fontRendererObj.drawString(I18n.format("gj.job.cleric.domain") + ":" + altar.toString(), suggestedX, suggestedY, 4210752);
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
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		if(nbt.hasKey(TAG_STRING_DOMAIN))
			domain = EnumDomain.valueOf(nbt.getString(TAG_STRING_DOMAIN));
		if(nbt.hasKey(TAG_LONG_BLOCKPOS))
			altar = BlockPos.fromLong(nbt.getLong(TAG_LONG_BLOCKPOS));
	}

	public static enum EnumDomain implements IStringSerializable {
		NONE("none"),
		CREATION("creation"),
		COMMUNITY("community");

		private String name;

		private EnumDomain(String name) {
			this.name = name;
		}

		@Override
		public String getName() { return name; }

		@Override
		public String toString() { return getName(); }
	}
}
