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
		this.position = new  Case(position.getLigne(), position.getColonne(), position.getNature());
		this.vitesse = 30;
		this.debit = 10;
		this.volumeReservoir=Integer.MAX_VALUE;
	}
	
	public RobotAPatte (Case position, double vitesse) {
		this.position = new  Case(position.getLigne(), position.getColonne(), position.getNature());
		if (vitesse > 80 ) {
			this.vitesse = 80 ;
		}
		else {
				this.vitesse = vitesse;
			}
		this.volumeReservoir=2000;
	}
	
	@Override
	public void setPosition(Case position) {
		// TODO Auto-generated method stub
		if(position.getNature() != NatureTerrain.EAU &&
				position.getNature() != NatureTerrain.ROCHE	) {
			this.position = new Case(position.getLigne(),position.getColonne(),
										position.getNature());
		}
		if(position.getNature() == NatureTerrain.FORET) {
			this.vitesse= this.vitesse*0.5;
		}
		
	}

	@Override
	public double getVitesse(NatureTerrain nature) {
		// TODO Auto-generated method stub
		return this.vitesse;
	}

	@Override
	public void deverserEau() {
		// TODO Auto-generated method stub
			this.volumeReservoir -= this.debit;
	}

	@Override
	public void remplirReservoir(Carte carte) {
		// TODO Auto-generated method stub
		this.volumeReservoir=Integer.MAX_VALUE;
	}

}
