package palaster.gj.network;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import palaster.gj.libs.LibMod;
import palaster.gj.network.client.PacketUpdateRPG;
import palaster.gj.network.server.ScreenButtonPacket;

public class PacketHandler {
    public static int packetId = 0;
    
    private static final String PROTOCOL_VERSION = "1";
    private static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(LibMod.MODID, "main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals);

    public static void init() {
        INSTANCE.registerMessage(packetId++, ScreenButtonPacket.class, ScreenButtonPacket::encode, ScreenButtonPacket::decode, ScreenButtonPacket::handle);
        INSTANCE.registerMessage(packetId++, PacketUpdateRPG.class, PacketUpdateRPG::encode, PacketUpdateRPG::decode, PacketUpdateRPG::handle);
    }

    public static final SimpleChannel getInstance() { return INSTANCE; }

    public static final void sendTo(ServerPlayer player, Object msg) { INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg); }

    public static final void sendToAll(Object msg) { INSTANCE.send(PacketDistributor.ALL.noArg(), msg); }

    public static final void sendToAllAround(PacketDistributor.TargetPoint point, Object msg) { INSTANCE.send(PacketDistributor.NEAR.with(() -> point), msg); }
	
	public static final void sendToAllAround(double x, double y, double z, double range, ResourceKey<Level> worldRegistryKey, Object msg) { PacketHandler.sendToAllAround(new PacketDistributor.TargetPoint(x, y, z, range, worldRegistryKey), msg); }
	
	public static final void sendToAllAround(Player player, double range, Object msg) { PacketHandler.sendToAllAround(player.getX(), player.getY(), player.getZ(), range, player.level.dimension(), msg); }
	
	public static final void sendToDimension(ResourceKey<Level> worldRegistryKey, Object msg) { INSTANCE.send(PacketDistributor.DIMENSION.with(() -> worldRegistryKey), msg); }
	
	public static final void sendToServer(Object msg) { INSTANCE.sendToServer(msg); }
}
