package palaster.gj.jobs.spells.botany;

import java.util.ArrayList;

import palaster.gj.jobs.BotanistJob;
import palaster.gj.jobs.spells.Spells;

public class BotanySpells extends Spells<IBotanySpell> {
    public static final ArrayList<IBotanySpell> BOTANY_SPELLS = new ArrayList<IBotanySpell>();

    static {
        BOTANY_SPELLS.add(new BoSRoot());
    }

    @Override
    public ArrayList<IBotanySpell> getSpells() { return BOTANY_SPELLS; }

    @Override
    public Class<BotanistJob> getJobClass() { return BotanistJob.class; }

    @Override
    public String getSelectedSpellNBTString() { return "gj:herb_sack:selected_spell"; }
}
