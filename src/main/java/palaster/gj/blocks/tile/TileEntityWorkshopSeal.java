package palaster.gj.blocks.tile;

import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;
import palaster.libpal.blocks.tile.TileEntityMod;

public class TileEntityWorkshopSeal extends TileEntityMod {
	
	public static final String TAG_UUID_OWNER = "WorkshopSealOwner";
	
	private UUID owner = null;
	
	public void setOwner(UUID owner) { this.owner = owner; }
	
	public UUID getOwner() { return owner; }

	@Override
	public void update() {}

	@Override
	public void readPacketNBT(NBTTagCompound compound) {
		super.readPacketNBT(compound);
		if(compound.hasUniqueId(TAG_UUID_OWNER))
			owner = compound.getUniqueId(TAG_UUID_OWNER);
	}

	@Override
	public void writePacketNBT(NBTTagCompound compound) {
		if(owner != null)
			compound.setUniqueId(TAG_UUID_OWNER, owner);
		super.writePacketNBT(compound);
	}
}
