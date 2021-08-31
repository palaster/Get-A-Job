package palaster.gj.jobs.abilities;

import palaster.gj.api.jobs.abilities.IAbility;
import palaster.gj.jobs.JobBloodSorcerer;
import palaster.gj.jobs.JobBotanist;
import palaster.gj.jobs.JobCleric;

public class Abilities {

    public static final IAbility FLOURISH = (rpg) -> rpg.getJob() instanceof JobBotanist,
            BOUNTIFUL_HARVEST = FLOURISH,
            DIVINE_SMACKDOWN = (rpg) -> rpg.getJob() instanceof JobCleric,
            DARK_STALKER = (rpg) -> rpg.getJob() instanceof JobBloodSorcerer;
}
