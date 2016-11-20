package palaster.gj.api.capabilities;

import java.util.concurrent.Callable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class WorldCapability {
	
	public static class WorldCapabilityDefault implements IWorld {

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
