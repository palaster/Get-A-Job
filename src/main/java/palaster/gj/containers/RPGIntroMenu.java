package palaster.gj.containers;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.IContainerFactory;
import palaster.gj.GetAJob;

public class RPGIntroMenu extends AbstractContainerMenu {

	public final InteractionHand hand;
	
	public RPGIntroMenu(int containerId, InteractionHand hand) {
		super(GetAJob.RPG_INTRO_MENU_TYPE.get(), containerId);
		this.hand = hand;
	}

	@Override
	public boolean stillValid(Player player) { return true; }

	@Override
	public ItemStack quickMoveStack(Player p_38941_, int p_38942_) { return ItemStack.EMPTY; }

	public static class Factory implements IContainerFactory<RPGIntroMenu> {
		@Override
		public RPGIntroMenu create(int windowId, Inventory inv, FriendlyByteBuf data) {
			InteractionHand hand = data.readBoolean() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
			return new RPGIntroMenu(windowId, hand);
		}
	}
}
