package palaster.gj.jobs.spells;

import palaster.gj.jobs.JobCleric.EnumDomain;

import java.util.ArrayList;

public class DomainSpells {

	public static final ArrayList<IDomainSpell> CREATION_DOMAIN_SPELLS = new ArrayList<IDomainSpell>();
	public static final ArrayList<IDomainSpell> COMMUNITY_DOMAIN_SPELLS = new ArrayList<IDomainSpell>();
	public static final ArrayList<IDomainSpell> LIFE_DOMAIN_SPELLS = new ArrayList<IDomainSpell>();
	
	static {
		CREATION_DOMAIN_SPELLS.add(new DSCreation());
		CREATION_DOMAIN_SPELLS.add(new DSBurn());

		COMMUNITY_DOMAIN_SPELLS.add(new DSShieldOther());
		COMMUNITY_DOMAIN_SPELLS.add(new DSHeal());

		LIFE_DOMAIN_SPELLS.add(new DSGrowth());
		LIFE_DOMAIN_SPELLS.add(new DSHeal());
	}

	public static ArrayList<IDomainSpell> getDomainSpellsFromDomain(EnumDomain Domain) {
		switch(Domain) {
			case CREATION:
				return CREATION_DOMAIN_SPELLS;
			case COMMUNITY:
				return COMMUNITY_DOMAIN_SPELLS;
			case LIFE:
				return LIFE_DOMAIN_SPELLS;
			default:
				return null;
		}
	}
}
