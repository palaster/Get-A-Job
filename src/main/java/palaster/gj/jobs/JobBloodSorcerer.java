package palaster.gj.jobs;

import java.util.UUID;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.jobs.IRPGJob;
import palaster.gj.jobs.abilities.Abilities;
import palaster.gj.network.client.PacketUpdateRPG;

public class JobBloodSorcerer implements IRPGJob {

	public static final String NBT_BLOOD_CURRENT = "gj:blood_sorcerer:blood_current",
			NBT_BLOOD_MAX = "gj:blood_sorcerer:blood_max",
			NBT_BLOOD_REGEN = "gj:blood_sorcerer:blood_regen";
			
	public static final UUID HEALTH_ID = UUID.fromString("4de67577-1e12-4dad-8ab4-3ee5d2cf3cc2");

	private int bloodCurrent = 0,
			bloodMax = 0,
			bloodRegen = 0,
			timer = 0;
	
	private boolean isInvisibleDueToDarkStalker = false;

	public JobBloodSorcerer() { this(0, 2000); }

	public JobBloodSorcerer(int bloodCurrent, int bloodMax) {
		this.bloodCurrent = bloodCurrent;
		this.bloodMax = bloodMax;
	}

	public void addBlood(PlayerEntity player, int amt) { setBloodCurrent(player, getBloodCurrent() + amt); }
	
	public void setBloodCurrent(PlayerEntity player, int amt) {
		bloodCurrent = amt >= bloodMax ? bloodCurrent : amt <= 0 ? 0 : amt;
		PacketUpdateRPG.syncPlayerRPGCapabilitiesToClient(player);
	}

	public void setBloodMax(PlayerEntity player, int amt) {
		bloodMax = amt > 0 ? amt : 0;
		PacketUpdateRPG.syncPlayerRPGCapabilitiesToClient(player);
	}

	public void setBloodRegen(PlayerEntity player, int amt) {
		bloodRegen = amt > 0 ? amt : 0;
		updatePlayerAttributes(player);
		PacketUpdateRPG.syncPlayerRPGCapabilitiesToClient(player);
	}

	public int getBloodCurrent() { return bloodCurrent; }
	public int getBloodMax() { return bloodMax; }
	public int getBloodRegen() { return bloodRegen; }
	
	@Override
	public String getJobName() { return "gj.job.bloodSorcerer"; }
	
	@Override
	public void leaveJob(PlayerEntity player) {
		ModifiableAttributeInstance attributeInstance = player.getAttributes().getInstance(Attributes.MAX_HEALTH);
		if(attributeInstance.getModifier(HEALTH_ID) != null)
			attributeInstance.removeModifier(attributeInstance.getModifier(HEALTH_ID));
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void drawExtraInformation(MatrixStack ms, FontRenderer font, int mouseX, int mouseY, PlayerEntity player, int suggestedX, int suggestedY) {
		//font.draw(ms, new StringTextComponent(I18n.get("gj.job.bloodSorcerer.blood", bloodCurrent, bloodMax)), suggestedX, suggestedY, 4210752);
		font.draw(ms, new TranslationTextComponent("gj.job.bloodSorcerer.blood", bloodCurrent, bloodMax), suggestedX, suggestedY, 4210752);
	}

	@Override
	public void updatePlayerAttributes(PlayerEntity player) {
		if(bloodRegen <= 0) {
			ModifiableAttributeInstance attributeInstance = player.getAttributes().getInstance(Attributes.MAX_HEALTH);
			if(attributeInstance.getModifier(HEALTH_ID) != null)
				attributeInstance.removeModifier(attributeInstance.getModifier(HEALTH_ID));
		} else {
			ModifiableAttributeInstance attributeInstance = player.getAttributes().getInstance(Attributes.MAX_HEALTH);
			if(attributeInstance.getModifier(HEALTH_ID) != null)
				attributeInstance.removeModifier(attributeInstance.getModifier(HEALTH_ID));
			attributeInstance.addTransientModifier(new AttributeModifier(HEALTH_ID, "gj.job.bloodSorcerer.regen", bloodRegen * -1, AttributeModifier.Operation.ADDITION));
			// attributeInstance.addPermanentModifier();
		}
	}

	@Override
	public boolean replaceMagick() { return true; }

	@Override
	public boolean doUpdate() { return true; }

	@Override
	public void update(IRPG rpg, PlayerEntity player) {
		if(player.isShiftKeyDown() && !player.isInvisible())
			if(Abilities.DARK_STALKER.isAvailable(rpg)) {
				player.setInvisible(true);
				isInvisibleDueToDarkStalker = true;
			}
		if(!player.isShiftKeyDown() && player.isInvisible() && isInvisibleDueToDarkStalker) {
			player.setInvisible(false);
			isInvisibleDueToDarkStalker = false;
		}
		if(timer >= 100) {
			if(bloodRegen > 0)
				addBlood(player, bloodRegen);
			timer = 0;
		} else
			timer++;
	}

	@Override
	public INBT serializeNBT() {
		INBT nbt = IRPGJob.super.serializeNBT();
		if(nbt instanceof CompoundNBT) {
			CompoundNBT cNBT = (CompoundNBT) nbt;
			cNBT.putInt(NBT_BLOOD_CURRENT, bloodCurrent);
			cNBT.putInt(NBT_BLOOD_MAX, bloodMax);
			cNBT.putInt(NBT_BLOOD_REGEN, bloodRegen);
		}
		return nbt;
	}


	@Override
	public void deserializeNBT(INBT nbt) {
		IRPGJob.super.deserializeNBT(nbt);
		if(nbt instanceof CompoundNBT) {
			CompoundNBT cNBT = (CompoundNBT) nbt;
			bloodCurrent = cNBT.getInt(NBT_BLOOD_CURRENT);
			bloodMax = cNBT.getInt(NBT_BLOOD_MAX);
			bloodRegen = cNBT.getInt(NBT_BLOOD_REGEN);
		}
	}
}