package tpl;

public class Incendie {
	private Case position;
	private int volExtinction;
	
	public Incendie(Case position, int volExtinction) {
		this.position = position;
		this.volExtinction = volExtinction;
	}

	public Case getPosition() {
		return position;
	}
	
	public int getVolExtinction() {
		return volExtinction;
	}
	
}
