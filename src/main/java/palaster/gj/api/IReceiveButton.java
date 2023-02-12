package palaster.gj.api;

import net.minecraft.world.entity.player.Player;

@FunctionalInterface
public interface IReceiveButton {
    public void receiveButtonEvent(Player player, int buttonId);
}
