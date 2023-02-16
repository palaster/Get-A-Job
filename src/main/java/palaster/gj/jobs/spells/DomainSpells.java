package palaster.gj.jobs.spells;

import java.util.ArrayList;

public class DomainSpells {

	public static final ArrayList<IDomainSpell> DOMAIN_SPELLS = new ArrayList<IDomainSpell>();
	
	static {
		DOMAIN_SPELLS.add(new DSBurn());
		DOMAIN_SPELLS.add(new DSShieldOther());
		DOMAIN_SPELLS.add(new DSHeal());
		DOMAIN_SPELLS.add(new DSGrowth());
	}
}
