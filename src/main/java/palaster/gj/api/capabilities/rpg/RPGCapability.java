package palaster.gj.api.capabilities.rpg;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import palaster.gj.api.jobs.IRPGJob;

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
		public void setConstitution(int amount) {
			if(amount > MAX_LEVEL)
				constitution = MAX_LEVEL;
			else if(amount <= 0)
				constitution = 0;
			else
				constitution = amount;
		}

		@Override
		public void setStrength(int amount) {
			if(amount > MAX_LEVEL)
				strength = MAX_LEVEL;
			else if(amount <= 0)
				strength = 0;
			else
				strength = amount;
		}
		
		@Override
		public void setDefense(int amount) {
			if(amount > MAX_LEVEL)
				defense = MAX_LEVEL;
			else if(amount <= 0)
				defense = 0;
			else
				defense = amount;
		}
		
		@Override
		public void setDexterity(int amount) {
			if(amount > MAX_LEVEL)
				dexterity = MAX_LEVEL;
			else if(amount <= 0)
				dexterity = 0;
			else
				dexterity = amount;
		}

		@Override
		public void setIntelligence(int amount) {
			if(amount > MAX_LEVEL)
				intelligence = MAX_LEVEL;
			else if(amount <= 0)
				intelligence = 0;
			else
				intelligence = amount;
		}

		@Override
		public void setExperienceSaved(int amount) { experienceSaved = amount; }

		@Override
		public void setMagick(int amount) {
			if(amount > getMaxMagick())
				magick = getMaxMagick();
			else if(amount <= 0)
				magick = 0;
			else
				magick = amount;
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
		public Tag serializeNBT() {
			CompoundTag compoundTag = new CompoundTag();
			compoundTag.putInt(NBT_CONSTITUTION, constitution);
			compoundTag.putInt(NBT_STRENGTH, strength);
			compoundTag.putInt(NBT_DEFENSE, defense);
			compoundTag.putInt(NBT_DEXTERITY, dexterity);
			compoundTag.putInt(NBT_INTELLIGENCE, intelligence);
			compoundTag.putInt(NBT_EXPERIENCE_SAVED, experienceSaved);
			compoundTag.putInt(NBT_MAGICK, magick);
			if(job != null && job.getClass() != null && !job.getClass().getName().isEmpty()) {
				compoundTag.putString(NBT_JOB_CLASS, job.getClass().getName());
				compoundTag.put(NBT_JOB, job.serializeNBT());
			}
			if(job == null || job.getClass() == null || job.getClass().getName().isEmpty()) {
				compoundTag.remove(NBT_JOB_CLASS);
				compoundTag.remove(NBT_JOB);
			}
			return compoundTag;
		}
		
		@Override
		public void deserializeNBT(Tag tag) {
			if(tag instanceof CompoundTag) {
				CompoundTag cNBT = (CompoundTag) tag;
				setConstitution(cNBT.getInt(NBT_CONSTITUTION));
				setStrength(cNBT.getInt(NBT_STRENGTH));
				setDefense(cNBT.getInt(NBT_DEFENSE));
				setDexterity(cNBT.getInt(NBT_DEXTERITY));
				setIntelligence(cNBT.getInt(NBT_INTELLIGENCE));
				setExperienceSaved(cNBT.getInt(NBT_EXPERIENCE_SAVED));
				setMagick(cNBT.getInt(NBT_MAGICK));
				if(cNBT.contains(NBT_JOB_CLASS) && !cNBT.getString(NBT_JOB_CLASS).isEmpty()) {
					try {
						Object obj = Class.forName(cNBT.getString(NBT_JOB_CLASS)).getConstructor().newInstance();
						if(obj instanceof IRPGJob) {
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

		public static void updatePlayerAttributes(@Nullable Player player, @Nullable IRPG rpg) {
			if(player == null || rpg == null)
				return;
			if(rpg.getConstitution() <= 0) {
				AttributeInstance attributeInstance = player.getAttributes().getInstance(Attributes.MAX_HEALTH);
    			if(attributeInstance != null && attributeInstance.getModifier(HEALTH_ID) != null)
    				attributeInstance.removeModifier(attributeInstance.getModifier(HEALTH_ID));
    		} else {
    			AttributeInstance attributeInstance = player.getAttributes().getInstance(Attributes.MAX_HEALTH);
				if(attributeInstance != null) {
					if(attributeInstance.getModifier(HEALTH_ID) != null)
    					attributeInstance.removeModifier(attributeInstance.getModifier(HEALTH_ID));
					attributeInstance.addTransientModifier(new AttributeModifier(HEALTH_ID, "gj.rpg.constitution", rpg.getConstitution() * .4, AttributeModifier.Operation.ADDITION));
					// attributeInstance.addPermanentModifier();
				}
    		}
			if(rpg.getDexterity() <= 0) {
				AttributeInstance attributeInstance = player.getAttributes().getInstance(Attributes.MOVEMENT_SPEED);
    			if(attributeInstance != null && attributeInstance.getModifier(DEXTERITY_ID) != null)
    				attributeInstance.removeModifier(attributeInstance.getModifier(DEXTERITY_ID));
    		} else {
    			AttributeInstance attributeInstance = player.getAttributes().getInstance(Attributes.MOVEMENT_SPEED);
				if(attributeInstance != null) {
					if(attributeInstance.getModifier(DEXTERITY_ID) != null)
    					attributeInstance.removeModifier(attributeInstance.getModifier(DEXTERITY_ID));
    				attributeInstance.addTransientModifier(new AttributeModifier(DEXTERITY_ID, "gj.rpg.dexterity", rpg.getDexterity() * .008, AttributeModifier.Operation.ADDITION));
					// attributeInstance.addPermanentModifier();
				}
    		}
			if(rpg.getJob() != null)
				rpg.getJob().updatePlayerAttributes(player);
		}
		
		public static void jobChange(@Nullable Player player, @Nullable IRPG rpg, @Nullable IRPGJob job) {
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
						player.sendSystemMessage(Component.literal("You can't leave these responsibilities."));
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
						player.sendSystemMessage(Component.literal("You can't leave these responsibilities."));
				}
			}
		}
		
	    public static int getExperienceCostForNextLevel(Player player) {
	    	LazyOptional<IRPG> lazy_optional_rpg = player.getCapability(RPGProvider.RPG_CAPABILITY, null);
	    	IRPG rpg = lazy_optional_rpg.orElse(null);
	    	if(rpg != null) {
	    		int rpgLevel = (rpg.getConstitution(true) + rpg.getStrength(true) + rpg.getDefense(true) + rpg.getDexterity(true) + rpg.getIntelligence(true));
	    		return rpgLevel <= 0 ? 1 : (int) (rpgLevel * 1.2) - rpg.getExperienceSaved();
	    	}
	        return 0;
	    }
	}
	
	public static class RPGProvider implements ICapabilitySerializable<Tag> {
	    public static final Capability<IRPG> RPG_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
		
	    protected IRPG rpg = new RPGDefault();
	    
	    private final LazyOptional<IRPG> holder = LazyOptional.of(() -> rpg);
	    
		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) { return RPG_CAPABILITY.orEmpty(cap, holder); }

		@Override
		public Tag serializeNBT() { return rpg.serializeNBT(); }

		@Override
		public void deserializeNBT(Tag nbt) { rpg.deserializeNBT(nbt); }
	}
}
