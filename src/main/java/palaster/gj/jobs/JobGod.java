package palaster.gj.jobs;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.jobs.IRPGJob;

public class JobGod implements IRPGJob {
	@Override
	public String getJobName() { return "gj.job.god"; }

	@Override
	public boolean canLeave() { return false; }

	@Override
	public boolean shouldDrawExtraInformation() { return false; }
	
	@Override
	public boolean replaceMagick() { return true; }
	
	@Override
	public boolean overrideConstitution() { return true; }
	
	@Override
	public boolean overrideStrength() { return true; }
	
	@Override
	public boolean overrideDefense() { return true; }
	
	@Override
	public boolean overrideDexterity() { return true; }
	
	@Override
	public boolean overrideIntelligence() { return true; }
	
	@Override
	public boolean doUpdate() { return true; }

	@Override
	public void update(IRPG rpg, Player player) {
		if(player.getFoodData().getFoodLevel() > 0) {
			player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
			player.removeEffect(MobEffects.DIG_SLOWDOWN);
			player.removeEffect(MobEffects.WEAKNESS);
		} else {
			if(!player.hasEffect(MobEffects.MOVEMENT_SLOWDOWN))
				player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1, 0, true, false));
			if(!player.hasEffect(MobEffects.DIG_SLOWDOWN))
				player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 1, 0, true, false));
			if(!player.hasEffect(MobEffects.WEAKNESS))
				player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 1, 0, true, false));
		}
	}
}
