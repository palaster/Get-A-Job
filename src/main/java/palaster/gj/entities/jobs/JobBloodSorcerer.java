package palaster.gj.entities.jobs;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class JobBloodSorcerer extends RPGJobBase {

	public static final String TAG_INT_BLOOD_CURRENT = "gj:BloodCurrent",
			TAG_INT_BLOOD_MAX = "gj:BloodMax",
			TAG_INT_BLOOD_REGEN = "gj:BloodRegen";

	private int bloodCurrent = 0,
	bloodMax = 0,
	bloodRegen = 0;
	
	public JobBloodSorcerer() { this(0, 2000); }
	
	public JobBloodSorcerer(int bloodCurrent, int bloodMax) {
		this.bloodCurrent = bloodCurrent;
		this.bloodMax = bloodMax;
	}
	
	public void addBlood(int amt) {
		if(bloodCurrent + amt >= bloodMax)
			bloodCurrent = bloodMax;
		if(bloodCurrent + amt <= 0)
			bloodCurrent = 0;
		else
			bloodCurrent += amt;
	}
	
	public void setBloodCurrent(int amt) {
		if(amt >= getBloodMax())
			bloodCurrent = getBloodCurrent();
		else if(amt <= 0)
			bloodCurrent = 0;
		else
			bloodCurrent = amt;
	}
	
	public void setBloodMax(int amt) { bloodMax = amt > 0 ? amt : 0; }
	
	public void setBloodRegen(int amt) { bloodRegen = amt > 0 ? amt : 0; }

	public int getBloodCurrent() { return bloodCurrent; }
	public int getBloodMax() { return bloodMax; }
	public int getBloodRegen() { return bloodRegen; }

	@Override
	public String getCareerName() { return "gj.job.bloodSorcerer"; }

	@Override
	public void leaveJob(EntityPlayer player) {}
	
	@Override
	public boolean replaceMagick() { return true; }
	
	@Override
	@Nonnull
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = super.serializeNBT();
		nbt.setInteger(TAG_INT_BLOOD_CURRENT, bloodCurrent);
		nbt.setInteger(TAG_INT_BLOOD_MAX, bloodMax);
		nbt.setInteger(TAG_INT_BLOOD_REGEN, bloodRegen);
		return nbt;
	}
	
	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		super.deserializeNBT(nbt);
		bloodCurrent = nbt.getInteger(TAG_INT_BLOOD_CURRENT);
		bloodMax = nbt.getInteger(TAG_INT_BLOOD_MAX);
		bloodRegen = nbt.getInteger(TAG_INT_BLOOD_REGEN);
	}
}