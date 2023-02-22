package palaster.gj.jobs.spells;

import java.util.ArrayList;

import palaster.gj.api.jobs.IRPGJob;

public abstract class Spells<T extends ISpell> {

    public abstract ArrayList<T> getSpells();

    public abstract Class<? extends IRPGJob> getJobClass();

    public abstract String getSelectedSpellNBTString();
}
