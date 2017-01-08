package palaster.gj.api.capabilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import palaster.gj.entities.jobs.RPGJobBase;

public interface IRPG {
	
	void setConstitution(EntityPlayer player, int amt);
	
	void setStrength(EntityPlayer player, int amt);
	
	void setDefense(int amt);
	
	void setDexterity(EntityPlayer player, int amt);
	
	void setIntelligence(int amt);
	
	void setMagick(int amt);
	
	void setJob(EntityPlayer player, RPGJobBase job);
	
	int getConstitution();
	
	int getStrength();
	
	int getDefense();
	
	int getDexterity();
	
	int getIntelligence();
		
	int getMagick();
	
	int getMaxMagick();
	
	RPGJobBase getJob();
	
	NBTTagCompound saveNBT();

    void loadNBT(EntityPlayer player, NBTTagCompound nbt);
}
