package robot;

import tpl.Carte;
import tpl.Case;
import tpl.NatureTerrain;

public abstract class Robot {
	protected Case position;
	protected int volumeReservoir;
	protected double debit;
	protected double vitesse;

	public Case getPosition() {
		return position;
	}

	public abstract void setPosition(Case position) ;
	
	public abstract double getVitesse(NatureTerrain nature);
	
	public abstract void deverserEau();
	
	public abstract void remplirReservoir(Carte carte);
	
	public boolean checkPosition(Carte carte , Case position) {
		if(position.getColonne() < carte.getNbColonnes() && position.getLigne() < carte.getNbLignes())
			return true;
		else return false;
	}
	
	public double getDebit() {
		return debit;
	}

	@Override
	public String toString() {
		return "Robot [position=" + position + ", volumeReservoir=" + volumeReservoir + ", vitesse=" + vitesse + "]\n";
	}
	
}
