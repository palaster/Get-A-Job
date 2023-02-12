package palaster.gj.network.client;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.NetworkEvent;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGProvider;
import palaster.gj.network.PacketHandler;

public class PacketUpdateRPG {

	private final Tag tag;

    public PacketUpdateRPG(Tag tag) { this.tag = tag; }

	public static void encode(PacketUpdateRPG pkt, FriendlyByteBuf buffer) {
		if(pkt.tag instanceof CompoundTag)
			buffer.writeNbt((CompoundTag) pkt.tag);
	}

	public static PacketUpdateRPG decode(FriendlyByteBuf buffer) { return new PacketUpdateRPG(buffer.readNbt()); }

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
	
	public static void syncPlayerRPGCapabilitiesToClient(@Nullable Player player) {
    	if(player != null && !player.level.isClientSide) {
    		LazyOptional<IRPG> lazy_optional_rpg = player.getCapability(RPGProvider.RPG_CAPABILITY, null);
    		IRPG rpg = lazy_optional_rpg.orElse(null);
    		if(rpg != null)
    			PacketHandler.sendTo((ServerPlayer) player, new PacketUpdateRPG(rpg.serializeNBT()));
    	}
    }
}