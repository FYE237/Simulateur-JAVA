/**
 * 
 */
package robot;

import tpl.Carte;
import tpl.Case;
import tpl.Direction;
import tpl.NatureTerrain;

/**
 * @author fye
 *
 */
public class RobotAPatte extends Robot {


	/**
	 * 
	 * @param position :Position sur laquelle se trouve le robot
	 */
	public RobotAPatte (Case position) {
		super();
		this.position = new  Case(position.getLigne(), position.getColonne(), position.getNature());
		this.vitesse = 30;
		this.volumeReservoir=Integer.MAX_VALUE;
		this.debit = 3000;
	}

	/**
	 * 
	 * @param position
	 * @param vitesse
	 */

	public RobotAPatte (Case position, double vitesse) {
		super();
		this.position = new  Case(position.getLigne(), position.getColonne(), position.getNature());
		if (vitesse > 80 ) {
			this.vitesse = 80 ;
		}
		else {
			this.vitesse = vitesse;
		}
		this.volumeReservoir=Integer.MAX_VALUE;
		this.debit = 3000;
	}

	/*
	 *On met la valeur du reservoir à la valeur par défaut.
	 *Ici le robot à patte ne se vide jamais donc on met le volume de son reservoir à infini 
	 */
	@Override
	public void setVolumeReservoir(){
		// TODO Auto-generated method stub
		this.volumeReservoir = Integer.MAX_VALUE;
	}

	/**
	 * @param carte
	 * @param position : La case sur laquelle se trouvera le robot
	 */
	@Override
	public void setPosition(Carte carte,Case position) {
		// TODO Auto-generated method stub
		if(checkPosition(carte, position)) {
			if(position.getNature() != NatureTerrain.EAU) {
				this.position = carte.getCase(position.getLigne(), position.getColonne());
			}
		}

	}

	/**
	 * @param nature 
	 */
	@Override
	public double getVitesse(NatureTerrain nature) {
		// TODO Auto-generated method stub
		if(nature == NatureTerrain.FORET) {
			return 10.0;
		}
		if(nature == NatureTerrain.EAU) {
			return 0.0;
		}
		return this.vitesse;
	}


	@Override
	public void deverserEau() {
		// TODO Auto-generated method stub
		//this.volumeReservoir -=this.debit;
	}

	/**
	 * @param carte  La carte 
	 */
	@Override
	public void remplirReservoir(Carte carte) {

		/*
		 * Pour ce robot il ne se remplira  jamais donc cette méthode n'est jamais appelé
		 */
		
	}

}
