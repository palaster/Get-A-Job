package palaster.gj.api.capabilities.rpg;

import java.util.UUID;
import java.util.concurrent.Callable;

import javax.annotation.Nullable;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import palaster.gj.api.jobs.IRPGJob;
import palaster.libpal.core.helpers.PlayerHelper;

public class RPGCapability {

	public static class RPGDefault implements IRPG {

		public static final String NBT_JOB_CLASS = "gj:rpg:job_class",
			NBT_JOB = "gj:rpg:job",
			NBT_CONSTITUTION = "gj:rpg:constitution",
			NBT_STRENGTH = "gj:rpg:strength",
			NBT_DEFENSE = "gj:rpg:defense",
			NBT_DEXTERITY = "gj:rpg:dexterity",
			NBT_INTELLIGENCE = "gj:rpg:intelligence",
			NBT_EXPERIENCE_SAVED = "gj:rpg:experience_saved",
			NBT_MAGICK = "gj:rpg:magick";
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
		public void setConstitution(int amt) {
			if(amt > MAX_LEVEL)
				constitution = MAX_LEVEL;
			else if(amt <= 0)
				constitution = 0;
			else
				constitution = amt;
		}

		@Override
		public void setStrength(int amt) {
			if(amt > MAX_LEVEL)
				strength = MAX_LEVEL;
			else if(amt <= 0)
				strength = 0;
			else
				strength = amt;
		}
		
		@Override
		public void setDefense(int amt) {
			if(amt > MAX_LEVEL)
				defense = MAX_LEVEL;
			else if(amt <= 0)
				defense = 0;
			else
				defense = amt;
		}
		
		@Override
		public void setDexterity(int amt) {
			if(amt > MAX_LEVEL)
				dexterity = MAX_LEVEL;
			else if(amt <= 0)
				dexterity = 0;
			else
				dexterity = amt;
		}

		@Override
		public void setIntelligence(int amt) {
			if(amt > MAX_LEVEL)
				intelligence = MAX_LEVEL;
			else if(amt <= 0)
				intelligence = 0;
			else
				intelligence = amt;
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
		public void setJob(IRPGJob job) { this.job = job; }

		@Override
		public int getConstitution(boolean getTrueValue) {
			if(getTrueValue)
				return constitution;
			return job != null ? job.overrideConstitution() ? job.getOverrideConstitution(constitution) : constitution : constitution;
		}

		@Override
		public int getStrength(boolean getTrueValue) {
			if(getTrueValue)
				return strength;
			return job != null ? job.overrideStrength() ? job.getOverrideStrength(strength) : strength : strength;
		}

		@Override
		public int getDefense(boolean getTrueValue) {
			if(getTrueValue)
				return defense;
			return job != null ? job.overrideDefense() ? job.getOverrideDefense(defense) : defense : defense;
		}

		@Override
		public int getDexterity(boolean getTrueValue) {
			if(getTrueValue)
				return dexterity;
			return job != null ? job.overrideDexterity() ? job.getOverrideDexterity(dexterity) : dexterity : dexterity;
		}

		@Override
		public int getIntelligence(boolean getTrueValue) {
			if(getTrueValue)
				return intelligence;
			return job != null ? job.overrideIntelligence() ? job.getOverrideIntelligence(intelligence) : intelligence : intelligence;
		}

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
		public INBT serializeNBT() {
			CompoundNBT nbt = new CompoundNBT();
			nbt.putInt(NBT_CONSTITUTION, constitution);
			nbt.putInt(NBT_STRENGTH, strength);
			nbt.putInt(NBT_DEFENSE, defense);
			nbt.putInt(NBT_DEXTERITY, dexterity);
			nbt.putInt(NBT_INTELLIGENCE, intelligence);
			nbt.putInt(NBT_EXPERIENCE_SAVED, experienceSaved);
			nbt.putInt(NBT_MAGICK, magick);
			if(job != null && job.getClass() != null && !job.getClass().getName().isEmpty()) {
				nbt.putString(NBT_JOB_CLASS, job.getClass().getName());
				nbt.put(NBT_JOB, job.serializeNBT());
			}
			if(job == null || job.getClass() == null || job.getClass().getName().isEmpty()) {
				nbt.remove(NBT_JOB_CLASS);
				nbt.remove(NBT_JOB);
			}
			return nbt;
		}
		
		@Override
		public void deserializeNBT(INBT nbt) {
			if(nbt instanceof CompoundNBT) {
				CompoundNBT cNBT = (CompoundNBT) nbt;
				setConstitution(cNBT.getInt(NBT_CONSTITUTION));
				setStrength(cNBT.getInt(NBT_STRENGTH));
				setDefense(cNBT.getInt(NBT_DEFENSE));
				setDexterity(cNBT.getInt(NBT_DEXTERITY));
				setIntelligence(cNBT.getInt(NBT_INTELLIGENCE));
				setExperienceSaved(cNBT.getInt(NBT_EXPERIENCE_SAVED));
				setMagick(cNBT.getInt(NBT_MAGICK));
				if(cNBT.contains(NBT_JOB_CLASS) && !cNBT.getString(NBT_JOB_CLASS).isEmpty()) {
					try {
						Object obj = Class.forName(cNBT.getString(NBT_JOB_CLASS)).newInstance();
						if(obj != null && obj instanceof IRPGJob) {
							if(cNBT.contains(NBT_JOB) && cNBT.getCompound(NBT_JOB) != null)
								((IRPGJob) obj).deserializeNBT(cNBT.getCompound(NBT_JOB));
							setJob((IRPGJob) obj);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else
					setJob(null);
			}
		}

		public static void updatePlayerAttributes(@Nullable PlayerEntity player, @Nullable IRPG rpg) {
			if(player == null || rpg == null)
				return;
			if(rpg.getConstitution() <= 0) {
				ModifiableAttributeInstance attributeInstance = player.getAttributes().getInstance(Attributes.MAX_HEALTH);
    			if(attributeInstance.getModifier(HEALTH_ID) != null)
    				attributeInstance.removeModifier(attributeInstance.getModifier(HEALTH_ID));
    		} else {
    			ModifiableAttributeInstance attributeInstance = player.getAttributes().getInstance(Attributes.MAX_HEALTH);
    			if(attributeInstance.getModifier(HEALTH_ID) != null)
    				attributeInstance.removeModifier(attributeInstance.getModifier(HEALTH_ID));
    			attributeInstance.addTransientModifier(new AttributeModifier(HEALTH_ID, "gj.rpg.constitution", rpg.getConstitution() * .4, AttributeModifier.Operation.ADDITION));
    			// attributeInstance.addPermanentModifier();
    		}
			if(rpg.getDexterity() <= 0) {
				ModifiableAttributeInstance attributeInstance = player.getAttributes().getInstance(Attributes.MOVEMENT_SPEED);
    			if(attributeInstance.getModifier(DEXTERITY_ID) != null)
    				attributeInstance.removeModifier(attributeInstance.getModifier(DEXTERITY_ID));
    		} else {
    			ModifiableAttributeInstance attributeInstance = player.getAttributes().getInstance(Attributes.MOVEMENT_SPEED);
    			if(attributeInstance.getModifier(DEXTERITY_ID) != null)
    				attributeInstance.removeModifier(attributeInstance.getModifier(DEXTERITY_ID));
    			attributeInstance.addTransientModifier(new AttributeModifier(DEXTERITY_ID, "gj.rpg.dexterity", rpg.getDexterity() * .008, AttributeModifier.Operation.ADDITION));
    			// attributeInstance.addPermanentModifier();
    		}
			if(rpg.getJob() != null)
				rpg.getJob().updatePlayerAttributes(player);
		}
		
		public static void jobChange(@Nullable PlayerEntity player, @Nullable IRPG rpg, @Nullable IRPGJob job) {
			if(player == null || rpg == null)
				return;
			if(job != null) {
				if(rpg.getJob() != null) {
					if(rpg.getJob().getJobName().equals(job.getJobName()))
						return;
					if(rpg.getJob().canLeave()) {
						if(player != null)
							rpg.getJob().leaveJob(player);
						rpg.setJob(job);
						rpg.getJob().updatePlayerAttributes(player);
					} else
						PlayerHelper.sendChatMessageToPlayer(player, "You can't leave these responsibilities.");
				} else {
					rpg.setJob(job);
					rpg.getJob().updatePlayerAttributes(player);
				}
			} else {
				if(rpg.getJob() != null) {
					if(rpg.getJob().canLeave()) {
						rpg.getJob().leaveJob(player);
						rpg.setJob(job);
					} else
						PlayerHelper.sendChatMessageToPlayer(player, "You can't leave these responsibilities.");
				}
			}
		}
		
	    public static int getExperienceCostForNextLevel(PlayerEntity player) {
	    	LazyOptional<IRPG> lazy_optional_rpg = player.getCapability(RPGProvider.RPG_CAPABILITY, null);
	    	IRPG rpg = lazy_optional_rpg.orElse(null);
	    	if(rpg != null) {
	    		int rpgLevel = (rpg.getConstitution(true) + rpg.getStrength(true) + rpg.getDefense(true) + rpg.getDexterity(true) + rpg.getIntelligence(true));
	    		return rpgLevel <= 0 ? 1 : (int) (rpgLevel * 1.2) - rpg.getExperienceSaved();
	    	}
	        return 0;
	    }
	}
	
	public static class RPGProvider implements ICapabilitySerializable<INBT> {
		@CapabilityInject(IRPG.class)
	    public static final Capability<IRPG> RPG_CAPABILITY = null;
		
	    protected IRPG rpg = new RPGDefault();
	    
	    private final LazyOptional<IRPG> holder = LazyOptional.of(() -> rpg);
	    
		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) { return RPG_CAPABILITY.orEmpty(capability, holder); }

		@Override
		public INBT serializeNBT() { return rpg.serializeNBT(); }

		@Override
		public void deserializeNBT(INBT nbt) { rpg.deserializeNBT(nbt); }
	}
	
	public static class RPGStorage implements Capability.IStorage<IRPG> {
		@Override
		public INBT writeNBT(Capability<IRPG> capability, IRPG instance, Direction side) { return instance.serializeNBT(); }

		@Override
		public void readNBT(Capability<IRPG> capability, IRPG instance, Direction side, INBT nbt) { instance.deserializeNBT(nbt); }
	}

	public static class RPGFactory implements Callable<IRPG> {
		@Override
	    public IRPG call() throws Exception { return new RPGDefault(); }
	}
}
