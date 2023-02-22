package palaster.gj.jobs.spells.domain;

import java.util.ArrayList;

import palaster.gj.jobs.ClericJob;
import palaster.gj.jobs.spells.Spells;

public class DomainSpells extends Spells<IDomainSpell> {

	public static final ArrayList<IDomainSpell> DOMAIN_SPELLS = new ArrayList<IDomainSpell>();
	
	static {
		DOMAIN_SPELLS.add(new DSBurn());
		DOMAIN_SPELLS.add(new DSShieldOther());
		DOMAIN_SPELLS.add(new DSHeal());
		DOMAIN_SPELLS.add(new DSGrowth());
	}

	@Override
	public ArrayList<IDomainSpell> getSpells() { return DOMAIN_SPELLS; }

	@Override
	public Class<ClericJob> getJobClass() { return ClericJob.class; }

	@Override
	public String getSelectedSpellNBTString() { return "gj:gospel:selected_spell"; }
}
