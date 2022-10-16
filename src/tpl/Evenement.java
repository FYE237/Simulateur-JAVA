package tpl;

public abstract class Evenement {
	private long date;

	public Evenement(long date) {
		this.date = date;
	}

	public long getDate() {
		return date;
	}
	
	public abstract void execute();
	
}