package palaster.gj.entities.jobs;

import net.minecraft.entity.player.EntityPlayer;

public class JobCleric extends RPGJobBase {

	@Override
	public String getCareerName() { return "gj.job.cleric"; }

	@Override
	public void leaveJob(EntityPlayer player) {}
	
	@Override
	public boolean replaceMagick() { return true; }
}
