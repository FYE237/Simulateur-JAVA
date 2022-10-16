package robot;

import tpl.Case;
import tpl.NatureTerrain;

public abstract class Robot {
	private Case position;

	public Case getPosition() {
		return position;
	}

	public abstract void setPosition(Case position);
	
	public abstract double getVitesse(NatureTerrain nature);
	
	public abstract void deverserEau(int vol);
	
	public abstract void remplirReservoir();
	
}
