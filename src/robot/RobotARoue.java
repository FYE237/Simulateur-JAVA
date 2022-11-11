/**
 * 
 */
package robot;

import tpl.Case;
import tpl.NatureTerrain;

/**
 * @author fezeuyoe
 *
 */
public class RobotARoue  extends Robot{

	
	public RobotARoue (Case position) {
		this.position = new  Case(position.getLigne(), position.getColonne(), position.getNature());
		this.vitesse = 80;
		this.debit = 100;
		this.volumeReservoir=5000;
	}
	
	public RobotARoue (Case position, double vitesse) {
		this.position = new  Case(position.getLigne(), position.getColonne(), position.getNature());
		this.vitesse = vitesse;
		this.volumeReservoir=5000;
	}
	
	@Override
	public void setPosition(Case position) {
		// TODO Auto-generated method stub
		if(position.getNature() == NatureTerrain.TERRAIN_LIBRE ||
				position.getNature() == NatureTerrain.HABITAT	) {
			this.position = new Case(position.getLigne(),position.getColonne(),
										position.getNature());
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
		if(this.volumeReservoir < 100) {
			this.volumeReservoir = 0;
		}
		else {
			this.volumeReservoir -= 4;
		}
	}

	@Override
	public void remplirReservoir() {
		// TODO Auto-generated method stub
		this.volumeReservoir = 5000;
	}

}
