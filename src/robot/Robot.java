package robot;


import tpl.Carte;
import tpl.Case;
import tpl.NatureTerrain;
import tpl.StatutRobot;


/**
 * 
 * @author 
 *
 */
public abstract class Robot implements Comparable<Robot>{
	protected Case position;
	protected int volumeReservoir;
	protected double vitesse;
	protected double debit;
	protected StatutRobot statut;

	/**
	 * 
	 */
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
	
	//Methode qui verse l'eau du reservoir du robot
	public abstract void deverserEau();
	
	//Methode qui remet la valeur du volume du réservoir à la valeur par défaut
	public abstract void setVolumeReservoir();
	
	/**
	 * 
	 * @param carte
	 */
	public abstract void remplirReservoir(Carte carte);
	
	/**
	 * 
	 * @param carte
	 * @param position
	 * @return true or false 
	 */
	//Methode qui la postion est sur la carte
	public boolean checkPosition(Carte carte, Case position) {
		if(position.getColonne() < carte.getNbColonnes() && position.getLigne() < carte.getNbColonnes()) return true;
		else return false;
	}
	
	/**
	 * 
	 * @param destination
	 * @return distance  
	 */
	//Distance entre la position du robot et la case destination
	public double calculDistance(Case destination) {
		double a = this.position.getLigne() - destination.getLigne();
		double b = this.position.getColonne() - destination.getColonne();
		return Math.sqrt(a*a + b*b);
	}
	
	/**
	 * 
	 * @param destination
	 * @param carte
	 * @return dureeDeplacement
	 */
	public double  getDureeDeplacement(Case destination,Carte carte) {
		double d = this.calculDistance(destination)*carte.getTailleCases();
		return d/this.getVitesse(this.position.getNature());
	}
	
	/**
	 * 
	 * @return debit
	 */
	//Elle retourne le debit du robot
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