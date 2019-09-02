package palaster.gj.jobs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import palaster.gj.api.EnumDomain;
import palaster.gj.api.capabilities.IRPG;
import palaster.gj.api.jobs.IRPGJob;
import palaster.gj.jobs.abilities.GodPowers;

import javax.annotation.Nullable;
import java.util.HashMap;

public class JobGod implements IRPGJob {

	public static final String TAG_STRING_DOMAIN = "gj:GodDomain",
			TAG_MAP_ACTIVE_POWERS = "gj:GodActivePower";

	private EnumDomain domain = null;
	private HashMap<String, Boolean> godPowers = new HashMap<>();

	public JobGod() {
		for(String godPower : GodPowers.GOD_POWERS)
			godPowers.put(godPower, false);
	}

	public void setDomain(EnumDomain domain) { this.domain = domain; }

	@Nullable
	public EnumDomain getDomain() { return domain; }

	public boolean isPowerActive(String power) { return godPowers.getOrDefault(power, false); }

	public void setPower(String power, boolean activate) { godPowers.put(power, activate); }

	@Override
	public String getJobName() { return "gj.job.god"; }

	@Override
	public boolean canLeave() { return false; }

	@Override
	public boolean replaceMagick() { return true; }

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		if(domain != null)
			nbt.setString(TAG_STRING_DOMAIN, domain.toString());
		if(!godPowers.isEmpty())
			for(String power : godPowers.keySet())
				nbt.setBoolean(TAG_MAP_ACTIVE_POWERS + "_" + power, godPowers.getOrDefault(power, false));
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		if(nbt.hasKey(TAG_STRING_DOMAIN))
			domain = EnumDomain.valueOf(nbt.getString(TAG_STRING_DOMAIN));
		if(!godPowers.isEmpty())
			for(String power : godPowers.keySet())
				godPowers.put(power, nbt.getBoolean(TAG_MAP_ACTIVE_POWERS + "_" + power));
	}
}
