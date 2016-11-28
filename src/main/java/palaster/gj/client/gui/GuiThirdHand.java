package palaster.gj.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import palaster.gj.inventories.ContainerThirdHand;
import palaster.gj.libs.LibResource;

@SideOnly(Side.CLIENT)
public class GuiThirdHand extends GuiContainer {

	public GuiThirdHand(InventoryPlayer invPlayer, IItemHandler ih) { super(new ContainerThirdHand(invPlayer, ih)); }

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		mc.renderEngine.bindTexture(LibResource.THIRD_HAND);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}