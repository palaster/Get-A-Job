package palaster.gj.jobs.spells.botany;

import java.util.ArrayList;

import palaster.gj.jobs.spells.IBotanySpell;

public class BotanySpells {
    public static final ArrayList<IBotanySpell> BOTANY_SPELLS = new ArrayList<IBotanySpell>();

    static {
        BOTANY_SPELLS.add(new BoSRoot());
    }
}
