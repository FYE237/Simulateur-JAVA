package robot;

import tpl.Case;
import tpl.NatureTerrain;

public abstract class Robot {
	protected Case position;
	protected int volumeReservoir;
	protected double vitesse;

	public Case getPosition() {
		return position;
	}

	public abstract void setPosition(Case position) ;
	
	public abstract double getVitesse(NatureTerrain nature);
	
	public abstract void deverserEau(int vol);
	
	public abstract void remplirReservoir();

	@Override
	public String toString() {
		return "Robot [position=" + position + ", volumeReservoir=" + volumeReservoir + ", vitesse=" + vitesse + "]";
	}
	
}
