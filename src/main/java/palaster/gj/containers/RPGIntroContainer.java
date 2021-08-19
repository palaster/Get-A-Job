package palaster.gj.containers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;

public class RPGIntroContainer extends Container {
	
	public static RPGIntroContainer fromNetwork(int containerId, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
		Hand hand = packetBuffer.readBoolean() ? Hand.MAIN_HAND : Hand.OFF_HAND;
		return new RPGIntroContainer(containerId, hand);
	}
	
	public final Hand hand;
	
	public RPGIntroContainer(int containerId, Hand hand) {
		super(ModContainerTypes.RPG_INTRO_CONTAINER, containerId);
		this.hand = hand;
	}

	@Override
	public boolean stillValid(PlayerEntity playerEntity) { return true; }
}
