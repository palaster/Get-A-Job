package palaster.gj.jobs.abilities;

import palaster.gj.api.jobs.abilities.IAbility;
import palaster.gj.jobs.BloodSorcererJob;
import palaster.gj.jobs.BotanistJob;
import palaster.gj.jobs.ClericJob;

public class Abilities {

    public static final IAbility FLOURISH = (rpg) -> rpg.getJob() instanceof BotanistJob,
            BOUNTIFUL_HARVEST = FLOURISH,
            DIVINE_SMACKDOWN = (rpg) -> rpg.getJob() instanceof ClericJob,
            HOLY_GOLD = DIVINE_SMACKDOWN,
            DARK_STALKER = (rpg) -> rpg.getJob() instanceof BloodSorcererJob;
}
