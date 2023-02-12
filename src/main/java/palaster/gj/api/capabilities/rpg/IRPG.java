package palaster.gj.api.capabilities.rpg;

import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;
import palaster.gj.api.jobs.IRPGJob;

public interface IRPG extends INBTSerializable<Tag> {
	
	void setConstitution(int amount);
	
	void setStrength(int amount);
	
	void setDefense(int amount);
	
	void setDexterity(int amount);
	
	void setIntelligence(int amount);

	void setExperienceSaved(int amount);
	
	void setMagick(int amount);
	
	void setJob(IRPGJob job);
	
	int getConstitution(boolean getTrueValue);
	
	int getStrength(boolean getTrueValue);
	
	int getDefense(boolean getTrueValue);
	
	int getDexterity(boolean getTrueValue);
	
	int getIntelligence(boolean getTrueValue);
	
	default int getConstitution() { return getConstitution(false); }
	
	default int getStrength() { return getStrength(false); }
	
	default int getDefense() { return getDefense(false); }
	
	default int getDexterity() { return getDexterity(false); }
	
	default int getIntelligence() { return getIntelligence(false); }

	int getExperienceSaved();

	int getLevel();
		
	int getMagick();
	
	int getMaxMagick();
	
	IRPGJob getJob();
}
