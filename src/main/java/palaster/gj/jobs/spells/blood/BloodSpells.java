package palaster.gj.jobs.spells.blood;

import java.util.ArrayList;

import palaster.gj.jobs.spells.IBloodSpell;

public class BloodSpells {
    public static final ArrayList<IBloodSpell> BLOOD_SPELLS = new ArrayList<IBloodSpell>();

    static {
        BLOOD_SPELLS.add(new BlSDrain());
    }
}
