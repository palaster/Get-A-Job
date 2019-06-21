package palaster.gj.jobs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import palaster.gj.api.jobs.IRPGJob;

public class JobGod implements IRPGJob {

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
	}

	@Override
	public String getJobName() { return "gj.job.god"; }

	@Override
	public boolean canLeave() { return false; }

	@Override
	public boolean replaceMagick() { return true; }
}
