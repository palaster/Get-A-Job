package palaster.gj.network.server;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent;
import palaster.gj.api.IReceiveButton;

public class ScreenButtonPacket {

    private final boolean hand;
    private final int buttonId;

    public ScreenButtonPacket(boolean hand, int id) {
        this.hand = hand;
        this.buttonId = id;
    }

	public static void encode(ScreenButtonPacket pkt, FriendlyByteBuf buffer) {
        buffer.writeBoolean(pkt.hand);
        buffer.writeInt(pkt.buttonId);
	}

	public static ScreenButtonPacket decode(FriendlyByteBuf buffer) { return new ScreenButtonPacket(buffer.readBoolean(), buffer.readInt()); }

	public static void handle(ScreenButtonPacket pkt, Supplier<NetworkEvent.Context> ctx) {
		if(!ctx.get().getDirection().getReceptionSide().isClient()) {
			ctx.get().enqueueWork(() -> {
                InteractionHand hand = pkt.hand ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
                if(!ctx.get().getSender().getItemInHand(hand).isEmpty()) {
                    if(ctx.get().getSender().getItemInHand(hand).getItem() instanceof IReceiveButton)
                        ((IReceiveButton) ctx.get().getSender().getItemInHand(hand).getItem()).receiveButtonEvent(ctx.get().getSender(), pkt.buttonId);
                }
			});
		}
		ctx.get().setPacketHandled(true);
	}
}
