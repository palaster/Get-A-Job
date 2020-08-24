package palaster.gj.client.gui;

import java.io.IOException;
import java.lang.ref.WeakReference;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.gj.api.capabilities.IRPG;
import palaster.gj.api.capabilities.RPGCapability.RPGCapabilityDefault;
import palaster.gj.api.capabilities.RPGCapability.RPGCapabilityProvider;
import palaster.gj.inventories.ContainerRPGIntro;
import palaster.gj.libs.LibResource;
import palaster.libpal.network.PacketHandler;
import palaster.libpal.network.server.GuiButtonMessage;

@SideOnly(Side.CLIENT)
public class GuiRPGIntro extends GuiContainer {
	
	private WeakReference<EntityPlayer> player = null;
	private int hand = -1;
	
	public GuiRPGIntro(EntityPlayer player, int hand) {
		super(new ContainerRPGIntro());
		this.player = new WeakReference<EntityPlayer>(player);
		this.hand = hand;
		ySize = 160;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		mc.renderEngine.bindTexture(LibResource.BLANK);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		if(player != null && player.get() != null) {
    		final IRPG rpg = RPGCapabilityProvider.get(player.get());
    		if(rpg != null) {
    			if(rpg.getJob() != null)
    				fontRenderer.drawString(I18n.format("gj.job.base") + ": " + I18n.format(rpg.getJob().getJobName()), 6, 6, 4210752);
    			else
    				fontRenderer.drawString(I18n.format("gj.job.base") + ": " + I18n.format("gj.job.noCareer"), 6, 6, 4210752);
				fontRenderer.drawString(I18n.format("gj.job.level") + ": " + rpg.getLevel(), 6, 16, 4210752);
    			fontRenderer.drawString(I18n.format("gj.job.constitution") + ": " + rpg.getConstitution(), 6, 26, 4210752);
    			fontRenderer.drawString(I18n.format("gj.job.strength") + ": " + rpg.getStrength(), 6, 36, 4210752);
    			fontRenderer.drawString(I18n.format("gj.job.defense") + ": " + rpg.getDefense(), 6, 46, 4210752);
    			fontRenderer.drawString(I18n.format("gj.job.dexterity") + ": " + rpg.getDexterity(), 6, 56, 4210752);
    			fontRenderer.drawString(I18n.format("gj.job.intelligence") + ": " + rpg.getIntelligence(), 6, 66, 4210752);
                if(rpg.getJob() == null || !rpg.getJob().replaceMagick())
                	fontRenderer.drawString(I18n.format("gj.job.magic") + ": " + rpg.getMagick() + "/" + rpg.getMaxMagick(), 98, 66, 4210752);
                if(RPGCapabilityDefault.getExperienceCostForNextLevel(player.get()) > player.get().experienceLevel)
                	fontRenderer.drawString(I18n.format("gj.expCost") + ": " + RPGCapabilityDefault.getExperienceCostForNextLevel(player.get()) + "", 6, 76, 0x8A0707);
                else
                	fontRenderer.drawString(I18n.format("gj.expCost") + ": " + RPGCapabilityDefault.getExperienceCostForNextLevel(player.get()) + "", 6, 76, 0x009900);
                if(rpg.getJob() != null)
                	rpg.getJob().drawExtraInformationBase(player.get(), fontRenderer, 6, 86, mouseX, mouseY);
    		}
    	}
	}
	
	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();

        buttonList.add(new GuiButton(0, guiLeft + 82, guiTop + 26, 12, 10, "->"));
        buttonList.add(new GuiButton(1, guiLeft + 82, guiTop + 36, 12, 10, "->"));
        buttonList.add(new GuiButton(2, guiLeft + 82, guiTop + 46, 12, 10, "->"));
        buttonList.add(new GuiButton(3, guiLeft + 82, guiTop + 56, 12, 10, "->"));
        buttonList.add(new GuiButton(4, guiLeft + 82, guiTop + 66, 12, 10, "->"));
		buttonList.add(new GuiButton(5, guiLeft + 82, guiTop + 76, 12, 10, "->"));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(player != null && player.get() != null)
			PacketHandler.sendToServer(new GuiButtonMessage(hand, player.get().getPosition(), button.id));
	}
}
