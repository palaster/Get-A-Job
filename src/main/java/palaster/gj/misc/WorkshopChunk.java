package palaster.gj.misc;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.INBTSerializable;

public class WorkshopChunk implements INBTSerializable<NBTTagCompound> {
	
	public static final String TAG_UUID_OWNER = "WorkshopChunkUUID",
			TAG_INT = "WorkshopChunk";

	private UUID owner = null;
	private int xCoord = 0,
	zCoord = 0;
	
	public WorkshopChunk(UUID owner, int xCoord, int zCoord) {
		this.owner = owner;
		this.xCoord = xCoord;
		this.zCoord = zCoord;
	}
	
	@Nullable
	public UUID getOwner() { return owner; }
	
	public int getX() { return xCoord; }
	
	public int getZ() { return zCoord; }

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		if(owner != null) {
			nbt.setUniqueId(TAG_UUID_OWNER, owner);
			nbt.setInteger(TAG_INT + "X", xCoord);
			nbt.setInteger(TAG_INT + "Z", zCoord);
		}
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		if(nbt.hasUniqueId(TAG_UUID_OWNER)) {
			owner = nbt.getUniqueId(TAG_UUID_OWNER);
			xCoord = nbt.getInteger(TAG_INT + "X");
			zCoord = nbt.getInteger(TAG_INT + "Z");
		}
	}
	
	public boolean isChunkEqual(Chunk chunk) {
		if(xCoord == chunk.xPosition && zCoord == chunk.zPosition)
			return true;
		return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof WorkshopChunk) {
			WorkshopChunk wc = (WorkshopChunk) obj;
			return owner.equals(wc.owner) && xCoord == wc.xCoord && zCoord == wc.zCoord;
		}
		return false;
	}
}
