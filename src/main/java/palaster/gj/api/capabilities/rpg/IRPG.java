package palaster.gj.api.capabilities.rpg;

import net.minecraft.nbt.INBT;
import net.minecraftforge.common.util.INBTSerializable;
import palaster.gj.api.jobs.IRPGJob;

public interface IRPG extends INBTSerializable<INBT> {
	
	void setConstitution(int amt);
	
	void setStrength(int amt);
	
	void setDefense(int amt);
	
	void setDexterity(int amt);
	
	void setIntelligence(int amt);

	void setExperienceSaved(int amt);
	
	void setMagick(int amt);
	
	void setJob(IRPGJob job);
	
	int getConstitution();
	
	int getStrength();
	
	int getDefense();
	
	int getDexterity();
	
	int getIntelligence();

	int getExperienceSaved();

	int getLevel();
		
	int getMagick();
	
	int getMaxMagick();
	
	IRPGJob getJob();
}
