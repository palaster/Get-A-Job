package palaster.gj.jobs;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.text.TranslationTextComponent;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.jobs.IRPGJob;

public class JobCleric implements IRPGJob {

	public static final String NBT_SPELL_SLOTS = "gj:cleric:spell_slots";
	
	private int spellSlots = 0;
	
	public boolean canCastSpell() { return spellSlots > 0; }
	
	public int getSpellSlots() { return spellSlots; }
	
	public void castSpell() { spellSlots--; }
	
	public void resetSpellSlots(IRPG rpg) { spellSlots = rpg.getConstitution() > 0 ? (rpg.getIntelligence() + 10) / 5 : 1; }

	@Override
	public String getJobName() { return "gj.job.cleric"; }

	@Override
	public void drawExtraInformation(MatrixStack ms, FontRenderer font, int mouseX, int mouseY, PlayerEntity player, int suggestedX, int suggestedY) {
		//font.draw(ms, new StringTextComponent(I18n.get("gj.job.spellSlots", spellSlots)), suggestedX, suggestedY, 4210752);
		font.draw(ms, new TranslationTextComponent("gj.job.spellSlots", spellSlots), suggestedX, suggestedY, 4210752);
	}

	@Override
	public boolean replaceMagick() { return true; }

	@Override
	public INBT serializeNBT() {
		INBT nbt = IRPGJob.super.serializeNBT();
		if(nbt instanceof CompoundNBT)
			((CompoundNBT) nbt).putInt(NBT_SPELL_SLOTS, spellSlots);
		return nbt;
	}

	@Override
	public void deserializeNBT(INBT nbt) {
		IRPGJob.super.deserializeNBT(nbt);
		if(nbt instanceof CompoundNBT)
			spellSlots = ((CompoundNBT) nbt).getInt(NBT_SPELL_SLOTS);
	}
}
