package palaster.gj.api.capabilities;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import palaster.gj.misc.WorkshopChunk;

public class WorldCapability {
	
	public static class WorldCapabilityDefault implements IWorld {
		
		private ArrayList<WorkshopChunk> wcs = new ArrayList<WorkshopChunk>();
		
		@Override
		public void addWorkshopChunk(WorkshopChunk wc) { wcs.add(wc); }

		@Override
		public void removeWorkshopChunk(WorkshopChunk wc) {
			for(int i = 0; i < wcs.size(); i++)
				if(wcs.get(i) != null)
					if(wcs.get(i).equals(wc))
						wcs.remove(i);
		}

		@Override
		@Nullable
		public WorkshopChunk getWorkshopChunkFromCoord(int xCoord, int zCoord) {
			for(WorkshopChunk wc : wcs)
				if(wc.getX() == xCoord && wc.getZ() == zCoord)
					return wc;
			return null;
		}

		@Override
		@Nullable
		public WorkshopChunk getWorkshopChunkFromChunk(@Nullable Chunk chunk) {
			if(chunk != null)
				for(WorkshopChunk wc : wcs)
					if(wc.isChunkEqual(chunk))
						return wc;
			return null;
		}

		@Override
		public NBTTagCompound serializeNBT() { return new NBTTagCompound(); }

		@Override
		public void deserializeNBT(NBTTagCompound nbt) {}
	}

	public static class WorldCapabilityFactory implements Callable<IWorld> {
		@Override
	    public IWorld call() throws Exception { return new WorldCapabilityDefault(); }
	}
	
	public static class WorldCapabilityProvider implements ICapabilitySerializable<NBTTagCompound> {
		
		@CapabilityInject(IWorld.class)
	    public static final Capability<IWorld> WORLD_CAP = null;
		
	    protected IWorld world = null;

	    public WorldCapabilityProvider() { world = new WorldCapabilityDefault(); }
	    
	    public static IWorld get(World world) {
	        if(world.hasCapability(WORLD_CAP, null))
	            return world.getCapability(WORLD_CAP, null);
	        return null;
	    }

		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) { return WORLD_CAP != null && capability == WORLD_CAP; }

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			if(WORLD_CAP != null && capability == WORLD_CAP)
	            return WORLD_CAP.cast(world);
	        return null;
		}

		@Override
		public NBTTagCompound serializeNBT() { return world.serializeNBT(); }

		@Override
		public void deserializeNBT(NBTTagCompound nbt) { world.deserializeNBT(nbt); }
	}
	
	public static class WorldCapabilityStorage implements Capability.IStorage<IWorld> {
		
		@Override
		public NBTBase writeNBT(Capability<IWorld> capability, IWorld instance, EnumFacing side) { return instance.serializeNBT(); }

		@Override
		public void readNBT(Capability<IWorld> capability, IWorld instance, EnumFacing side, NBTBase nbt) { instance.deserializeNBT((NBTTagCompound) nbt); }
	}
}
