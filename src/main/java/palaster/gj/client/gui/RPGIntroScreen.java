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
    			
    			String constitution = I18n.get("gj.job.constitution", rpg.getConstitution());
    			if(rpg.getJob() != null && rpg.getJob().overrideConstitution()) {
    				int difference = rpg.getConstitution() - rpg.getConstitution(true);
    				String temp = "(" + rpg.getConstitution(true);
    				if(difference < 0)
    					temp = temp + "-" + Math.abs(difference) + ")";
    				else if(difference > 0)
    					temp = temp + "+" + difference + ")";
    				else
    					temp = temp + ")";
    				constitution = I18n.get("gj.job.constitution_job", rpg.getConstitution(), temp);
    			}
    			String strength = I18n.get("gj.job.strength", rpg.getStrength());
    			if(rpg.getJob() != null && rpg.getJob().overrideStrength()) {
    				int difference = rpg.getStrength() - rpg.getStrength(true);
    				String temp = "(" + rpg.getStrength(true);
    				if(difference < 0)
    					temp = temp + "-" + Math.abs(difference) + ")";
    				else if(difference > 0)
    					temp = temp + "+" + difference + ")";
    				else
    					temp = temp + ")"; 
    				strength = I18n.get("gj.job.strength_job", rpg.getStrength(), temp);
    			}
    			String defense = I18n.get("gj.job.defense", rpg.getDefense());
    			if(rpg.getJob() != null && rpg.getJob().overrideDefense()) {
    				int difference = rpg.getDefense() - rpg.getDefense(true);
    				String temp = "(" + rpg.getDefense(true);
    				if(difference < 0)
    					temp = temp + "-" + Math.abs(difference) + ")";
    				else if(difference > 0)
    					temp = temp + "+" + difference + ")";
    				else
    					temp = temp + ")"; 
    				constitution = I18n.get("gj.job.defense_job", rpg.getDefense(), temp);
    			}
    			String dexterity = I18n.get("gj.job.dexterity", rpg.getDexterity());
    			if(rpg.getJob() != null && rpg.getJob().overrideDexterity()) {
    				int difference = rpg.getDexterity() - rpg.getDexterity(true);
    				String temp = "(" + rpg.getDexterity(true);
    				if(difference < 0)
    					temp = temp + "-" + Math.abs(difference) + ")";
    				else if(difference > 0)
    					temp = temp + "+" + difference + ")";
    				else
    					temp = temp + ")"; 
    				constitution = I18n.get("gj.job.dexterity_job", rpg.getDexterity(), temp);
    			}
    			String intelligence = I18n.get("gj.job.intelligence", rpg.getIntelligence());
    			if(rpg.getJob() != null && rpg.getJob().overrideIntelligence()) {
    				int difference = rpg.getIntelligence() - rpg.getIntelligence(true);
    				String temp = "(" + rpg.getIntelligence(true);
    				if(difference < 0)
    					temp = temp + "-" + Math.abs(difference) + ")";
    				else if(difference > 0)
    					temp = temp + "+" + difference + ")";
    				else
    					temp = temp + ")"; 
    				constitution = I18n.get("gj.job.intelligence_job", rpg.getIntelligence(), temp);
    			}
    			
    			font.draw(ms, constitution, 6, 26, 4210752);
    			font.draw(ms, strength, 6, 36, 4210752);
    			font.draw(ms, defense, 6, 46, 4210752);
    			font.draw(ms, dexterity, 6, 56, 4210752);
    			font.draw(ms, intelligence, 6, 66, 4210752);
    			
    			if(rpg.getJob() == null || !rpg.getJob().replaceMagick())
    				font.draw(ms, I18n.get("gj.job.magic", rpg.getMagick(), rpg.getMaxMagick()), 98, 66, 4210752);
    			int expColor = RPGDefault.getExperienceCostForNextLevel(player.get()) >= player.get().experienceLevel ? 0x8A0707 : 0x009900; 
                font.draw(ms, I18n.get("gj.expCost", RPGDefault.getExperienceCostForNextLevel(player.get())), 6, 76, expColor);
                if(rpg.getJob() != null)
                	rpg.getJob().drawExtraInformationBase(ms, font, mouseX, mouseY, player.get(), 6, 86);
    		}
		}
	}

	@Override
	protected void init() {
		super.init();
		
		buttons.clear();
		
		ITextComponent arrow = new StringTextComponent("->");
		boolean bHand = hand == Hand.MAIN_HAND;
		addButton(new Button(leftPos + 82, topPos + 26, 12, 10, arrow, (button) -> PacketHandler.sendToServer(new ScreenButtonPacket(bHand, 0))));
		addButton(new Button(leftPos + 82, topPos + 36, 12, 10, arrow, (button) -> PacketHandler.sendToServer(new ScreenButtonPacket(bHand, 1))));
		addButton(new Button(leftPos + 82, topPos + 46, 12, 10, arrow, (button) -> PacketHandler.sendToServer(new ScreenButtonPacket(bHand, 2))));
		addButton(new Button(leftPos + 82, topPos + 56, 12, 10, arrow, (button) -> PacketHandler.sendToServer(new ScreenButtonPacket(bHand, 3))));
		addButton(new Button(leftPos + 82, topPos + 66, 12, 10, arrow, (button) -> PacketHandler.sendToServer(new ScreenButtonPacket(bHand, 4))));
		addButton(new Button(leftPos + 82, topPos + 76, 12, 10, arrow, (button) -> PacketHandler.sendToServer(new ScreenButtonPacket(bHand, 5))));
	}
}
