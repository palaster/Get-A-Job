package palaster.gj.jobs;

import java.util.ArrayList;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.jobs.IRPGJob;
import palaster.gj.jobs.abilities.Abilities;
import palaster.gj.network.client.PacketUpdateRPG;

public class BloodSorcererJob implements IRPGJob {

	public static final String NBT_BLOOD_CURRENT = "gj:blood_sorcerer:blood_current",
			NBT_BLOOD_MAX = "gj:blood_sorcerer:blood_max",
			NBT_BLOOD_REGEN = "gj:blood_sorcerer:blood_regen";
			
	public static final UUID HEALTH_ID = UUID.fromString("4de67577-1e12-4dad-8ab4-3ee5d2cf3cc2");

	private int bloodCurrent = 0,
			bloodMax = 0,
			bloodRegen = 0,
			timer = 0;
	
	private boolean isInvisibleDueToDarkStalker = false;

	public BloodSorcererJob() { this(0, 2000); }

	public BloodSorcererJob(int bloodCurrent, int bloodMax) {
		this.bloodCurrent = bloodCurrent;
		this.bloodMax = bloodMax;
	}

	public void addBlood(Player player, int amount) { setBloodCurrent(player, getBloodCurrent() + amount); }

	public void removeBlood(Player player, int amount) { setBloodCurrent(player, getBloodCurrent() - amount); }
	
	public void setBloodCurrent(Player player, int amount) {
		if(amount < 0) {
			player.hurt(DamageSource.STARVE, (float) -amount / 100.0f);
			amount = 0;
		}
		bloodCurrent = amount >= bloodMax ? bloodMax : amount <= 0 ? 0 : amount;
		PacketUpdateRPG.syncPlayerRPGCapabilitiesToClient(player);
	}

	public void setBloodMax(Player player, int amount) {
		bloodMax = amount > 0 ? amount : 0;
		PacketUpdateRPG.syncPlayerRPGCapabilitiesToClient(player);
	}

	public void setBloodRegen(Player player, int amount) {
		bloodRegen = amount > 0 ? amount : 0;
		updatePlayerAttributes(player);
		PacketUpdateRPG.syncPlayerRPGCapabilitiesToClient(player);
	}

	public int getBloodCurrent() { return bloodCurrent; }
	public int getBloodMax() { return bloodMax; }
	public int getBloodRegen() { return bloodRegen; }
	
	@Override
	public String getJobName() { return "gj.job.blood_sorcerer"; }
	
	@Override
	public void leaveJob(@Nullable Player player) {
		if(player == null)
			return;
		AttributeInstance attributeInstance = player.getAttributes().getInstance(Attributes.MAX_HEALTH);
		if(attributeInstance != null && attributeInstance.getModifier(HEALTH_ID) != null)
			attributeInstance.removeModifier(attributeInstance.getModifier(HEALTH_ID));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addExtraInformation(final ArrayList<Component> components) {
		components.add(Component.translatable("gj.job.blood_sorcerer.blood", bloodCurrent, bloodMax));
		components.add(Component.translatable("gj.job.blood_sorcerer.blood_regen", bloodRegen));
		components.add(Component.translatable("gj.job.blood_sorcerer.abilities.dark_stalker"));
	}

	@Override
	public void updatePlayerAttributes(@Nullable Player player) {
		if(player == null)
			return;
		AttributeInstance attributeInstance = player.getAttributes().getInstance(Attributes.MAX_HEALTH);
		if(bloodRegen <= 0) {
			if(attributeInstance != null && attributeInstance.getModifier(HEALTH_ID) != null)
				attributeInstance.removeModifier(attributeInstance.getModifier(HEALTH_ID));
		} else {
			if(attributeInstance != null) {
				if(attributeInstance.getModifier(HEALTH_ID) != null)
					attributeInstance.removeModifier(attributeInstance.getModifier(HEALTH_ID));
				attributeInstance.addTransientModifier(new AttributeModifier(HEALTH_ID, "gj.job.blood_sorcerer.blood_regen", -1.0D * bloodRegen, AttributeModifier.Operation.ADDITION));
			}
			// attributeInstance.addPermanentModifier();
		}
	}

	@Override
	public boolean replaceMagick() { return true; }

	@Override
	public boolean doUpdate() { return true; }

	@Override
	public void update(IRPG rpg, Player player) {
		if(player.isShiftKeyDown() && !player.isInvisible())
			if(rpg != null && Abilities.DARK_STALKER.isAvailable(rpg)) {
				player.setInvisible(true);
				isInvisibleDueToDarkStalker = true;
			}
		if(!player.isShiftKeyDown() && player.isInvisible() && isInvisibleDueToDarkStalker) {
			player.setInvisible(false);
			isInvisibleDueToDarkStalker = false;
		}
		if(timer >= 100) {
			if(bloodRegen > 0)
				addBlood(player, bloodRegen * 5);
			timer = 0;
		} else
			timer++;
	}

	@Nonnull
	@Override
	public Tag serializeNBT() {
		Tag nbt = IRPGJob.super.serializeNBT();
		if(nbt instanceof CompoundTag) {
			CompoundTag cNBT = (CompoundTag) nbt;
			cNBT.putInt(NBT_BLOOD_CURRENT, bloodCurrent);
			cNBT.putInt(NBT_BLOOD_MAX, bloodMax);
			cNBT.putInt(NBT_BLOOD_REGEN, bloodRegen);
		}
		return nbt;
	}


	@Override
	public void deserializeNBT(Tag nbt) {
		IRPGJob.super.deserializeNBT(nbt);
		if(nbt instanceof CompoundTag) {
			CompoundTag cNBT = (CompoundTag) nbt;
			bloodCurrent = cNBT.getInt(NBT_BLOOD_CURRENT);
			bloodMax = cNBT.getInt(NBT_BLOOD_MAX);
			bloodRegen = cNBT.getInt(NBT_BLOOD_REGEN);
		}
	}
}