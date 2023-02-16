package palaster.gj.client.screens;

import java.util.function.Consumer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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

	private InteractionHand hand;
	
	public RPGIntroScreen(RPGIntroMenu container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		this.hand = container.hand;
		imageHeight = 160;
	}

	@Override
	public void render(PoseStack ps, int mouseX, int mouseY, float partialTicks) {
		renderBackground(ps);
		super.render(ps, mouseX, mouseY, partialTicks);
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
				font.draw(ps, I18n.get("gj.job.base", I18n.get(rpg.getJob().getJobName())), 6, 6, 4210752);
			else
				font.draw(ps, I18n.get("gj.job.base", I18n.get("gj.job.noCareer")), 6, 6, 4210752);
			font.draw(ps, I18n.get("gj.job.level", rpg.getLevel()), 6, 16, 4210752);

			font.draw(ps, getStatLocalization("gj.job.constitution", rpg.getConstitution(), rpg.getJob() != null && rpg.getJob().overrideConstitution(), rpg.getConstitution(true)), 6, 27, 4210752);
			font.draw(ps, getStatLocalization("gj.job.strength", rpg.getStrength(), rpg.getJob() != null && rpg.getJob().overrideStrength(), rpg.getStrength(true)), 6, 39, 4210752);
			font.draw(ps, getStatLocalization("gj.job.defense", rpg.getDefense(), rpg.getJob() != null && rpg.getJob().overrideDefense(), rpg.getDefense(true)), 6, 51, 4210752);
			font.draw(ps, getStatLocalization("gj.job.dexterity", rpg.getDexterity(), rpg.getJob() != null && rpg.getJob().overrideDexterity(), rpg.getDexterity(true)), 6, 63, 4210752);
			font.draw(ps, getStatLocalization("gj.job.intelligence", rpg.getIntelligence(), rpg.getJob() != null && rpg.getJob().overrideIntelligence(), rpg.getIntelligence(true)), 6, 75, 4210752);
			
			if(rpg.getJob() == null || !rpg.getJob().replaceMagick())
				font.draw(ps, I18n.get("gj.job.magic", rpg.getMagick(), rpg.getMaxMagick()), 78, 16, 4210752);
			int expColor = RPGDefault.getExperienceCostForNextLevel(minecraft.player) >= minecraft.player.experienceLevel ? 0x8A0707 : 0x009900; 
            font.draw(ps, I18n.get("gj.expCost", RPGDefault.getExperienceCostForNextLevel(minecraft.player)), 6, 87, expColor);
            if(rpg.getJob() != null)
            	rpg.getJob().drawExtraInformationBase(ps, font, mouseX, mouseY, minecraft.player, 6, 99);
		}
	}
	
	private static String getStatLocalization(String statToLocalize, int statOverride, boolean willOverride, int statOriginal) {
		String stat = I18n.get(statToLocalize, statOverride);
		if(willOverride) {
			int difference = statOverride - statOriginal;
			String temp = "(" + statOriginal;
			if(difference < 0)
				temp += "-" + Math.abs(difference) + ")";
			else if(difference > 0)
				temp += "+" + difference + ")";
			else
				temp += ")"; 
			stat = I18n.get(statToLocalize + "_job", statOverride, temp);
		}
		return stat;
	}

	@Override
	protected void init() {
		super.init();
		
		renderables.clear();
		
		final Component arrow = Component.literal("->");
		final boolean bHand = hand == InteractionHand.MAIN_HAND;
		final Consumer<Integer> screenButton = (buttonId) -> PacketHandler.sendToServer(new ScreenButtonPacket(bHand, buttonId));

		addRenderableWidget(new Button.Builder(arrow, (button) -> screenButton.accept(0)).pos(leftPos + 124, topPos + 25).size(16, 10).build());
		addRenderableWidget(new Button.Builder(arrow, (button) -> screenButton.accept(1)).pos(leftPos + 124, topPos + 37).size(16, 10).build());
		addRenderableWidget(new Button.Builder(arrow, (button) -> screenButton.accept(2)).pos(leftPos + 124, topPos + 49).size(16, 10).build());
		addRenderableWidget(new Button.Builder(arrow, (button) -> screenButton.accept(3)).pos(leftPos + 124, topPos + 61).size(16, 10).build());
		addRenderableWidget(new Button.Builder(arrow, (button) -> screenButton.accept(4)).pos(leftPos + 124, topPos + 73).size(16, 10).build());
		addRenderableWidget(new Button.Builder(arrow, (button) -> screenButton.accept(5)).pos(leftPos + 124, topPos + 85).size(16, 10).build());
	}
}
