package palaster.gj.api.capabilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import palaster.gj.api.jobs.IRPGJob;

public interface IRPG {
	
	void setConstitution(EntityPlayer player, int amt);
	
	void setStrength(EntityPlayer player, int amt);
	
	void setDefense(int amt);
	
	void setDexterity(EntityPlayer player, int amt);
	
	void setIntelligence(int amt);
	
	void setMagick(int amt);
	
	void setJob(EntityPlayer player, IRPGJob job);
	
	int getConstitution();
	
	int getStrength();
	
	int getDefense();
	
	int getDexterity();
	
	int getIntelligence();
		
	int getMagick();
	
	int getMaxMagick();
	
	IRPGJob getJob();
	
	NBTTagCompound saveNBT();

    void loadNBT(EntityPlayer player, NBTTagCompound nbt);
}
