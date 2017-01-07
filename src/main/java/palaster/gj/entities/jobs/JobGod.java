package palaster.gj.entities.jobs;

import net.minecraft.entity.player.EntityPlayer;
import palaster.gj.api.rpg.RPGJobBase;

public class JobGod extends RPGJobBase {

	public JobGod(EntityPlayer player) {
		if(!player.isSpectator() && !player.capabilities.isCreativeMode)
			if(!player.capabilities.allowFlying) {
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
	public boolean canLeave() { return false; }

	@Override
	public String getCareerName() { return "gj.job.god"; }	
}
