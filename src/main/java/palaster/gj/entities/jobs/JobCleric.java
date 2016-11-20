package palaster.gj.entities.jobs;

import net.minecraft.entity.player.EntityPlayer;
import palaster.gj.api.rpg.RPGJobBase;

public class JobCleric extends RPGJobBase {

	@Override
	public void leaveJob(EntityPlayer player) {}

	@Override
	public String getCareerName() { return "gj.job.cleric"; }
}
