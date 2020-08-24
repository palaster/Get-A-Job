package palaster.gj.jobs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import palaster.gj.api.capabilities.IRPG;
import palaster.gj.api.jobs.IRPGJob;

public class JobGod implements IRPGJob {

	public static final String TAG_BOOLEAN_DEBUFF_HUNGER = "gj:HasDebuffFromHunger";
	
	public boolean hasDebuffFromHunger = false;
	
	@Override
	public String getJobName() { return "gj.job.god"; }

	@Override
	public boolean canLeave() { return false; }

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
	public void update(IRPG rpg, EntityPlayer player) {
		if(hasDebuffFromHunger) {
			if(player.getFoodStats().getFoodLevel() > 0) {
				hasDebuffFromHunger = false;
				player.removePotionEffect(Potion.getPotionById(2));
				player.removePotionEffect(Potion.getPotionById(4));
				player.removePotionEffect(Potion.getPotionById(18));
			} else {
				PotionEffect slownessPotionEffect = player.getActivePotionEffect(Potion.getPotionById(2));
				PotionEffect miningFatiguePotionEffect = player.getActivePotionEffect(Potion.getPotionById(4));
				PotionEffect weaknessPotionEffect = player.getActivePotionEffect(Potion.getPotionById(18));
				if(slownessPotionEffect == null)
					player.addPotionEffect(new PotionEffect(Potion.getPotionById(2), 1, 0, true, false));
				if(miningFatiguePotionEffect == null)
					player.addPotionEffect(new PotionEffect(Potion.getPotionById(4), 1, 0, true, false));
				if(weaknessPotionEffect == null)
					player.addPotionEffect(new PotionEffect(Potion.getPotionById(18), 1, 0, true, false));
			}
		} else if(player.getFoodStats().getFoodLevel() <= 0) {
			hasDebuffFromHunger = true;
			PotionEffect slownessPotionEffect = player.getActivePotionEffect(Potion.getPotionById(2));
			PotionEffect miningFatiguePotionEffect = player.getActivePotionEffect(Potion.getPotionById(4));
			PotionEffect weaknessPotionEffect = player.getActivePotionEffect(Potion.getPotionById(18));
			if(slownessPotionEffect == null)
				player.addPotionEffect(new PotionEffect(Potion.getPotionById(2), 1, 0, true, false));
			if(miningFatiguePotionEffect == null)
				player.addPotionEffect(new PotionEffect(Potion.getPotionById(4), 1, 0, true, false));
			if(weaknessPotionEffect == null)
				player.addPotionEffect(new PotionEffect(Potion.getPotionById(18), 1, 0, true, false));
		}
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = IRPGJob.super.serializeNBT();
		nbt.setBoolean(TAG_BOOLEAN_DEBUFF_HUNGER, hasDebuffFromHunger);
		return nbt;
	}


	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		IRPGJob.super.deserializeNBT(nbt);
		hasDebuffFromHunger = nbt.getBoolean(TAG_BOOLEAN_DEBUFF_HUNGER);
	}
}
