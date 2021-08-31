package palaster.gj.client.gui;

import java.lang.ref.WeakReference;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGDefault;
import palaster.gj.api.capabilities.rpg.RPGCapability.RPGProvider;
import palaster.gj.containers.RPGIntroContainer;
import palaster.gj.libs.LibResource;
import palaster.libpal.network.PacketHandler;
import palaster.libpal.network.server.ScreenButtonPacket;

@OnlyIn(Dist.CLIENT)
public class RPGIntroScreen extends ContainerScreen<RPGIntroContainer> {
	
	private WeakReference<PlayerEntity> player = null;
	private Hand hand;
	
	public RPGIntroScreen(RPGIntroContainer container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title);
		this.player = new WeakReference<PlayerEntity>(playerInventory.player);
		this.hand = container.hand;
		imageHeight = 160;
	}

	@Override
	protected void renderBg(MatrixStack ms, float mouseX, int mouseY, int partialTicks) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		minecraft.getTextureManager().bind(LibResource.BLANK);
		blit(ms, (width - imageWidth) / 2, (height - imageHeight) / 2, 0, 0, imageWidth, imageHeight);
	}
	
	@Override
	protected void renderLabels(MatrixStack ms, int mouseX, int mouseY) {
		if(player != null && player.get() != null) {
			LazyOptional<IRPG> lazy_optional_rpg = player.get().getCapability(RPGProvider.RPG_CAPABILITY, null);
    		final IRPG rpg = lazy_optional_rpg.orElse(null);
    		if(rpg != null) {
    			if(rpg.getJob() != null)
    				font.draw(ms, I18n.get("gj.job.base", I18n.get(rpg.getJob().getJobName())), 6, 6, 4210752);
    			else
    				font.draw(ms, I18n.get("gj.job.base", I18n.get("gj.job.noCareer")), 6, 6, 4210752);
    			font.draw(ms, I18n.get("gj.job.level", rpg.getLevel()), 6, 16, 4210752);

    			font.draw(ms, getStatLocalization("gj.job.constitution", rpg.getConstitution(), rpg.getJob() != null && rpg.getJob().overrideConstitution(), rpg.getConstitution(true)), 6, 27, 4210752);
    			font.draw(ms, getStatLocalization("gj.job.strength", rpg.getStrength(), rpg.getJob() != null && rpg.getJob().overrideStrength(), rpg.getStrength(true)), 6, 39, 4210752);
    			font.draw(ms, getStatLocalization("gj.job.defense", rpg.getDefense(), rpg.getJob() != null && rpg.getJob().overrideDefense(), rpg.getDefense(true)), 6, 51, 4210752);
    			font.draw(ms, getStatLocalization("gj.job.dexterity", rpg.getDexterity(), rpg.getJob() != null && rpg.getJob().overrideDexterity(), rpg.getDexterity(true)), 6, 63, 4210752);
    			font.draw(ms, getStatLocalization("gj.job.intelligence", rpg.getIntelligence(), rpg.getJob() != null && rpg.getJob().overrideIntelligence(), rpg.getIntelligence(true)), 6, 75, 4210752);
    			
    			if(rpg.getJob() == null || !rpg.getJob().replaceMagick())
    				font.draw(ms, I18n.get("gj.job.magic", rpg.getMagick(), rpg.getMaxMagick()), 78, 16, 4210752);
    			int expColor = RPGDefault.getExperienceCostForNextLevel(player.get()) >= player.get().experienceLevel ? 0x8A0707 : 0x009900; 
                font.draw(ms, I18n.get("gj.expCost", RPGDefault.getExperienceCostForNextLevel(player.get())), 6, 87, expColor);
                if(rpg.getJob() != null)
                	rpg.getJob().drawExtraInformationBase(ms, font, mouseX, mouseY, player.get(), 6, 99);
    		}
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
		
		buttons.clear();
		
		final ITextComponent arrow = new StringTextComponent("->");
		final boolean bHand = hand == Hand.MAIN_HAND;
		addButton(new Button(leftPos + 92, topPos + 25, 16, 10, arrow, (button) -> PacketHandler.sendToServer(new ScreenButtonPacket(bHand, 0))));
		addButton(new Button(leftPos + 92, topPos + 37, 16, 10, arrow, (button) -> PacketHandler.sendToServer(new ScreenButtonPacket(bHand, 1))));
		addButton(new Button(leftPos + 92, topPos + 49, 16, 10, arrow, (button) -> PacketHandler.sendToServer(new ScreenButtonPacket(bHand, 2))));
		addButton(new Button(leftPos + 92, topPos + 61, 16, 10, arrow, (button) -> PacketHandler.sendToServer(new ScreenButtonPacket(bHand, 3))));
		addButton(new Button(leftPos + 92, topPos + 73, 16, 10, arrow, (button) -> PacketHandler.sendToServer(new ScreenButtonPacket(bHand, 4))));
		addButton(new Button(leftPos + 92, topPos + 85, 16, 10, arrow, (button) -> PacketHandler.sendToServer(new ScreenButtonPacket(bHand, 5))));
	}
}
