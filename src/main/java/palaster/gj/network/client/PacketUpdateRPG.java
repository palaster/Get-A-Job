package palaster.gj.network.client;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGProvider;
import palaster.libpal.network.PacketHandler;

public class PacketUpdateRPG {

	private final INBT tag;

    public PacketUpdateRPG(INBT tag) { this.tag = tag; }

	public void encode(PacketBuffer buffer) {
		if(tag instanceof CompoundNBT)
			buffer.writeNbt((CompoundNBT) tag);
	}

	public static PacketUpdateRPG decode(PacketBuffer buffer) { return new PacketUpdateRPG(buffer.readNbt()); }

	public static void handle(PacketUpdateRPG pkt, Supplier<NetworkEvent.Context> ctx) {
		if(ctx.get().getDirection().getReceptionSide().isClient()) {
			ctx.get().enqueueWork(() -> {
				LazyOptional<IRPG> lazy_optional_rpg = Minecraft.getInstance().player.getCapability(RPGProvider.RPG_CAPABILITY, null);
				IRPG rpg = lazy_optional_rpg.orElse(null);
				if(rpg != null)
					rpg.deserializeNBT(pkt.tag);
			});
		}
		ctx.get().setPacketHandled(true);
	}
	
	public static void syncPlayerRPGCapabilitiesToClient(@Nullable PlayerEntity player) {
    	if(player != null && !player.level.isClientSide) {
    		LazyOptional<IRPG> lazy_optional_rpg = player.getCapability(RPGProvider.RPG_CAPABILITY, null);
    		IRPG rpg = lazy_optional_rpg.orElse(null);
    		if(rpg != null)
    			PacketHandler.sendTo((ServerPlayerEntity) player, new PacketUpdateRPG(rpg.serializeNBT()));
    	}
    }
}