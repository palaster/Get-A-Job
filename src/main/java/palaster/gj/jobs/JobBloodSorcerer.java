package palaster.gj.jobs;

import java.util.UUID;

import javax.annotation.Nonnull;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import palaster.gj.api.jobs.RPGJobBase;
import palaster.gj.core.proxy.CommonProxy;

public class JobBloodSorcerer extends RPGJobBase {

	public static final String TAG_INT_BLOOD_CURRENT = "gj:BloodCurrent",
			TAG_INT_BLOOD_MAX = "gj:BloodMax",
			TAG_INT_BLOOD_REGEN = "gj:BloodRegen";
			
	public static final UUID HEALTH_ID = UUID.fromString("4de67577-1e12-4dad-8ab4-3ee5d2cf3cc2");

	private int bloodCurrent = 0,
			bloodMax = 0,
			bloodRegen = 0,
			timer = 0;

	public JobBloodSorcerer() { this(0, 2000); }

	public JobBloodSorcerer(int bloodCurrent, int bloodMax) {
		this.bloodCurrent = bloodCurrent;
		this.bloodMax = bloodMax;
	}

	public void addBlood(EntityPlayer player, int amt) { setBloodCurrent(player, getBloodCurrent() + amt); }
	
	public void setBloodCurrent(EntityPlayer player, int amt) {
		bloodCurrent = amt >= bloodMax ? bloodCurrent : amt <= 0 ? 0 : amt;
		CommonProxy.syncPlayerRPGCapabilitiesToClient(player);
	}

	public void setBloodMax(EntityPlayer player, int amt) {
		bloodMax = amt > 0 ? amt : 0;
		CommonProxy.syncPlayerRPGCapabilitiesToClient(player);
	}

	public void setBloodRegen(EntityPlayer player, int amt) {
		bloodRegen = amt > 0 ? amt : 0;
		updatePlayerAttributes(player);
		CommonProxy.syncPlayerRPGCapabilitiesToClient(player);
	}

	public int getBloodCurrent() { return bloodCurrent; }
	public int getBloodMax() { return bloodMax; }
	public int getBloodRegen() { return bloodRegen; }
	
	@Override
	public String getCareerName() { return "gj.job.bloodSorcerer"; }
	
	@Override
	public void leaveJob(EntityPlayer player) {
		IAttributeInstance iAttributeInstance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
		if(iAttributeInstance.getModifier(HEALTH_ID) != null)
			iAttributeInstance.removeModifier(iAttributeInstance.getModifier(HEALTH_ID));
	}

	@Override
	public void drawExtraInformation(EntityPlayer player, FontRenderer fontRendererObj, int suggestedX, int suggestedY, int mouseX, int mouseY) { fontRendererObj.drawString(net.minecraft.util.text.translation.I18n.translateToLocal("gj.job.bloodSorcerer.blood") + ": " + bloodCurrent + "/ " + bloodMax, suggestedX, suggestedY + 10, 4210752); }

	@Override
	public void updatePlayerAttributes(EntityPlayer player) {
		if(bloodRegen <= 0) {
			IAttributeInstance iAttributeInstance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
			if(iAttributeInstance.getModifier(HEALTH_ID) != null)
				iAttributeInstance.removeModifier(iAttributeInstance.getModifier(HEALTH_ID));
		} else {
			IAttributeInstance iAttributeInstance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
			if(iAttributeInstance.getModifier(HEALTH_ID) != null)
				iAttributeInstance.removeModifier(iAttributeInstance.getModifier(HEALTH_ID));
            iAttributeInstance.applyModifier(new AttributeModifier(HEALTH_ID, "gj.job.bloodSorcerer.regen", bloodRegen * -1, 0));
		}
	}

	@Override
	public boolean replaceMagick() { return true; }

	@Override
	public boolean doUpdate() { return true; }

	@Override
	public void update(EntityPlayer player) {
		if(timer >= 100) {
			if(bloodRegen > 0)
				addBlood(player, bloodRegen);
			timer = 0;
		} else
			timer++;
	}

	@Override
	@Nonnull
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = super.serializeNBT();
		nbt.setInteger(TAG_INT_BLOOD_CURRENT, bloodCurrent);
		nbt.setInteger(TAG_INT_BLOOD_MAX, bloodMax);
		nbt.setInteger(TAG_INT_BLOOD_REGEN, bloodRegen);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		super.deserializeNBT(nbt);
		bloodCurrent = nbt.getInteger(TAG_INT_BLOOD_CURRENT);
		bloodMax = nbt.getInteger(TAG_INT_BLOOD_MAX);
		bloodRegen = nbt.getInteger(TAG_INT_BLOOD_REGEN);
	}
}