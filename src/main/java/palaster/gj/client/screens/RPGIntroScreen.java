package palaster.gj.client.screens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ScrollPanel;
import net.minecraftforge.common.util.LazyOptional;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGDefault;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGProvider;
import palaster.gj.containers.RPGIntroMenu;
import palaster.gj.libs.LibResource;
import palaster.gj.network.PacketHandler;
import palaster.gj.network.server.ScreenButtonPacket;

@OnlyIn(Dist.CLIENT)
public class RPGIntroScreen extends AbstractContainerScreen<RPGIntroMenu> {

	private static final int SUGGESTED_Y = 99;

	private final ArrayList<InfoComponentTooltip> infoComponentTooltips = new ArrayList<>(Arrays.asList(
		new InfoComponentTooltip(Component.translatable("gj.job.constitution.tooltip"), 6, 123, 27, 38),
		new InfoComponentTooltip(Component.translatable("gj.job.strength.tooltip"), 6, 123, 39, 50),
		new InfoComponentTooltip(Component.translatable("gj.job.defense.tooltip"), 6, 123, 51, 62),
		new InfoComponentTooltip(Component.translatable("gj.job.dexterity.tooltip"), 6, 123, 63, 74),
		new InfoComponentTooltip(Component.translatable("gj.job.intelligence.tooltip"), 6, 123, 75, 86)
	));

	private final InteractionHand hand;

	private ExtraInfoScrollPanel extraInfoScrollPanel = null;
	
	public RPGIntroScreen(RPGIntroMenu container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		this.hand = container.hand;
		imageHeight = 160;
	}

	@Override
	public void render(PoseStack ps, int mouseX, int mouseY, float partialTicks) {
		renderBackground(ps);
		if(extraInfoScrollPanel != null) {
			extraInfoScrollPanel.components.clear();
		}
		super.render(ps, mouseX, mouseY, partialTicks);
		if(extraInfoScrollPanel != null) {
			extraInfoScrollPanel.render(ps, mouseX, mouseY, partialTicks);
		}
		renderTooltip(ps, mouseX, mouseY);
	}

	@Override
	protected void renderTooltip(PoseStack ps, int mouseX, int mouseY) {
		for(InfoComponentTooltip infoComponent: infoComponentTooltips) {
			int leftMinX = infoComponent.minX + leftPos;
			int leftMaxX = infoComponent.maxX + leftPos;
			if(mouseX >= leftMinX && mouseX <= leftMaxX) {
				int topMinY = infoComponent.minY + topPos;
				int topMaxY = infoComponent.maxY + topPos;
				if(mouseY >= topMinY && mouseY <= topMaxY) {
					super.renderTooltip(ps, infoComponent.component, mouseX, mouseY);
					return;
				}
			}
		}
	}

	@Override
	protected void renderBg(PoseStack ps, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, LibResource.BLANK);
		blit(ps, (width - imageWidth) / 2, (height - imageHeight) / 2, 0, 0, imageWidth, imageHeight);
	}

	@Override
	protected void renderLabels(PoseStack ps, int mouseX, int mouseY) {
		LazyOptional<IRPG> lazy_optional_rpg = minecraft.player.getCapability(RPGProvider.RPG_CAPABILITY, null);
		final IRPG rpg = lazy_optional_rpg.orElse(null);
		if(rpg != null) {
			if(rpg.getJob() != null)
				font.draw(ps, Component.translatable("gj.job.base", Component.translatable(rpg.getJob().getJobName())), 6, 6, 4210752);
			else
				font.draw(ps, Component.translatable("gj.job.base", Component.translatable("gj.job.no_career")), 6, 6, 4210752);
			font.draw(ps, Component.translatable("gj.job.level", rpg.getLevel()), 6, 15, 4210752);

			font.draw(ps, getStatLocalization("gj.job.constitution", rpg.getConstitution(), rpg.getJob() != null && rpg.getJob().overrideConstitution(), rpg.getConstitution(true)), 6, 27, 4210752);
			font.draw(ps, getStatLocalization("gj.job.strength", rpg.getStrength(), rpg.getJob() != null && rpg.getJob().overrideStrength(), rpg.getStrength(true)), 6, 39, 4210752);
			font.draw(ps, getStatLocalization("gj.job.defense", rpg.getDefense(), rpg.getJob() != null && rpg.getJob().overrideDefense(), rpg.getDefense(true)), 6, 51, 4210752);
			font.draw(ps, getStatLocalization("gj.job.dexterity", rpg.getDexterity(), rpg.getJob() != null && rpg.getJob().overrideDexterity(), rpg.getDexterity(true)), 6, 63, 4210752);
			font.draw(ps, getStatLocalization("gj.job.intelligence", rpg.getIntelligence(), rpg.getJob() != null && rpg.getJob().overrideIntelligence(), rpg.getIntelligence(true)), 6, 75, 4210752);
			
			if(rpg.getJob() == null || !rpg.getJob().replaceMagick())
				font.draw(ps, Component.translatable("gj.job.magic", rpg.getMagick(), rpg.getMaxMagick()), 78, 16, 4210752);
			int expColor = RPGDefault.getExperienceCostForNextLevel(minecraft.player) >= minecraft.player.experienceLevel ? 0x8A0707 : 0x009900;
            font.draw(ps, Component.translatable("gj.exp_cost", RPGDefault.getExperienceCostForNextLevel(minecraft.player)), 6, 87, expColor);
			if(extraInfoScrollPanel != null)
            	if(rpg.getJob() != null && rpg.getJob().shouldDrawExtraInformation()) {
					extraInfoScrollPanel.components.add(Component.translatable("gj.job.additional_info"));
					rpg.getJob().addExtraInformation(extraInfoScrollPanel.components);
				}
		}
	}
	
	private static String getStatLocalization(String statToLocalize, int statOverride, boolean willOverride, int statOriginal) {
		String stat = Component.translatable(statToLocalize, statOverride).getString();
		if(willOverride) {
			int difference = statOverride - statOriginal;
			String temp = "(" + statOriginal;
			if(difference < 0)
				temp += "-" + Math.abs(difference) + ")";
			else if(difference > 0)
				temp += "+" + difference + ")";
			else
				temp += ")"; 
			stat = Component.translatable(statToLocalize + "_job", statOverride, temp).getString();
		}
		return stat;
	}

	@Override
	protected void init() {
		super.init();
		
		renderables.clear();

		final Component arrow = Component.literal("->");
		final boolean bHand = hand == InteractionHand.MAIN_HAND;
		final Consumer<Integer> screenButton = buttonId -> PacketHandler.sendToServer(new ScreenButtonPacket(bHand, buttonId));

		addRenderableWidget(new Button.Builder(arrow, (button) -> screenButton.accept(0)).pos(leftPos + 124, topPos + 25).size(16, 10).build());
		addRenderableWidget(new Button.Builder(arrow, (button) -> screenButton.accept(1)).pos(leftPos + 124, topPos + 37).size(16, 10).build());
		addRenderableWidget(new Button.Builder(arrow, (button) -> screenButton.accept(2)).pos(leftPos + 124, topPos + 49).size(16, 10).build());
		addRenderableWidget(new Button.Builder(arrow, (button) -> screenButton.accept(3)).pos(leftPos + 124, topPos + 61).size(16, 10).build());
		addRenderableWidget(new Button.Builder(arrow, (button) -> screenButton.accept(4)).pos(leftPos + 124, topPos + 73).size(16, 10).build());
		addRenderableWidget(new Button.Builder(arrow, (button) -> screenButton.accept(5)).pos(leftPos + 124, topPos + 85).size(16, 10).build());

		extraInfoScrollPanel = new ExtraInfoScrollPanel(minecraft, imageWidth - (4 * 2), 56, topPos + SUGGESTED_Y, leftPos + 4);
		addRenderableWidget(extraInfoScrollPanel);
	}

	private class ExtraInfoScrollPanel extends ScrollPanel {

		private final ArrayList<Component> components = new ArrayList<>();

		public ExtraInfoScrollPanel(Minecraft client, int width, int height, int top, int left) {
			super(client, width, height, top, left, 4, 6, 0xC6C6C6);
		}

		@Override
		public NarrationPriority narrationPriority() { return NarrationPriority.NONE; }

		@Override
		public void updateNarration(NarrationElementOutput output) {}

		@Override
		protected int getContentHeight() { return components.size() * 12; }

		@Override
		protected int getScrollAmount() { return 12; }

		@Override
		protected void drawPanel(PoseStack poseStack, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {
			for(Component component : components) {
				font.draw(poseStack, component, left, relativeY, 4210752);
				relativeY += 12;
			}
		}
	}

	private static class InfoComponentTooltip {
		public final Component component;
		public final int minX;
		public final int maxX;
		public final int minY;
		public final int maxY;
	
		public InfoComponentTooltip(Component component, int minX, int maxX, int minY, int maxY) {
			this.component = component;
			this.minX = minX;
			this.maxX = maxX;
			this.minY = minY;
			this.maxY = maxY;
		}
	}
}
