package tpl;

public abstract class Robot {
	private Case position;

	public Case getPosition() {
		return position;
	}

	public void setPosition(Case position) {
		this.position = position;
	}
	
	public abstract double getVitesse(NatureTerrain nature);
	
	public abstract void deverserEau(int vol);
	
	public abstract void remplirReservoir();
	
}
