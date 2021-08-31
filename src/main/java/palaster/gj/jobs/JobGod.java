package palaster.gj.jobs;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
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
	public void update(IRPG rpg, PlayerEntity player) {
		if(player.getFoodData().getFoodLevel() > 0) {
			player.removeEffect(Effects.MOVEMENT_SLOWDOWN);
			player.removeEffect(Effects.DIG_SLOWDOWN);
			player.removeEffect(Effects.WEAKNESS);
		} else {
			if(!player.hasEffect(Effects.MOVEMENT_SLOWDOWN))
				player.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 1, 0, true, false));
			if(!player.hasEffect(Effects.DIG_SLOWDOWN))
				player.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, 1, 0, true, false));
			if(!player.hasEffect(Effects.WEAKNESS))
				player.addEffect(new EffectInstance(Effects.WEAKNESS, 1, 0, true, false));
		}
	}
}
