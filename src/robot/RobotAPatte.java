/**
 * 
 */
package robot;

import tpl.Carte;
import tpl.Case;
import tpl.NatureTerrain;

/**
 * @author fye
 *
 */
public class RobotAPatte extends Robot {
	
	
	
	public RobotAPatte (Case position) {
		super();
		this.position = new  Case(position.getLigne(), position.getColonne(), position.getNature());
		this.vitesse = 30;
		this.volumeReservoir=Integer.MAX_VALUE;
		this.debit = 10;
	}
	
	public RobotAPatte (Case position, double vitesse) {
		super();
		this.position = new  Case(position.getLigne(), position.getColonne(), position.getNature());
		if (vitesse > 80 ) {
			this.vitesse = 80 ;
		}
		else {
				this.vitesse = vitesse;
			}
		this.volumeReservoir=2000;
		this.debit = 10;
	}
	
	//fye
	@Override
	public void setPosition(Carte carte,Case position) {
		// TODO Auto-generated method stub
		if(checkPosition(carte, position)) {
			if(position.getNature() != NatureTerrain.EAU) {
				this.position = carte.getCase(position.getLigne(), position.getColonne());
			}
		}
		
	}

	@Override
	public double getVitesse(NatureTerrain nature) {
		// TODO Auto-generated method stub
		if(position.getNature() == NatureTerrain.FORET) {
			return 10.0;
		}
		return this.vitesse;
	}
	

	@Override
	public void deverserEau() {
		// TODO Auto-generated method stub
		 this.volumeReservoir -=this.debit;
	}

	@Override
	public void remplirReservoir(Carte carte) {
		// TODO Auto-generated method stub
	}

}