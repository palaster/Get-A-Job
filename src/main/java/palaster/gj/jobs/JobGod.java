package palaster.gj.jobs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import palaster.gj.api.jobs.IRPGJob;

public class JobGod implements IRPGJob {

	public static String TAG_BOOLEAN_FALLEN = "gj:GodFallen";

	private boolean isFallen = false;

	public JobGod(EntityPlayer player) {
		if(!player.isSpectator() && !player.capabilities.isCreativeMode)
			if(!player.capabilities.allowFlying || !player.capabilities.disableDamage) {
				player.capabilities.disableDamage = true;
				player.capabilities.allowFlying = true;
				player.sendPlayerAbilities();
			}
	}

	@Override
	public void leaveJob(EntityPlayer player) {
		if(!player.isSpectator() && !player.capabilities.isCreativeMode) {
			player.capabilities.allowFlying = false;
			player.capabilities.isFlying = false;
			player.capabilities.disableDamage = false;
			player.sendPlayerAbilities();
		}
	}

	@Override
	public boolean doUpdate() { return true; }

	@Override
	public void update(EntityPlayer player) {
		if(!player.isSpectator() && !player.capabilities.isCreativeMode)
			if(!player.capabilities.allowFlying || !player.capabilities.disableDamage) {
				player.capabilities.disableDamage = true;
				player.capabilities.allowFlying = true;
				player.sendPlayerAbilities();
			}
		if(isFallen)
			if(player.dimension != -1)
				player.changeDimension(-1);
	}

	@Override
	public String getJobName() { return "gj.job.god"; }

	@Override
	public boolean canLeave() { return false; }

	@Override
	public boolean replaceMagick() { return true; }
	
	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = IRPGJob.super.serializeNBT();
		nbt.setBoolean(TAG_BOOLEAN_FALLEN, isFallen);
		return nbt;
	}
	
	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		IRPGJob.super.deserializeNBT(nbt);
		isFallen = nbt.getBoolean(TAG_BOOLEAN_FALLEN);
	}
}
