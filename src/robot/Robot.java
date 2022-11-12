package robot;


import tpl.Carte;
import tpl.Case;
import tpl.NatureTerrain;

public abstract class Robot {
	protected Case position;
	protected int volumeReservoir;
	protected double vitesse;
	protected double debit;

	public Case getPosition() {
		return position;
	}

	public abstract void setPosition(Carte carte,Case position) ;
	
	public abstract double getVitesse(NatureTerrain nature);
	
	public abstract void deverserEau();
	
	public abstract void remplirReservoir(Carte carte);
	
	public boolean checkPosition(Carte carte, Case position) {
		if(position.getColonne() < carte.getNbColonnes() && position.getLigne() < carte.getNbColonnes()) return true;
		else return false;
	}
	
	public double calculDistance(Case destination) {
		double a = this.position.getLigne() - destination.getLigne();
		double b = this.position.getColonne() - destination.getColonne();
		return Math.sqrt(a*a + b*b);
	}
	
	public double  getDureeDeplacement(Case destination,Carte carte) {
		double d = this.calculDistance(destination)*carte.getTailleCases();
		return d/this.getVitesse(this.position.getNature());
	}
	
	public double getDebit() {
		return debit;
	}

	@Override
	public String toString() {
		return "Robot [position=" + position + ", volumeReservoir=" + volumeReservoir + ", vitesse=" + vitesse + "]\n";
	}
	
}