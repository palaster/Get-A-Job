package palaster.gj.jobs.spells.blood;

import java.util.ArrayList;

import palaster.gj.jobs.BloodSorcererJob;
import palaster.gj.jobs.spells.Spells;

public class BloodSpells extends Spells<IBloodSpell> {
    public static final ArrayList<IBloodSpell> BLOOD_SPELLS = new ArrayList<IBloodSpell>();

    static {
        BLOOD_SPELLS.add(new BlSDrain());
        BLOOD_SPELLS.add(new BlsMassDecay());
    }

    @Override
    public ArrayList<IBloodSpell> getSpells() { return BLOOD_SPELLS; }

    @Override
    public Class<BloodSorcererJob> getJobClass() { return BloodSorcererJob.class; }

    @Override
    public String getSelectedSpellNBTString() { return "gj:blood_book:selected_spell"; }

    @Override
    public String getSpellNameLocalization() { return "gj.job.blood_sorcerer.spell."; }
}
