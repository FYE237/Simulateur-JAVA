package tpl;

public class Incendie {
	private Case position;
	private int intensite;
	
	public Incendie(Case position, int intensite) {
		this.position = position;
		this.intensite = intensite;
	}

	public Case getPosition() {
		return position;
	}
	
	public int getIntensite() {
		return intensite;
	}

	@Override
	public String toString() {
		return "Incendie [position=" + position + ", intensite=" + intensite + "]\n";
	}
	
}
