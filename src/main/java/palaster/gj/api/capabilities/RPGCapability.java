package palaster.gj.api.capabilities;

import java.lang.ref.SoftReference;
import java.util.UUID;
import java.util.concurrent.Callable;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import palaster.gj.api.jobs.IRPGJob;
import palaster.gj.core.proxy.CommonProxy;
import palaster.gj.jobs.JobGod;
import palaster.libpal.core.helpers.PlayerHelper;

public class RPGCapability {

public static class RPGCapabilityDefault implements IRPG {

		public static final String TAG_STRING_CLASS = "RPGJobClass",
			TAG_JOB = "RPGJob",
			TAG_INT_CONSTITUTION = "ConstitutionInteger",
			TAG_INT_STRENGTH = "StrengthInteger",
			TAG_INT_DEFENSE = "DefenseInteger",
			TAG_INT_DEXTERITY = "DexterityInteger",
			TAG_INT_INTELLIGENCE = "IntelligenceInteger",
			TAG_INT_EXPERIENCE_SAVED = "ExperienceSaved",
			TAG_INT_MAGICK = "MagickInteger";
		public static final int MAX_LEVEL = 99;

		public static final UUID HEALTH_ID = UUID.fromString("c6531f9f-b737-4cb6-aea1-fd01c25606be"),
			DEXTERITY_ID = UUID.fromString("d0ff0df9-9f9c-491d-9d9c-5997b5d5ba22");

		private IRPGJob job = null;
		
		private int constitution = 0,
			strength = 0,
			defense = 0,
			dexterity = 0,
			intelligence = 0,
			experienceSaved = 0,
			magick = 0;

		@Override
		public void setConstitution(EntityPlayer player, int amt) {
			if(amt > MAX_LEVEL)
				constitution = MAX_LEVEL;
			else if(amt <= 0)
				constitution = 0;
			else
				constitution = amt;
			constitution = job != null ? job.overrideConstitution() ? job.getOverrideConstitution() : constitution : constitution;
			if(constitution <= 0) {
    			IAttributeInstance iAttributeInstance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
    			if(iAttributeInstance.getModifier(HEALTH_ID) != null)
    				iAttributeInstance.removeModifier(iAttributeInstance.getModifier(HEALTH_ID));
    		} else {
    			IAttributeInstance iAttributeInstance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
    			if(iAttributeInstance.getModifier(HEALTH_ID) != null)
    				iAttributeInstance.removeModifier(iAttributeInstance.getModifier(HEALTH_ID));
                iAttributeInstance.applyModifier(new AttributeModifier(HEALTH_ID, "gj.rpg.constitution", constitution * .4, 0));
    		}
		}

		@Override
		public void setStrength(int amt) {
			if(amt > MAX_LEVEL)
				strength = MAX_LEVEL;
			else if(amt <= 0)
				strength = 0;
			else
				strength = amt;
			strength = job != null ? job.overrideStrength() ? job.getOverrideStrength() : strength : strength;
		}
		
		@Override
		public void setDefense(int amt) {
			if(amt > MAX_LEVEL)
				defense = MAX_LEVEL;
			else if(amt <= 0)
				defense = 0;
			else
				defense = amt;
			defense = job != null ? job.overrideDefense() ? job.getOverrideDefense() : defense : defense;
		}
		
		@Override
		public void setDexterity(EntityPlayer player, int amt) {
			if(amt > MAX_LEVEL)
				dexterity = MAX_LEVEL;
			else if(amt <= 0)
				dexterity = 0;
			else
				dexterity = amt;
			dexterity = job != null ? job.overrideDexterity() ? job.getOverrideDexterity() : dexterity : dexterity;
			if(dexterity <= 0) {
    			IAttributeInstance iAttributeInstance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED);
    			if(iAttributeInstance.getModifier(DEXTERITY_ID) != null)
                	iAttributeInstance.removeModifier(iAttributeInstance.getModifier(DEXTERITY_ID));
    		} else {
    			IAttributeInstance iAttributeInstance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED);
    			if(iAttributeInstance.getModifier(DEXTERITY_ID) != null)
                	iAttributeInstance.removeModifier(iAttributeInstance.getModifier(DEXTERITY_ID));
                iAttributeInstance.applyModifier(new AttributeModifier(DEXTERITY_ID, "gj.rpg.dexterity", dexterity * .008, 0));
    		}
		}

		@Override
		public void setIntelligence(int amt) {
			if(amt > MAX_LEVEL)
				intelligence = MAX_LEVEL;
			else if(amt <= 0)
				intelligence = 0;
			else
				intelligence = amt;
			intelligence = job != null ? job.overrideIntelligence() ? job.getOverrideIntelligence() : intelligence : intelligence;
		}

		@Override
		public void setExperienceSaved(int amt) { experienceSaved = amt; }

		@Override
		public void setMagick(int amt) {
			if(amt > getMaxMagick())
				magick = getMaxMagick();
			else if(amt <= 0)
				magick = 0;
			else
				magick = amt;
		}

		@Override
		public void setJob(EntityPlayer player, IRPGJob job) {
			if(this.job != null) {
				if(this.job.getJobName().equals(job.getJobName()))
					return;
				if(this.job.canLeave()) {
					if(player != null)
						this.job.leaveJob(player);
					this.job = job;
					if(this.job != null)
						this.job.updatePlayerAttributes(player);
				} else
					PlayerHelper.sendChatMessageToPlayer(player, "You can't leave these responsibilities.");
			} else {
				this.job = job;
				if(this.job != null)
					this.job.updatePlayerAttributes(player);
			}
		}

		@Override
		public int getConstitution() { return job != null ? job.overrideConstitution() ? job.getOverrideConstitution() : constitution : constitution; }

		@Override
		public int getStrength() { return job != null ? job.overrideStrength() ? job.getOverrideStrength() : strength : strength; }

		@Override
		public int getDefense() { return job != null ? job.overrideDefense() ? job.getOverrideDefense() : defense : defense; }

		@Override
		public int getDexterity() { return job != null ? job.overrideDexterity() ? job.getOverrideDexterity() : dexterity : dexterity; }

		@Override
		public int getIntelligence() { return job != null ? job.overrideIntelligence() ? job.getOverrideIntelligence() : intelligence : intelligence; }

		@Override
		public int getExperienceSaved() { return experienceSaved; }

		@Override
		public int getLevel() { return constitution + strength + defense + dexterity + intelligence; }

		@Override
		public int getMagick() { return job != null ? job.replaceMagick() ? 0 : magick  : magick; }

		@Override
		public int getMaxMagick() { return job != null ? job.replaceMagick() ? 0 : getIntelligence() * 1000  : getIntelligence() * 1000; }

		@Override
		public IRPGJob getJob() { return job; }

		@Override
		public NBTTagCompound saveNBT() {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger(TAG_INT_CONSTITUTION, constitution);
			nbt.setInteger(TAG_INT_STRENGTH, strength);
			nbt.setInteger(TAG_INT_DEFENSE, defense);
			nbt.setInteger(TAG_INT_DEXTERITY, dexterity);
			nbt.setInteger(TAG_INT_INTELLIGENCE, intelligence);
			nbt.setInteger(TAG_INT_EXPERIENCE_SAVED, experienceSaved);
			nbt.setInteger(TAG_INT_MAGICK, magick);
			if(job != null && job.getClass() != null && !job.getClass().getName().isEmpty()) {
				nbt.setString(TAG_STRING_CLASS, job.getClass().getName());
				nbt.setTag(TAG_JOB, job.serializeNBT());
			}
			if(job == null || job.getClass() == null || job.getClass().getName().isEmpty()) {
				nbt.removeTag(TAG_STRING_CLASS);
				nbt.removeTag(TAG_JOB);
			}
			return nbt;
		}

		@Override
		public void loadNBT(EntityPlayer player, NBTTagCompound nbt) {
			setConstitution(player, nbt.getInteger(TAG_INT_CONSTITUTION));
			setStrength(nbt.getInteger(TAG_INT_STRENGTH));
			setDefense(nbt.getInteger(TAG_INT_DEFENSE));
			setDexterity(player, nbt.getInteger(TAG_INT_DEXTERITY));
			setIntelligence(nbt.getInteger(TAG_INT_INTELLIGENCE));
			setExperienceSaved(nbt.getInteger(TAG_INT_EXPERIENCE_SAVED));
			setMagick(nbt.getInteger(TAG_INT_MAGICK));
			if(nbt.hasKey(TAG_STRING_CLASS) && !nbt.getString(TAG_STRING_CLASS).isEmpty()) {
				try {
					Object obj = nbt.getString(TAG_STRING_CLASS).equals(JobGod.class.getName()) ? Class.forName(nbt.getString(TAG_STRING_CLASS)).getConstructor(EntityPlayer.class).newInstance(player) : Class.forName(nbt.getString(TAG_STRING_CLASS)).newInstance();
					if(obj != null && obj instanceof IRPGJob) {
						if(nbt.hasKey(TAG_JOB) && nbt.getCompoundTag(TAG_JOB) != null)
							((IRPGJob) obj).deserializeNBT(nbt.getCompoundTag(TAG_JOB));
						setJob(player, (IRPGJob) obj);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else
				setJob(player, null);
		}

	    public static int getExperienceCostForNextLevel(EntityPlayer player) {
	    	final IRPG rpg = RPGCapabilityProvider.get(player);
	    	if(rpg != null) {
	    		int rpgLevel = (rpg.getConstitution() + rpg.getStrength() + rpg.getDefense() + rpg.getDexterity() + rpg.getIntelligence());
	    		return rpgLevel <= 0 ? 1 : (int) (rpgLevel * 1.2) - rpg.getExperienceSaved();
	    	}
	        return 0;
	    }
	}
	
	public static class RPGCapabilityFactory implements Callable<IRPG> {
		@Override
	    public IRPG call() throws Exception { return new RPGCapabilityDefault(); }
	}
	
	public static class RPGCapabilityProvider implements ICapabilitySerializable<NBTTagCompound> {
		
		@CapabilityInject(IRPG.class)
	    public static final Capability<IRPG> RPG_CAP = null;
		
	    protected IRPG rpg = null;

	    private final SoftReference<EntityPlayer> p;

	    public RPGCapabilityProvider(EntityPlayer player) {
	    	rpg = new RPGCapabilityDefault();
	    	p = new SoftReference<EntityPlayer>(player);
	    }
	    
	    public static IRPG get(EntityPlayer player) {
	        if(player.hasCapability(RPG_CAP, null))
	            return player.getCapability(RPG_CAP, null);
	        return null;
	    }

		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) { return RPG_CAP != null && capability == RPG_CAP; }

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			if(RPG_CAP != null && capability == RPG_CAP)
	            return RPG_CAP.cast(rpg);
	        return null;
		}

		@Override
		public NBTTagCompound serializeNBT() { return rpg.saveNBT(); }

		@Override
		public void deserializeNBT(NBTTagCompound nbt) {
			if(p != null && p.get() != null)
				rpg.loadNBT(p.get(), nbt);
		}
	}
	
	public static class RPGCapabilityStorage implements Capability.IStorage<IRPG> {
		
		@Override
		public NBTBase writeNBT(Capability<IRPG> capability, IRPG instance, EnumFacing side) { return null; }

		@Override
		public void readNBT(Capability<IRPG> capability, IRPG instance, EnumFacing side, NBTBase nbt) {}
	}
}
