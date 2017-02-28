package palaster.gj.jobs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IStringSerializable;
import palaster.gj.api.jobs.RPGJobBase;

public class JobCleric extends RPGJobBase {

	public static final String TAG_STRING_DOMAIN = "gj:Domain";

	private EnumDomain domain = null;
	
	public void setDomain(EnumDomain domain) { this.domain = domain; }
	
	public EnumDomain getDomain() { return domain; }

	@Override
	public String getCareerName() { return "gj.job.cleric"; }

	@Override
	public void leaveJob(EntityPlayer player) {}

	@Override
	public boolean replaceMagick() { return true; }
	
	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		if(domain != null)
			nbt.setString(TAG_STRING_DOMAIN, domain.toString());
		return nbt;
	}
	
	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		if(nbt.hasKey(TAG_STRING_DOMAIN))
			domain = EnumDomain.valueOf(nbt.getString(TAG_STRING_DOMAIN));
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
