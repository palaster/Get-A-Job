package palaster.gj.jobs.spells;

import java.util.ArrayList;

public class BloodSpells {
    public static final ArrayList<IBloodSpell> BLOOD_SPELLS = new ArrayList<IBloodSpell>();

    static {
        BLOOD_SPELLS.add(new BlSDrain());
    }
}
