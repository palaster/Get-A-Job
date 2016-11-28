package palaster.gj.api.capabilities;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.INBTSerializable;
import palaster.gj.misc.WorkshopChunk;

public interface IWorld extends INBTSerializable<NBTTagCompound> {

	void addWorkshopChunk(WorkshopChunk wc);
	
	void removeWorkshopChunk(WorkshopChunk wc);
	
	@Nullable
	WorkshopChunk getWorkshopChunkFromCoord(int xCoord, int zCoord);
	
	@Nullable
	WorkshopChunk getWorkshopChunkFromChunk(@Nullable Chunk chunk);
}
