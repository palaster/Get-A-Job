package palaster.gj.jobs;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.jobs.IRPGJob;

public class ClericJob implements IRPGJob {

	public static final String NBT_SPELL_SLOTS = "gj:cleric:spell_slots";
	
	private int spellSlots = 0;
	
	public boolean canCastSpell() { return spellSlots > 0; }
	
	public int getSpellSlots() { return spellSlots; }
	
	public void castSpell() { spellSlots--; }
	
	public void resetSpellSlots(IRPG rpg) { spellSlots = rpg.getConstitution() > 0 ? (rpg.getIntelligence() + 10) / 5 : 1; }

	@Override
	public String getJobName() { return "gj.job.cleric"; }

	@Override
	public void addExtraInformation(ArrayList<Component> components) {
		components.add(Component.translatable("gj.job.spell_slots", spellSlots));
		components.add(Component.translatable("gj.job.cleric.abilities.divine_smackdown"));
		components.add(Component.translatable("gj.job.cleric.abilities.holy_gold"));
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
