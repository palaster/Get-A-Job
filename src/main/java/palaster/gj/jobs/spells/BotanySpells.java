package palaster.gj.jobs.spells;

import java.util.ArrayList;

public class BotanySpells {
    public static final ArrayList<IBotanySpell> BOTANY_SPELLS = new ArrayList<IBotanySpell>();

    static {
        BOTANY_SPELLS.add(new BSSummonSpineShooter());
        BOTANY_SPELLS.add(new BSRoot());
    }
}
