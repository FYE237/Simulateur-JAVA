package robot;


import tpl.Carte;
import tpl.Case;
import tpl.NatureTerrain;
import tpl.StatutRobot;



public abstract class Robot implements Comparable<Robot>{
	protected Case position;
	protected int volumeReservoir;
	protected double vitesse;
	protected double debit;
	protected StatutRobot statut;

	public Robot() {
		this.statut = StatutRobot.disponible;
	}
	
	public Case getPosition() {
		return this.position;
	}

	public StatutRobot getStatut() {
		return this.statut;
	}

	public void setStatut(StatutRobot statut) {
		this.statut = statut;
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
		return this.debit;
	}

	@Override
	public String toString() {
		return "Robot"+ this.getClass() +"[position=" + position + ", volumeReservoir=" + volumeReservoir + ", vitesse=" + vitesse
				+ ", debit=" + debit + ", statut=" + statut + "]";
	}

	@Override
	public int compareTo(Robot o) {
		// TODO Auto-generated method stub
		return (int) (o.debit-this.debit);
	}
	
	
	
}