package palaster.gj.api.jobs;

public abstract class RPGJobBase implements IRPGJob {

	@Override
	public String toString() { return getJobName(); }
}
