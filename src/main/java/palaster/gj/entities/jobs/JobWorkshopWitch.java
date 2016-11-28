package palaster.gj.entities.jobs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import palaster.gj.api.rpg.RPGJobBase;

public class JobWorkshopWitch extends RPGJobBase {

	public static final String TAG_BOOLEAN_LEADER = "WorkshopWitchLeader";
	
	private boolean isLeader = false;
	
	public void setLeader(boolean value) { isLeader = value; }
	
	public boolean isLeader() { return isLeader; }
	
	@Override
	public void leaveJob(EntityPlayer player) {}

	@Override
	public String getCareerName() { return "gj.job.workshopWitch"; }

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean(TAG_BOOLEAN_LEADER, isLeader);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) { isLeader = nbt.getBoolean(TAG_BOOLEAN_LEADER); }
}
