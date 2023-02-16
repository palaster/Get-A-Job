package palaster.gj.jobs;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Font;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
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
	public void drawExtraInformation(PoseStack ps, Font font, int mouseX, int mouseY, @Nullable Player player, int suggestedX, int suggestedY) {
		font.draw(ps, Component.translatable("gj.job.spellSlots", spellSlots), suggestedX, suggestedY, 4210752);
		font.draw(ps, Component.translatable("gj.job.abilities"), suggestedX, suggestedY, 4210752);
        font.draw(ps, Component.translatable("gj.job.cleric.abilities.divine_smackdown"), suggestedX, suggestedY + 12, 4210752);
	}

	@Override
	public boolean replaceMagick() { return true; }

	@Override
	@Nonnull
	public Tag serializeNBT() {
		Tag nbt = IRPGJob.super.serializeNBT();
		if(nbt instanceof CompoundTag)
			((CompoundTag) nbt).putInt(NBT_SPELL_SLOTS, spellSlots);
		return nbt;
	}

	@Override
	public void deserializeNBT(Tag nbt) {
		IRPGJob.super.deserializeNBT(nbt);
		if(nbt instanceof CompoundTag)
			spellSlots = ((CompoundTag) nbt).getInt(NBT_SPELL_SLOTS);
	}
}
